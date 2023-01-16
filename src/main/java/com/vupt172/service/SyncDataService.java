package com.vupt172.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vupt172.dto.SyncDataResult;
import com.vupt172.entity.Employee;
import com.vupt172.model.JsonEmployee;
import com.vupt172.model.Role;
import com.vupt172.model.SyncData;
import com.vupt172.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.*;
@Service
public class SyncDataService {
    @Autowired
    private WebClient webClient;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    EmployeeRepository employeeRepository;
    private static final ObjectMapper mapper = new ObjectMapper();
    public List<JsonEmployee> getEmployeeListJson(){
        Mono<SyncData> objects=webClient.get().uri("")
                .retrieve()
                .bodyToMono(SyncData.class);
       SyncData syncData=objects.block();
       return  syncData.getEmployeeList();
    }
    @Transactional
    public SyncDataResult SyncData(){
     List<JsonEmployee> jsonEmployeeList=getEmployeeListJson();
     List<Employee> newEmployeeList=new ArrayList<>();
     List<Employee> notActiveEmployeeList=new ArrayList<>();
     List<Employee> currentEmployeeList=employeeRepository.findAllByStatusAndRole("Enable",Role.USER.getValue());
     //check current employee not active->change to disable status
      for(Employee currentEmployee:currentEmployeeList){
          if(!isEmployeeActive(currentEmployee,jsonEmployeeList)) {
              currentEmployee.setStatus("Disable");
              notActiveEmployeeList.add(currentEmployee);
          }
      }
      employeeRepository.saveAll(notActiveEmployeeList);
     //check if not exist in db->then create new employee
        for(JsonEmployee jsonEmployee:jsonEmployeeList){
            if(!employeeRepository.existsByEmail(jsonEmployee.getEmail())){
              Employee newEmployee=makeEmployeeFromJson(jsonEmployee);
             newEmployeeList.add(newEmployee);
            }
        }
       newEmployeeList= employeeRepository.saveAll(newEmployeeList);
        return new SyncDataResult(notActiveEmployeeList,newEmployeeList);
    }
    public Employee makeEmployeeFromJson(JsonEmployee jsonEmployee){
        Employee newEmployee=new Employee();
        newEmployee.setEmail(jsonEmployee.getEmail());
        newEmployee.setFullName(jsonEmployee.getLastName()+" "+jsonEmployee.getFirstName());
        String[] emailSplit=jsonEmployee.getEmail().split("@");
        newEmployee.setUsername(emailSplit[0]);
        newEmployee.setPassword(passwordEncoder.encode("12345678"));
        newEmployee.setStatus("Enable");
        newEmployee.setRole(Role.USER.getValue());
        //phone-birthday
        return newEmployee;
    }
    public boolean isEmployeeActive(Employee employee,List<JsonEmployee> jsonEmployeeList){
     return   jsonEmployeeList.stream().anyMatch(json->json.getEmail().equals(employee.getEmail()));
    }

}

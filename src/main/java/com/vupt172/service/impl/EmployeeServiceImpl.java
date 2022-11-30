package com.vupt172.service.impl;

import com.vupt172.converter.EmployeeConverter;
import com.vupt172.dto.EmployeeDTO;
import com.vupt172.entity.Employee;
import com.vupt172.exception.DataUniqueException;
import com.vupt172.exception.ElementNotExistException;
import com.vupt172.exception.OverPermissionException;
import com.vupt172.repository.EmployeeRepository;
import com.vupt172.security.service.UserDetailsImpl;
import com.vupt172.service.IEmployeeService;
import com.vupt172.utils.UserDetailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements IEmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public List<EmployeeDTO> findAll() {
        List<EmployeeDTO> employeeDTOS = employeeRepository.findAll().stream()
                .map(e -> EmployeeConverter.toDTO(e)).collect(Collectors.toList());
        return employeeDTOS;
    }

    @Override
    public Optional<EmployeeDTO> findById(Long id) {
        Employee employee = employeeRepository.findById(id).orElse(null);
        EmployeeDTO employeeDTO = null;
        if (employee != null) {
            employeeDTO = EmployeeConverter.toDTO(employee);
        }
        return Optional.ofNullable(employeeDTO);
    }

    @Override
    public EmployeeDTO create(EmployeeDTO employeeDTO) throws DataUniqueException {
        employeeDTO.setId(null);
        //check business logic
        //-check unique
        //--check username
        boolean isExistByUsername=employeeRepository.existsByUsername(employeeDTO.getUsername());
        if(isExistByUsername)
            throw new DataUniqueException("Username is unique");
        //--check phone
        boolean isExistByPhone=employeeRepository.existsByPhone(employeeDTO.getPhone());
        if(isExistByPhone)
            throw new DataUniqueException("Phone is unique");
        //--check email
        boolean isExistByEmail=employeeRepository.existsByEmail(employeeDTO.getEmail());
        if(isExistByEmail)
            throw new DataUniqueException("Email is unique");
        //continue
        Employee employee = EmployeeConverter.toEntity(employeeDTO);
        employee = employeeRepository.save(employee);
        return EmployeeConverter.toDTO(employee);
    }

    @Override
    public EmployeeDTO update(EmployeeDTO employeeDTO) throws ElementNotExistException {
        //check business logic
        //-find Employee
        Employee dbEmployee = employeeRepository.findById(employeeDTO.getId())
                .orElseThrow(() -> new ElementNotExistException("Employee is not exist with id=" + employeeDTO.getId()));
        //-check unique
        Employee updatingEmployee=EmployeeConverter.toEntity(employeeDTO,dbEmployee);
        List<String> uniqueDetails=new ArrayList<>();
        //--check username
        Employee employeeByUsername=employeeRepository.findByUsername(updatingEmployee.getUsername()).orElse(null);
        if(employeeByUsername!=null&&!employeeByUsername.getId().equals(updatingEmployee.getId())){
           uniqueDetails.add("Username is unique");
        }
        //--check email
        Employee employeeByEmail=employeeRepository.findByEmail(updatingEmployee.getEmail()).orElse(null);
        if(employeeByEmail!=null&&!employeeByEmail.getId().equals(updatingEmployee.getId())){
            uniqueDetails.add("Email is unique");
        }
        //--check phone
        Employee employeeByPhone=employeeRepository.findByPhone(updatingEmployee.getPhone()).orElse(null);
        if(employeeByPhone!=null&&!employeeByPhone.getId().equals(updatingEmployee.getId())){
            uniqueDetails.add("Phone is unique");
        }
        if(!uniqueDetails.isEmpty()){
            throw new DataUniqueException(uniqueDetails.toString());
        }
        //continue
        updatingEmployee = employeeRepository.save(updatingEmployee);
        return EmployeeConverter.toDTO(updatingEmployee);
    }

    @Override
    public EmployeeDTO delete(Long id) {
        //check business logic
        //-find Employee
        Employee deletingEmployee=employeeRepository.findById(id)
                .orElseThrow(()->new ElementNotExistException("Employee not exist with id ="+id));
        //-check authentication Role;
        UserDetailsImpl userDetails= (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetailUtil userDetailUtil=new UserDetailUtil(userDetails);
       if(userDetailUtil.hasSuperAdminRole()){
           //-check deletingEmployee role;
           if(deletingEmployee.getRole()==0){
               throw new OverPermissionException("SUPER ADMIN cannot delete own itself");
           }
       }
        else if(userDetailUtil.hasAdminRole()){
           //--check deletingEmployee role;
           if(deletingEmployee.getRole()==0)
               throw new OverPermissionException("ADMIN cannot delete SUPER ADMIN");
           if(deletingEmployee.getRole()==1)
               throw new OverPermissionException("ADMIN cannot delete ADMIN");
       }
        //continue
        employeeRepository.delete(deletingEmployee);
        deletingEmployee.setStatus("Deleted");
        return EmployeeConverter.toDTO(deletingEmployee);
    }
}

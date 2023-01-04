package com.vupt172.service.impl;

import com.vupt172.converter.EmployeeConverter;
import com.vupt172.dto.EmployeeDTO;
import com.vupt172.entity.Employee;
import com.vupt172.exception.DataUniqueException;
import com.vupt172.exception.ElementNotExistException;
import com.vupt172.exception.OverPermissionException;
import com.vupt172.repository.EmployeeInProjectRepository;
import com.vupt172.repository.EmployeeRepository;
import com.vupt172.service.IEmployeeService;
import com.vupt172.utils.AuthenticationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements IEmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    EmployeeConverter employeeConverter;
    @Autowired
    EmployeeInProjectRepository employeeInProjectRepository;
    @Override
    public List<EmployeeDTO> findAll() {
        List<EmployeeDTO> employeeDTOS = employeeRepository.findAll().stream()
                .map(e -> employeeConverter.toDTO(e)).collect(Collectors.toList());
        return employeeDTOS;
    }

    @Override
    public Optional<EmployeeDTO> findById(Long id) {
        Employee employee = employeeRepository.findById(id).orElse(null);
        EmployeeDTO employeeDTO = null;
        if (employee != null) {
            employeeDTO = employeeConverter.toDTO(employee);
        }
        return Optional.ofNullable(employeeDTO);
    }

    @Override
    public EmployeeDTO create(EmployeeDTO employeeDTO) throws DataUniqueException {
        employeeDTO.setId(null);
        //check business logic
        //-check unique
        //--check username
        boolean isExistByUsername = employeeRepository.existsByUsername(employeeDTO.getUsername());
        if (isExistByUsername)
            throw new DataUniqueException("Username is unique");
 /*       //--check phone
        boolean isExistByPhone = employeeRepository.existsByPhone(employeeDTO.getPhone());
        if (isExistByPhone)
            throw new DataUniqueException("Phone is unique");*/
        //--check email
        boolean isExistByEmail = employeeRepository.existsByEmail(employeeDTO.getEmail());
        if (isExistByEmail)
            throw new DataUniqueException("Email is unique");
        //check role
        if(employeeDTO.getRole()==0){
            throw new OverPermissionException("Cannot create employee with SUPERADMIN role");
        }
        //continue
        Employee employee = employeeConverter.toEntity(employeeDTO);
        employee = employeeRepository.save(employee);
        return employeeConverter.toDTO(employee);
    }

    @Override
    public EmployeeDTO update(EmployeeDTO employeeDTO)  {
        //check business logic
        //-find Employee
        Employee dbEmployee = employeeRepository.findById(employeeDTO.getId())
                .orElseThrow(() -> new ElementNotExistException("Employee is not exist with id = " + employeeDTO.getId()));

        Employee updatingEmployee = employeeConverter.toEntity(employeeDTO, dbEmployee);
        //check Role
        AuthenticationUtil authenticationUtil = new AuthenticationUtil(SecurityContextHolder.getContext().getAuthentication());
        //-with Super Admin Role ->cannot down role self
        if (authenticationUtil.hasSuperAdminRole()) {
            //check updating entity=superAdmin itself
            if (updatingEmployee.getUsername().equals(authenticationUtil.getAuthUsername()))
                if (updatingEmployee.getRole() != 0)
                    throw new OverPermissionException("SUPER ADMIN cannot down grade itself.");
        }
        //-with Admin Role
        else if (authenticationUtil.hasAdminRole()) {
            //--check updating enity =superAdmin+admn
            if (dbEmployee.getRole() == 0)
                throw new OverPermissionException("ADMIN cannot update SUPER ADMIN");
            else if (dbEmployee.getRole() == 1)
                throw new OverPermissionException("ADMIN cannot update ADMIN");
        }
        //-SuperAdmin is only
        if (updatingEmployee.getRole() == 0) {
            if(!permissionUpdateToSuperAdminRole(authenticationUtil,dbEmployee,updatingEmployee)){
                throw new OverPermissionException("Super Admin is only");
            }
        }
        //-check unique
        List<String> uniqueDetails = new ArrayList<>();
        //--check username
  /*      Employee employeeByUsername=employeeRepository.findByUsername(updatingEmployee.getUsername()).orElse(null);
        if(employeeByUsername!=null&&!employeeByUsername.getId().equals(updatingEmployee.getId())){
           uniqueDetails.add("Username is unique");
        }*/
        //--check email
        Employee employeeByEmail = employeeRepository.findByEmail(updatingEmployee.getEmail()).orElse(null);
        if (employeeByEmail != null && !employeeByEmail.getId().equals(updatingEmployee.getId())) {
            uniqueDetails.add("Email is unique");
        }
        //--check phone
        Employee employeeByPhone = employeeRepository.findByPhone(updatingEmployee.getPhone()).orElse(null);
        if (employeeByPhone != null && !employeeByPhone.getId().equals(updatingEmployee.getId())) {
            uniqueDetails.add("Phone is unique");
        }
        if (!uniqueDetails.isEmpty()) {
            throw new DataUniqueException(uniqueDetails.toString());
        }

        //continue
        updatingEmployee = employeeRepository.save(updatingEmployee);
        return employeeConverter.toDTO(updatingEmployee);
    }

    @Override
    public EmployeeDTO delete(Long id) {
        //check business logic
        //-find Employee
        Employee deletingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new ElementNotExistException("Employee not exist with id =" + id));
        //-check authentication Role;
        AuthenticationUtil userDetailUtil = new AuthenticationUtil(SecurityContextHolder.getContext().getAuthentication());
        if (userDetailUtil.hasSuperAdminRole()) {
            //-check deletingEmployee role;
            if (deletingEmployee.getRole() == 0) {
                throw new OverPermissionException("SUPER ADMIN cannot delete own itself");
            }
        } else if (userDetailUtil.hasAdminRole()) {
            //--check deletingEmployee role;
            if (deletingEmployee.getRole() == 0)
                throw new OverPermissionException("ADMIN cannot delete SUPER ADMIN");
            if (deletingEmployee.getRole() == 1)
                throw new OverPermissionException("ADMIN cannot delete ADMIN");
        }
        //soft delete
        boolean hasEmployeeInProject =employeeInProjectRepository.existsByEmployee_Id(id);
        if(hasEmployeeInProject){
            deletingEmployee.setStatus("Disable");
            employeeRepository.save(deletingEmployee);
            return employeeConverter.toDTO(deletingEmployee);
        }
        //continue
        employeeRepository.delete(deletingEmployee);
        deletingEmployee.setStatus("Deleted");
        return employeeConverter.toDTO(deletingEmployee);
    }

    boolean permissionUpdateToSuperAdminRole(AuthenticationUtil authenticationUtil, Employee dbEmployee, Employee updatingEmployee) {
        //only Super Admin can update itselft to SuperAdmin
        if(authenticationUtil.hasSuperAdminRole()){
            if(authenticationUtil.getAuthUsername().equals(dbEmployee.getUsername()))return true;
        }
        return false;
    }
}

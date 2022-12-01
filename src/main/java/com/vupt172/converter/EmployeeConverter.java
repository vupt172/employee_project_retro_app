package com.vupt172.converter;

import com.vupt172.dto.EmployeeDTO;
import com.vupt172.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class EmployeeConverter {
    @Autowired
    PasswordEncoder passwordEncoder;
    public  Employee toEntity(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setId(employeeDTO.getId());
        employee.setUsername(employeeDTO.getUsername());
        employee.setFullName(employeeDTO.getFullName());
        employee.setPassword(passwordEncoder.encode(employeeDTO.getPassword()));
        employee.setEmail(employeeDTO.getEmail());
        employee.setPhone(employeeDTO.getPhone());
        employee.setStatus(employeeDTO.getStatus());
        employee.setRole(employeeDTO.getRole());
        employee.setBirthDay(employeeDTO.getBirthDay());
        return employee;
    }

    public static EmployeeDTO toDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setFullName(employee.getFullName());
        employeeDTO.setUsername(employee.getUsername());
        employeeDTO.setRole(employee.getRole());
        employeeDTO.setPassword(employee.getPassword());
        employeeDTO.setBirthDay(employee.getBirthDay());
        employeeDTO.setEmail(employee.getEmail());
        employeeDTO.setPhone(employee.getPhone());
        employeeDTO.setStatus(employee.getStatus());
        return employeeDTO;
    }
/**
 * merge new dto and db employee to updating info
 * */
    public  Employee toEntity(EmployeeDTO employeeDTO, Employee dbEmployee) {
        Employee updatingEmployee = new Employee();
        updatingEmployee.setId(dbEmployee.getId());
        //cannot change username
        updatingEmployee.setUsername(dbEmployee.getUsername());
        updatingEmployee.setPassword(passwordEncoder.encode(employeeDTO.getPassword()));
        updatingEmployee.setFullName(employeeDTO.getFullName());
        updatingEmployee.setEmail(employeeDTO.getEmail());
        updatingEmployee.setPhone(employeeDTO.getPhone());
        updatingEmployee.setBirthDay(employeeDTO.getBirthDay());
        updatingEmployee.setRole(employeeDTO.getRole());
        updatingEmployee.setStatus(employeeDTO.getStatus());
        return updatingEmployee;
    }
}

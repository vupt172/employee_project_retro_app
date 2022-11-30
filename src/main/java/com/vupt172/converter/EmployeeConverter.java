package com.vupt172.converter;

import com.vupt172.dto.EmployeeDTO;
import com.vupt172.entity.Employee;

public class EmployeeConverter {
    public static Employee toEntity(EmployeeDTO employeeDTO){
        Employee employee=new Employee();
        employee.setId(employeeDTO.getId());
        employee.setUsername(employeeDTO.getUsername());
        employee.setFullName(employeeDTO.getFullName());
        employee.setPassword(employeeDTO.getPassword());
        employee.setEmail(employeeDTO.getEmail());
        employee.setPhone(employeeDTO.getPhone());
        employee.setStatus(employeeDTO.getStatus());
        employee.setRole(employeeDTO.getRole());
        employee.setBirthDay(employeeDTO.getBirthDay());
   return employee;
    }
    public static EmployeeDTO toDTO(Employee employee){
        EmployeeDTO employeeDTO=new EmployeeDTO();
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
    public static Employee toEntity(EmployeeDTO employeeDTO,Employee dbEmployee){
    dbEmployee.setUsername(employeeDTO.getUsername());
    dbEmployee.setPassword(employeeDTO.getPassword());
    dbEmployee.setFullName(employeeDTO.getFullName());
    dbEmployee.setEmail(employeeDTO.getEmail());
    dbEmployee.setPhone(employeeDTO.getPhone());
    dbEmployee.setBirthDay(employeeDTO.getBirthDay());
    dbEmployee.setRole(employeeDTO.getRole());
    dbEmployee.setStatus(employeeDTO.getStatus());
    return dbEmployee;
    }
}

package com.vupt172.service;

import com.vupt172.dto.EmployeeDTO;
import com.vupt172.entity.Employee;
import com.vupt172.exception.DataUniqueException;
import com.vupt172.exception.ElementNotExistException;

import java.util.List;
import java.util.Optional;

public interface IEmployeeService {
    List<EmployeeDTO> findAll();
    Optional<EmployeeDTO> findById(Long id);
    EmployeeDTO create(EmployeeDTO employeeDTO);
    EmployeeDTO update(EmployeeDTO employeeDTO) ;
    EmployeeDTO delete(Long id);
}

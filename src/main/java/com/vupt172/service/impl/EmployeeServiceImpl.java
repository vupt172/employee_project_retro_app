package com.vupt172.service.impl;

import com.vupt172.converter.EmployeeConverter;
import com.vupt172.dto.EmployeeDTO;
import com.vupt172.entity.Employee;
import com.vupt172.exception.DataUniqueException;
import com.vupt172.exception.ElementNotExistException;
import com.vupt172.repository.EmployeeRepository;
import com.vupt172.security.service.UserDetailsImpl;
import com.vupt172.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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
        //-checkRole
        UserDetailsImpl userDetails= (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //continue
        Employee employee = EmployeeConverter.toEntity(employeeDTO);
        employee = employeeRepository.save(employee);
        return EmployeeConverter.toDTO(employee);
    }

    @Override
    public EmployeeDTO update(EmployeeDTO employeeDTO) throws ElementNotExistException {
        Employee dbEmployee = employeeRepository.findById(employeeDTO.getId())
                .orElseThrow(() -> new ElementNotExistException("Employee is not exist with id=" + employeeDTO.getId()));
        Employee updatingEmployee = EmployeeConverter.toEntity(employeeDTO, dbEmployee);
        updatingEmployee = employeeRepository.save(updatingEmployee);
        return EmployeeConverter.toDTO(updatingEmployee);
    }
}

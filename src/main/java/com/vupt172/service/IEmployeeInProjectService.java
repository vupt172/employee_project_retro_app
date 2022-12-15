package com.vupt172.service;

import com.vupt172.dto.EmployeeInProjectDTO;

import java.util.List;
import java.util.Optional;

public interface IEmployeeInProjectService {
    List<EmployeeInProjectDTO> findAll(Long projectId);
    Optional<EmployeeInProjectDTO> findEmployeeInProjectById(Long projectId,Long employeeId);
    EmployeeInProjectDTO createEmployeeInProject(EmployeeInProjectDTO employeeInProjectDTO);
    EmployeeInProjectDTO updateEmployeeInProject(EmployeeInProjectDTO employeeInProjectDTO);
    EmployeeInProjectDTO deleteEmployeeInProject(Long projectId,Long employeeId);
}

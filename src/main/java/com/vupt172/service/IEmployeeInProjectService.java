package com.vupt172.service;

import com.vupt172.dto.EmployeeInProjectDTO;
import com.vupt172.entity.Employee;
import com.vupt172.entity.EmployeeInProject;

import java.util.*;

public interface IEmployeeInProjectService {
    List<EmployeeInProject> findAll(Long projectId);
    Optional<EmployeeInProjectDTO> findEmployeeInProjectById(Long projectId,Long employeeId);
    EmployeeInProjectDTO createEmployeeInProject(EmployeeInProjectDTO employeeInProjectDTO);
}

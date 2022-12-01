package com.vupt172.service.impl;

import com.vupt172.converter.EmployeeInProjectConverter;
import com.vupt172.dto.EmployeeInProjectDTO;
import com.vupt172.entity.Employee;
import com.vupt172.entity.EmployeeInProject;
import com.vupt172.entity.Project;
import com.vupt172.entity.ProjectRole;
import com.vupt172.exception.ElementNotExistException;
import com.vupt172.repository.EmployeeInProjectRepository;
import com.vupt172.repository.EmployeeRepository;
import com.vupt172.repository.ProjectRepository;
import com.vupt172.repository.ProjectRoleRepository;
import com.vupt172.service.IEmployeeInProjectService;
import com.vupt172.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeInProjectServiceImpl implements IEmployeeInProjectService {
    @Autowired
    EmployeeInProjectRepository employeeInProjectRepository;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    ProjectRoleRepository projectRoleRepository;
    @Autowired
    EmployeeInProjectConverter employeeInProjectConverter;


    @Override
    public List<EmployeeInProject> findAll(Long projectId) {
       Project project=projectRepository.findById(projectId)
               .orElseThrow(()->new ElementNotExistException("Project not exist with id="+projectId));
       //List<Employee> employees=
        //return employeeInProjectRepository.find;
        return null;
    }

    @Override
    public Optional<EmployeeInProjectDTO> findEmployeeInProjectById(Long projectId, Long employeeId) {
        return Optional.empty();
    }

    @Override
    public EmployeeInProjectDTO createEmployeeInProject(EmployeeInProjectDTO employeeInProjectDTO) {
        EmployeeInProject employeeInProject = new EmployeeInProject();
        Project project = projectRepository.findById(employeeInProjectDTO.getProjectId())
                .orElseThrow(() -> new ElementNotExistException("Project not exist with id =" + employeeInProjectDTO.getProjectId()));
        Employee employee = employeeRepository.findByUsername(employeeInProjectDTO.getUsername())
                .orElseThrow(() -> new ElementNotExistException("Employee not exist with username=" + employeeInProjectDTO.getUsername()));
        ProjectRole projectRole = projectRoleRepository.findByName(employeeInProjectDTO.getProjectRole())
                .orElseThrow(() -> new ElementNotExistException("Project Role not exist with name=" + employeeInProjectDTO.getProjectRole()));
        employeeInProject.setProject(project);
        employeeInProject.setEmployee(employee);
        employeeInProject.setProjectRole(projectRole);
      employeeInProject=  employeeInProjectRepository.save(employeeInProject);
        return employeeInProjectConverter.toDTO(employeeInProject);
    }

}

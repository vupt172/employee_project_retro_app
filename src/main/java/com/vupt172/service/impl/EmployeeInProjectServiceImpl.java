package com.vupt172.service.impl;

import com.vupt172.converter.EmployeeInProjectConverter;
import com.vupt172.dto.EmployeeInProjectDTO;
import com.vupt172.entity.Employee;
import com.vupt172.entity.EmployeeInProject;
import com.vupt172.entity.Project;
import com.vupt172.entity.ProjectRole;
import com.vupt172.exception.ElementNotExistException;
import com.vupt172.exception.EmployeeAlreadyExistInProjectException;
import com.vupt172.repository.EmployeeInProjectRepository;
import com.vupt172.repository.EmployeeRepository;
import com.vupt172.repository.ProjectRepository;
import com.vupt172.repository.ProjectRoleRepository;
import com.vupt172.service.IEmployeeInProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public List<EmployeeInProjectDTO> findAll(Long projectId) {
       Project project=projectRepository.findById(projectId)
               .orElseThrow(()->new ElementNotExistException("Project not exist with id="+projectId));
       List<EmployeeInProject> employees=employeeInProjectRepository.findByProject_Id(projectId);
       return employees.stream().map(e->employeeInProjectConverter.toDTO(e)).collect(Collectors.toList());
    }

    @Override
    public Optional<EmployeeInProjectDTO> findEmployeeInProjectById(Long projectId, Long employeeId) {
        Project project=projectRepository.findById(projectId)
                .orElseThrow(()->new ElementNotExistException("Project not exist with id="+projectId));
        EmployeeInProject employeeInProject=employeeInProjectRepository.findByProject_IdAndEmployee_Id(projectId,employeeId)
                .orElse(null);
        EmployeeInProjectDTO employeeInProjectDTO=null;
        if(employeeInProject!=null)employeeInProjectDTO=employeeInProjectConverter.toDTO(employeeInProject);
        return Optional.ofNullable(employeeInProjectDTO);
    }

    @Override
    public EmployeeInProjectDTO createEmployeeInProject(EmployeeInProjectDTO employeeInProjectDTO) {
        EmployeeInProject employeeInProject = new EmployeeInProject();
        Project project = projectRepository.findById(employeeInProjectDTO.getProjectId())
                .orElseThrow(() -> new ElementNotExistException("Project not exist with id =" + employeeInProjectDTO.getProjectId()));
        Employee employee = employeeRepository.findById(employeeInProjectDTO.getEmployeeId())
                .orElseThrow(() -> new ElementNotExistException("Employee not exist with id=" + employeeInProjectDTO.getEmployeeId()));
        ProjectRole projectRole = projectRoleRepository.findByName(employeeInProjectDTO.getProjectRole())
                .orElseThrow(() -> new ElementNotExistException("Project Role not exist with name=" + employeeInProjectDTO.getProjectRole()));
        EmployeeInProject employeeExistInProject=employeeInProjectRepository.findByProject_IdAndEmployee_Id(employeeInProjectDTO.getProjectId(),employeeInProjectDTO.getEmployeeId()).orElse(null);
        if(employeeExistInProject!=null){
            throw new EmployeeAlreadyExistInProjectException("Employee with id = "+employeeInProjectDTO.getEmployeeId()+" already exist in project");
        }

        employeeInProject.setProject(project);
        employeeInProject.setEmployee(employee);
        employeeInProject.setProjectRole(projectRole);
      employeeInProject=  employeeInProjectRepository.save(employeeInProject);
        return employeeInProjectConverter.toDTO(employeeInProject);
    }

    @Override
    public EmployeeInProjectDTO updateEmployeeInProject(EmployeeInProjectDTO employeeInProjectDTO) {
        Project project=projectRepository.findById(employeeInProjectDTO.getProjectId())
                .orElseThrow(()->new ElementNotExistException("Project not exist with id ="+employeeInProjectDTO.getProjectId()));
        EmployeeInProject employeeExistInProject=employeeInProjectRepository.findByProject_IdAndEmployee_Id(employeeInProjectDTO.getProjectId(),employeeInProjectDTO.getEmployeeId())
                .orElseThrow(()->new ElementNotExistException("Employee with id = "+employeeInProjectDTO.getEmployeeId()+" not exist in project"));
        ProjectRole projectRole=projectRoleRepository.findByName(employeeInProjectDTO.getProjectRole())
                .orElseThrow(()->new ElementNotExistException("Project role not exist with name ="+employeeInProjectDTO.getProjectRole()));
        employeeExistInProject.setProjectRole(projectRole);
        employeeInProjectRepository.save(employeeExistInProject);
        return employeeInProjectConverter.toDTO(employeeExistInProject);
    }

}

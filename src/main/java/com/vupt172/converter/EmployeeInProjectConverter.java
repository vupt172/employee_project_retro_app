package com.vupt172.converter;

import com.vupt172.dto.EmployeeInProjectDTO;
import com.vupt172.entity.EmployeeInProject;
import org.springframework.stereotype.Component;

@Component
public class EmployeeInProjectConverter {
   public  EmployeeInProjectDTO toDTO(EmployeeInProject employeeInProject){
        EmployeeInProjectDTO employeeInProjectDTO=new EmployeeInProjectDTO();
        employeeInProjectDTO.setEmployeeId(employeeInProject.getEmployee().getId());
        employeeInProjectDTO.setProjectId(employeeInProject.getProject().getId());
       // employeeInProjectDTO.setUsername(employeeInProject.getEmployee().getUsername());
        employeeInProjectDTO.setProjectRole(employeeInProject.getProjectRole().getName());
        employeeInProjectDTO.setStatus(employeeInProject.getStatus());
        return employeeInProjectDTO;
    }
    public EmployeeInProject toEntity(EmployeeInProjectDTO employeeInProjectDTO){
       EmployeeInProject employeeInProject=new EmployeeInProject();
       employeeInProject.setStatus(employeeInProjectDTO.getStatus());
       return employeeInProject;
    }
    //merge when update;
    public EmployeeInProject toEntity(EmployeeInProjectDTO employeeInProjectDTO,EmployeeInProject dbEmployeeInProject){
       EmployeeInProject employeeInProject=new EmployeeInProject();
       employeeInProject.setEmployee(dbEmployeeInProject.getEmployee());
       employeeInProject.setProject(dbEmployeeInProject.getProject());
       employeeInProject.setStatus(employeeInProjectDTO.getStatus());
       return employeeInProject;
    }
}

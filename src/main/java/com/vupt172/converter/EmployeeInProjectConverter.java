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
}

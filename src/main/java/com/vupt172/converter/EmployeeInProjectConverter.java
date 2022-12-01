package com.vupt172.converter;

import com.vupt172.dto.EmployeeInProjectDTO;
import com.vupt172.entity.EmployeeInProject;
import org.springframework.stereotype.Component;

import javax.crypto.MacSpi;

@Component
public class EmployeeInProjectConverter {
   public  EmployeeInProjectDTO toDTO(EmployeeInProject employeeInProject){
        EmployeeInProjectDTO employeeInProjectDTO=new EmployeeInProjectDTO();
        employeeInProjectDTO.setProjectId(employeeInProject.getProject().getId());
        employeeInProjectDTO.setUsername(employeeInProject.getEmployee().getUsername());
        employeeInProjectDTO.setProjectRole(employeeInProject.getProjectRole().getName());
        return employeeInProjectDTO;
    }
}

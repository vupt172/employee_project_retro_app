package com.vupt172.controller;

import com.vupt172.dto.EmployeeInProjectDTO;
import com.vupt172.service.IEmployeeInProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects/{projectId}/employees")
public class EmployeeInProjectController {
    @Autowired
    IEmployeeInProjectService employeeInProjectService;
    @GetMapping
    public EmployeeInProjectDTO getAllEmployeeInProject(@PathVariable Long projectId){
return null;
    }
    @PostMapping EmployeeInProjectDTO addEmployeeToProject(@PathVariable Long projectId,@RequestBody  EmployeeInProjectDTO employeeInProjectDTO){
       employeeInProjectDTO.setProjectId(projectId);
        return employeeInProjectService.createEmployeeInProject(employeeInProjectDTO);
    }
}

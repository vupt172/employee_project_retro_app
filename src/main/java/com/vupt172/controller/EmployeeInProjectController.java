package com.vupt172.controller;

import com.vupt172.dto.EmployeeInProjectDTO;
import com.vupt172.repository.EmployeeInProjectRepository;
import com.vupt172.repository.EmployeeRepository;
import com.vupt172.service.IEmployeeInProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects/{projectId}/employees")
public class EmployeeInProjectController {
    @Autowired
    IEmployeeInProjectService employeeInProjectService;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    EmployeeInProjectRepository employeeInProjectRepository;

    @GetMapping
    public List<EmployeeInProjectDTO> getAllEmployeeInProject(@PathVariable Long projectId){
        return employeeInProjectService.findAll(projectId);
    }
    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeInProjectDTO> getEmployeeInProjectById(@PathVariable Long projectId, @PathVariable Long employeeId){
        EmployeeInProjectDTO employeeInProjectDTO=employeeInProjectService.findEmployeeInProjectById(projectId,employeeId).orElse(null);
        if(employeeInProjectDTO==null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(employeeInProjectDTO);
    }
    @PostMapping ResponseEntity<EmployeeInProjectDTO> addEmployeeToProject(@PathVariable Long projectId,@RequestBody  EmployeeInProjectDTO employeeInProjectDTO){
       employeeInProjectDTO.setProjectId(projectId);
        EmployeeInProjectDTO returnDTO=employeeInProjectService.createEmployeeInProject(employeeInProjectDTO);
        return ResponseEntity.ok(returnDTO);
    }
    @PutMapping("/{employeeId}")
    public ResponseEntity<EmployeeInProjectDTO> updateEmployeeInProject(@PathVariable Long projectId,@PathVariable Long employeeId,@RequestBody EmployeeInProjectDTO employeeInProjectDTO){
        employeeInProjectDTO.setProjectId(projectId);
        employeeInProjectDTO.setEmployeeId(employeeId);
        EmployeeInProjectDTO returnDTO=employeeInProjectService.updateEmployeeInProject(employeeInProjectDTO);
        return ResponseEntity.ok(returnDTO);
    }
    @DeleteMapping("/{employeeId}")
    public ResponseEntity<EmployeeInProjectDTO> deleteEmployeeInProject(@PathVariable Long projectId, @PathVariable Long employeeId){
     return ResponseEntity.ok(employeeInProjectService.deleteEmployeeInProject(projectId,employeeId));
    }
}

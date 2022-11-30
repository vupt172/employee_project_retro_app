package com.vupt172.controller;

import com.vupt172.dto.ProjectDTO;
import com.vupt172.exception.ElementNotExistException;
import com.vupt172.exception.DataUniqueException;
import com.vupt172.service.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    @Autowired
    private IProjectService projectService;
    @GetMapping("")
    public List<ProjectDTO> getAll() {
       return projectService.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO> getProjectById(@PathVariable Long id){
     ProjectDTO projectDTO=projectService.findById(id).orElse(null);
     if(projectDTO==null)
         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
     return ResponseEntity.ok(projectDTO);
    }
    @PostMapping("")
    public ResponseEntity<ProjectDTO> createProject(@RequestBody ProjectDTO projectDTO)  {
        return ResponseEntity.ok(projectService.create(projectDTO));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ProjectDTO> updateProject(@PathVariable Long id,@RequestBody ProjectDTO projectDTO)  {
        projectDTO.setId(id);
        return ResponseEntity.ok(projectService.update(projectDTO));
      /*  throw new ElementNotExistException("element not exist");*/
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ProjectDTO> deleteProject(@PathVariable Long id) throws ElementNotExistException{
        return ResponseEntity.ok(projectService.delete(id));
    }

}

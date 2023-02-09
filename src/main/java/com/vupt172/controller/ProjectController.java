package com.vupt172.controller;

import com.vupt172.dto.ProjectDTO;
import com.vupt172.entity.Project;
import com.vupt172.exception.ElementNotExistException;
import com.vupt172.service.itf.IProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/projects")
@Tag(name = "Project API")
@SecurityRequirement(name = "bearerAuth")
public class ProjectController {

    @Autowired
    private IProjectService projectService;

    @Operation(description = "Retrieve project list", responses = {
            @ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Project.class))), responseCode = "200")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Thành công"),

    })
    @GetMapping("")
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.ok(projectService.findAll());
    }

    @Operation(description = "Find project by Id", responses = {
            @ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Project.class))), responseCode = "200")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "400", description = "Invalid Id")

    })
    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO> getProjectById(@Parameter(description ="id of project want to find" ) @PathVariable Long id) {
        ProjectDTO projectDTO = projectService.findById(id).orElse(null);
        if (projectDTO == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(projectDTO);
    }
    @Operation(description = "Create new project", responses = {
            @ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Project.class))), responseCode = "200")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "409", description = "Project exist by name"),
            @ApiResponse(responseCode = "400", description = "Invalid Request Body")
    })

    @PostMapping("")
    public ResponseEntity<ProjectDTO> createProject(@Valid @RequestBody ProjectDTO projectDTO) {
        return ResponseEntity.ok(projectService.create(projectDTO));
    }
    @Operation(description = "Update an exist project", responses = {
            @ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Project.class))), responseCode = "200")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "409", description = "Project exist by name"),
            @ApiResponse(responseCode = "400", description = "Project not exist by Id | Invalid Id | Invalid Request Body")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProjectDTO> updateProject(@Parameter(description ="id of project" )@PathVariable Long id, @Valid @RequestBody ProjectDTO projectDTO) {
        projectDTO.setId(id);
        return ResponseEntity.ok(projectService.update(projectDTO));
        /*  throw new ElementNotExistException("element not exist");*/
    }
    @Operation(description = "Delete a project", responses = {
            @ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Project.class))), responseCode = "200")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Project not exist by Id| Invalid Id")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ProjectDTO> deleteProject(@Parameter(description ="id of project" )@PathVariable Long id) throws ElementNotExistException {
        return ResponseEntity.ok(projectService.delete(id));
    }

}

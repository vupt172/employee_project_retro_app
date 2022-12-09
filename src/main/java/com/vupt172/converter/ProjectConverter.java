package com.vupt172.converter;

import com.vupt172.dto.ProjectDTO;
import com.vupt172.entity.Project;
import org.springframework.stereotype.Component;

@Component
public class ProjectConverter {
    public String message="project converter";
    public  ProjectDTO toDTO(Project project) {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(project.getId());
        projectDTO.setName(project.getName());
        projectDTO.setDescription(project.getDescription());
        projectDTO.setStatus(project.getStatus());
        return projectDTO;
    }

    public  Project toEntity(ProjectDTO projectDTO) {
        Project project = new Project();
        project.setId(projectDTO.getId());
        project.setName(projectDTO.getName());
        project.setDescription(projectDTO.getDescription());
        project.setStatus(projectDTO.getStatus());

        return project;
    }

    public  Project toEntity(ProjectDTO projectDTO, Project dbProject) {
        dbProject.setName(projectDTO.getName());
        dbProject.setDescription(projectDTO.getDescription());
        dbProject.setStatus(projectDTO.getStatus());
            dbProject.setStatus(projectDTO.getStatus());
        return dbProject;
    }
}

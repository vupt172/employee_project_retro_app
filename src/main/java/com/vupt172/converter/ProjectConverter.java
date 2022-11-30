package com.vupt172.converter;

import com.vupt172.dto.ProjectDTO;
import com.vupt172.entity.Project;

public class ProjectConverter {
    public static ProjectDTO toDTO(Project project) {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(project.getId());
        projectDTO.setName(project.getName());
        projectDTO.setDescription(project.getDescription());
        projectDTO.setStatus(project.getStatus());
        return projectDTO;
    }

    public static Project toEntity(ProjectDTO projectDTO) {
        Project project = new Project();
        project.setId(projectDTO.getId());
        project.setName(projectDTO.getName());
        project.setDescription(projectDTO.getDescription());
        project.setStatus(projectDTO.getStatus());

        return project;
    }

    public static Project toEntity(ProjectDTO projectDTO, Project dbProject) {
        dbProject.setName(projectDTO.getName());
        dbProject.setDescription(projectDTO.getDescription());
        dbProject.setStatus(projectDTO.getStatus());
            dbProject.setStatus(projectDTO.getStatus());
        return dbProject;
    }
}

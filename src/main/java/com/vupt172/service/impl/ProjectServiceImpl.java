package com.vupt172.service.impl;

import com.vupt172.converter.ProjectConverter;
import com.vupt172.dto.ProjectDTO;
import com.vupt172.entity.Project;
import com.vupt172.exception.ElementNotExistException;
import com.vupt172.exception.DataUniqueException;
import com.vupt172.repository.ProjectRepository;
import com.vupt172.service.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements IProjectService {
    @Autowired
    ProjectRepository projectRepository;

    @Override
    public List<ProjectDTO> findAll() {
        List<Project> projectList=projectRepository.findAll();
        List<ProjectDTO> projectDTOS =projectList.stream()
                .map(p -> ProjectConverter.toDTO(p)).collect(Collectors.toList());
        return projectDTOS;
    }

    @Override
    public Optional<ProjectDTO> findById(Long id) {
        Project project = projectRepository.findById(id).orElse(null);
        ProjectDTO projectDTO = null;
        if (project != null) {
            projectDTO = ProjectConverter.toDTO(project);
        }
        return Optional.ofNullable(projectDTO);
    }

    @Override
    public ProjectDTO create(ProjectDTO projectDTO)  {
        projectDTO.setId(null);
        //check business logic
        //-unique
       boolean isExistByName= projectRepository.existsByName(projectDTO.getName());
       if(isExistByName)throw new DataUniqueException("Project name is unique");
        //continue
        Project newProject = ProjectConverter.toEntity(projectDTO);
        newProject = projectRepository.save(newProject);
        return ProjectConverter.toDTO(newProject);
    }

    @Override
    public ProjectDTO update(ProjectDTO projectDTO)  {
        //check business logic
        //-find project
        Project oldProject = projectRepository.findById(projectDTO.getId())
                .orElseThrow(() -> new ElementNotExistException("Project is not exist with id=" + projectDTO.getId()));
        //-check unique
        Project updatingProject = ProjectConverter.toEntity(projectDTO, oldProject);
        Project projectByName=projectRepository.findByName(updatingProject.getName()).orElse(null);
        if(projectByName!=null&&!projectByName.getId().equals(updatingProject.getId())){
            throw new DataUniqueException("Project name is unique");
        }
        //continue
        updatingProject = projectRepository.save(updatingProject);
        return ProjectConverter.toDTO(updatingProject);
    }

    @Override
    public ProjectDTO delete(Long id) throws ElementNotExistException {
        Project deletingProject = projectRepository.findById(id)
                .orElseThrow(() -> new ElementNotExistException("Project id not exist with id=" + id));
        projectRepository.delete(deletingProject);
        deletingProject.setStatus("Deleted");
        return ProjectConverter.toDTO(deletingProject);
    }


}

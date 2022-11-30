package com.vupt172.service;

import com.vupt172.dto.ProjectDTO;
import com.vupt172.exception.ElementNotExistException;
import com.vupt172.exception.DataUniqueException;

import java.util.List;
import java.util.Optional;


public interface IProjectService {
    List<ProjectDTO> findAll();

    Optional<ProjectDTO> findById(Long id);
    ProjectDTO create(ProjectDTO projectDTO) ;
    ProjectDTO update(ProjectDTO projectDTO) ;
    ProjectDTO delete (Long id) throws ElementNotExistException;
}

package com.vupt172.service;

import com.vupt172.dto.ProjectDTO;
import com.vupt172.entity.Project;
import com.vupt172.repository.ProjectRepository;
import com.vupt172.service.impl.ProjectServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.*;

@ExtendWith(MockitoExtension.class)
//@RunWith(MockitoJUnitRunner.class)
public class ProjectServiceTest {
    @Mock
    ProjectRepository projectRepository;
    @InjectMocks
    ProjectServiceImpl projectService;
    @Test
    void whenFindAll_shouldReturnList(){
        //1.createMockData
        List<Project> projectList=new ArrayList<>();
        projectList.add(new Project(1L,"Project1","","Enable"));
        projectList.add(new Project(1L,"Project2","","Enable"));
        projectList.add(new Project(1L,"Project3","","Enable"));
        //2. define behavior or Repository
        Mockito.when(projectRepository.findAll()).thenReturn(projectList);
        //3. call service method
        List<ProjectDTO> dtoList=projectService.findAll();
        //4. check the result
        Assertions.assertTrue(dtoList.size()==projectList.size());
        Mockito.verify(projectRepository).findAll();

    }

}

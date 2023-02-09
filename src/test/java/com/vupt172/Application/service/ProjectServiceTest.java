package com.vupt172.Application.service;

import com.vupt172.converter.ProjectConverter;
import com.vupt172.dto.ProjectDTO;
import com.vupt172.entity.Project;
import com.vupt172.exception.DataUniqueException;
import com.vupt172.exception.ElementNotExistException;
import com.vupt172.repository.EmployeeInProjectRepository;
import com.vupt172.repository.ProjectRepository;
import com.vupt172.service.ProjectServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
//@RunWith(MockitoJUnitRunner.class)
public class ProjectServiceTest {
    @Mock
    ProjectRepository projectRepository;
    @Mock
    EmployeeInProjectRepository employeeInProjectRepository;
    @Spy
    ProjectConverter projectConverter;
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
    @Test
    void whenFindById_returnOptional_Empty(){
        //setup
        Long projectId=-1L;
        Mockito.when(projectRepository.findById(projectId)).thenReturn(Optional.empty());
        //test
        Optional<ProjectDTO> dto=projectService.findById(projectId);
        Assertions.assertTrue(dto.isEmpty());
        Mockito.verify(projectRepository).findById(projectId);
    }
    @Test
    void whenFindById_returnOptional_Present(){
        //setup
        Project p=new Project(1L,"Project1","","Enable");
        Mockito.when(projectRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(p));
        //test
       Optional<ProjectDTO> dto=projectService.findById(Mockito.anyLong());
       Assertions.assertTrue(dto.isPresent());
       Mockito.verify(projectRepository).findById(Mockito.anyLong());
    }
    @Test
    void whenCreate_WithExistName_ThrowDataUniqueException(){
        //set up
        Mockito.when(projectRepository.existsByName(Mockito.anyString())).thenReturn(true);
        ProjectDTO projectDTO=new ProjectDTO();
        projectDTO.setName("Project ABC");
        projectDTO.setStatus("Enable");
        DataUniqueException exception=Assertions.assertThrows(DataUniqueException.class,()-> projectService.create(projectDTO));
   String expectedMessage="Project name is unique";
   String actualMessage=exception.getMessage();
   Assertions.assertTrue(expectedMessage.equals(actualMessage));
    }
    @Test
    void whenCreate_WithNotExistName_ThenSuccess(){
       //set up
        ProjectDTO projectDTO=new ProjectDTO();
        projectDTO.setName("Project ABC");
        projectDTO.setStatus("Enable");
        Project savingProject=projectConverter.toEntity(projectDTO);
        savingProject.setId(1L);
        Mockito.when(projectRepository.existsByName(Mockito.anyString())).thenReturn(false);
        Mockito.when(projectRepository.save(Mockito.any(Project.class))).thenReturn(savingProject);
        //test
        ProjectDTO creatingDTO=projectService.create(projectDTO);
        Assertions.assertTrue(creatingDTO.equals(projectConverter.toDTO(savingProject)));
    }
    @Test
    void whenUpdate_WithNotExistId_ThrowElementNotExistException(){
        //setup
        Mockito.when(projectRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        ProjectDTO projectDTO=new ProjectDTO();
        projectDTO.setId(1L);
        projectDTO.setName("Project ABC");
        projectDTO.setDescription("...");
        projectDTO.setStatus("Enable");
        ElementNotExistException exception=Assertions.assertThrows(ElementNotExistException.class,()->projectService.update(projectDTO));
    }
    @Test
    void whenUpdate_WithExistNameOfOtherProjects_ThrowwDataUniqueException(){
        //setup
        Project dbProject=new Project(1L,"Project A1","","Enable");
        ProjectDTO projectDTO=new ProjectDTO();
        projectDTO.setId(1L);
        projectDTO.setName("Project A2");
        projectDTO.setDescription("project A2");
        projectDTO.setStatus("Enable");
        Project existProjectByName=new Project(2L,"Project A2","","Enable");
        Mockito.when(projectRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(dbProject));
        Mockito.when(projectRepository.findByName(Mockito.anyString())).thenReturn(Optional.of(existProjectByName)) ;
        //test
        DataUniqueException exception=Assertions.assertThrows(DataUniqueException.class,()->projectService.update(projectDTO));
    }
    @Test
    void whenUpdate_WithExistNameSelfProject_ThenSuccess(){
        //setup
        Project dbProject=new Project(1L,"Project A1","","Enable");
        ProjectDTO projectDTO=new ProjectDTO();
        projectDTO.setId(1L);
        projectDTO.setName("Project A1");
        projectDTO.setDescription("project A1");
        projectDTO.setStatus("Enable");
        Project existProjectByName=new Project(1L,"Project A1","","Enable");
        Mockito.when(projectRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(dbProject));
        Mockito.when(projectRepository.findByName(Mockito.anyString())).thenReturn(Optional.of(existProjectByName)) ;
        Project updatingProject=projectConverter.toEntity(projectDTO,dbProject);
        Mockito.when(projectRepository.save(Mockito.any(Project.class))).thenReturn(updatingProject);
        //test
        ProjectDTO returnDTO=projectService.update(projectDTO);
        Assertions.assertTrue(returnDTO.equals(projectDTO));
    }
    @Test
    void whenUpdate_WithNewProjectName_ThenSuccess(){
        Project dbProject=new Project(1L,"Project A1","","Enable");
        ProjectDTO projectDTO=new ProjectDTO();
        projectDTO.setId(1L);
        projectDTO.setName("Project A11");
        projectDTO.setDescription("project A11");
        projectDTO.setStatus("Enable");
        Mockito.when(projectRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(dbProject));
        Mockito.when(projectRepository.findByName(Mockito.anyString())).thenReturn(Optional.empty());
        Project updatingProject=projectConverter.toEntity(projectDTO,dbProject);
        Mockito.when(projectRepository.save(Mockito.any(Project.class))).thenReturn(updatingProject);
       //test
        ProjectDTO returnDTO=projectService.update(projectDTO);
        Assertions.assertTrue(returnDTO.equals(projectDTO));
    }
   @Test
    void whenDelete_WithNotExistId_ThrowElementNotExistException() {
       Mockito.when(projectRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
       ElementNotExistException exception = Assertions.assertThrows(
               ElementNotExistException.class,
               () -> projectService.delete(1L)
        );

   }
   @Test
    void whenDelete_IfHasEmployeeInProject_ThenChangeStatus_Disable(){
        Project dbProject=new Project(1L,"Project A1","","Enable");
        Project deletingProject=new Project(1L,"Project A1","","Disable");
        Mockito.when(projectRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(dbProject));
        Mockito.when(employeeInProjectRepository.existsByProject_Id(Mockito.anyLong())).thenReturn(true);
        Mockito.when(projectRepository.save(Mockito.any(Project.class))).thenReturn(deletingProject);
        ProjectDTO returnDTO=projectService.delete(1L);
        Assertions.assertTrue(returnDTO.getStatus().equals("Disable"));
   }
   @Test
    void whenDelete_IfHasNoEmployeeInProject_ThenSuccess(){
        Project dbProject=new Project(1L,"Project A1","","Enable");
        Project deletingProject=new Project();
        deletingProject.setId(dbProject.getId());
        deletingProject.setName(dbProject.getName());
        deletingProject.setStatus("Disable");
       Mockito.when(projectRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(dbProject));
    Mockito.when(employeeInProjectRepository.existsByProject_Id(Mockito.anyLong())).thenReturn(false);
    ProjectDTO returnDTO=projectService.delete(1L);
    Assertions.assertTrue(returnDTO.getStatus().equals("Deleted"));
   Mockito.verify(projectRepository).delete(Mockito.any(Project.class));
   }


}

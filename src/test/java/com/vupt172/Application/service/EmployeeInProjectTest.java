package com.vupt172.Application.service;

import com.vupt172.converter.EmployeeInProjectConverter;
import com.vupt172.dto.EmployeeInProjectDTO;
import com.vupt172.entity.EmployeeInProject;
import com.vupt172.exception.ElementNotExistException;
import com.vupt172.exception.EmployeeAlreadyExistInProjectException;
import com.vupt172.Application.TestData.EmployeeInProjectTestData;
import com.vupt172.Application.TestData.EmployeeTestData;
import com.vupt172.Application.TestData.ProjectRoleTestData;
import com.vupt172.Application.TestData.ProjectTestData;
import com.vupt172.repository.*;
import com.vupt172.service.EmployeeInProjectServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EmployeeInProjectTest {
    @Mock
    ProjectRepository projectRepository;
    @Mock
    EmployeeInProjectRepository employeeInProjectRepository;
    @Mock
    EmployeeRepository employeeRepository;
    @Spy
    EmployeeInProjectConverter employeeInProjectConverter;
    @Mock
    ProjectRoleRepository projectRoleRepository;
    @Mock
    EvaluationRepository evaluationRepository;
    @InjectMocks
    EmployeeInProjectServiceImpl employeeInProjectService;

    @Test
    public void whenFindAll_WithNotExistProjectById_ThrowElementNotExistException() {
        //when
        Mockito.when(projectRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        //test
        ElementNotExistException exception = Assertions.assertThrows(ElementNotExistException.class, () -> employeeInProjectService.findAll(1L));
        String expectMessage = "Project not exist with id = " + 1L;
        Assertions.assertEquals(expectMessage, exception.getMessage());
    }

    @Test
    public void whenFindALl_ThenSuccess() {
        //when
        Mockito.when(projectRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(ProjectTestData.project1));
        Mockito.when(employeeInProjectRepository.findByProject_Id(Mockito.anyLong())).thenReturn(EmployeeInProjectTestData.employeeInProjectList);
        //test
        List<EmployeeInProjectDTO> returnDTOList = employeeInProjectService.findAll(1L);
        Assertions.assertEquals(returnDTOList.size(), 2);
    }

    @Test
    public void whenFindById_WithNotFoundProjectById_ThrowElementNotExistException() {
        //when
        Mockito.when(projectRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        //test
        ElementNotExistException exception = Assertions.assertThrows(ElementNotExistException.class, () -> employeeInProjectService.findEmployeeInProjectById(1L, 1L));
        String expectMessage = "Project not exist with id = " + 1L;
        Assertions.assertEquals(expectMessage, exception.getMessage());
    }

    @Test
    public void whenFindById_returnOptional_Empty() {

        //when
        Mockito.when(projectRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(ProjectTestData.project1));
        Mockito.when(employeeInProjectRepository.findByProject_IdAndEmployee_Id(1L, 1L)).thenReturn(Optional.empty());
        //test
        Optional<EmployeeInProjectDTO> returnDTO = employeeInProjectService.findEmployeeInProjectById(1L, 1L);
        Assertions.assertTrue(returnDTO.isEmpty());
        Mockito.verify(employeeInProjectRepository).findByProject_IdAndEmployee_Id(1L, 1L);
    }

    @Test
    public void whenFindById_returnOptional_Present() {
        //when
        Mockito.when(projectRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(ProjectTestData.project1));
        Mockito.when(employeeInProjectRepository.findByProject_IdAndEmployee_Id(1L, 1L)).thenReturn(Optional.of(EmployeeInProjectTestData.ep1));
        //test
        Optional<EmployeeInProjectDTO> returnDTO = employeeInProjectService.findEmployeeInProjectById(1L, 1L);
        Assertions.assertTrue(returnDTO.isPresent());
        Assertions.assertEquals(returnDTO.get().getEmployeeId(), EmployeeInProjectTestData.ep1.getEmployee().getId());
        Mockito.verify(employeeInProjectRepository).findByProject_IdAndEmployee_Id(1L, 1L);

    }

    @Test
    public void whenCreate_WithNotFoundProjectById_ThrowElementNotExistException() {
        EmployeeInProjectDTO employeeInProjectDTO = new EmployeeInProjectDTO();
        employeeInProjectDTO.setProjectId(1L);
        employeeInProjectDTO.setEmployeeId(2L);
        employeeInProjectDTO.setProjectRole("PM");
        employeeInProjectDTO.setStatus("Enable");
        //when
        Mockito.when(projectRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        //test
        ElementNotExistException exception = Assertions.assertThrows(ElementNotExistException.class, () -> employeeInProjectService.createEmployeeInProject(employeeInProjectDTO));
        String expectMessage = "Project not exist with id = " + 1L;
        Assertions.assertEquals(expectMessage, exception.getMessage());
    }

    @Test
    public void whenCreate_WithNotFoundEmployeeById_ThrowElementNotExistException() {
        EmployeeInProjectDTO employeeInProjectDTO = new EmployeeInProjectDTO();
        employeeInProjectDTO.setProjectId(1L);
        employeeInProjectDTO.setEmployeeId(2L);
        employeeInProjectDTO.setProjectRole("PM");
        employeeInProjectDTO.setStatus("Enable");
        //when
        Mockito.when(projectRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(ProjectTestData.project1));
        Mockito.when(employeeRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        //test
        ElementNotExistException exception = Assertions.assertThrows(ElementNotExistException.class, () -> employeeInProjectService.createEmployeeInProject(employeeInProjectDTO));
        String expectMessage = "Employee not exist with id = " + 2L;
        Assertions.assertEquals(expectMessage, exception.getMessage());
    }

    @Test
    public void whenCreate_WithNotFoundProjectRole_ThrowElementNotExistException() {
        EmployeeInProjectDTO employeeInProjectDTO = new EmployeeInProjectDTO();
        employeeInProjectDTO.setProjectId(1L);
        employeeInProjectDTO.setEmployeeId(2L);
        employeeInProjectDTO.setProjectRole("PM2");
        employeeInProjectDTO.setStatus("Enable");
        //when
        Mockito.when(projectRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(ProjectTestData.project1));
        Mockito.when(employeeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(EmployeeTestData.employee2));
        Mockito.when(projectRoleRepository.findByName(Mockito.anyString())).thenReturn(Optional.empty());
        //test
        ElementNotExistException exception = Assertions.assertThrows(ElementNotExistException.class, () -> employeeInProjectService.createEmployeeInProject(employeeInProjectDTO));
        String expectMessage = "Project Role not exist with name = " + employeeInProjectDTO.getProjectRole();
        Assertions.assertEquals(expectMessage, exception.getMessage());
    }

    @Test
    public void whenCreate_WithAlreadyExistEmployeeInProject_ThrowEmployeeAlreadyExistInProjectException() {
        EmployeeInProjectDTO employeeInProjectDTO = new EmployeeInProjectDTO();
        employeeInProjectDTO.setProjectId(1L);
        employeeInProjectDTO.setEmployeeId(2L);
        employeeInProjectDTO.setProjectRole("PM");
        employeeInProjectDTO.setStatus("Enable");
        //when
        Mockito.when(projectRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(ProjectTestData.project1));
        Mockito.when(employeeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(EmployeeTestData.employee2));
        Mockito.when(projectRoleRepository.findByName(Mockito.anyString())).thenReturn(Optional.of(ProjectRoleTestData.pm));
        Mockito.when(employeeInProjectRepository.findByProject_IdAndEmployee_Id(1L, 2L)).thenReturn(Optional.of(EmployeeInProjectTestData.ep2));
        //test
        EmployeeAlreadyExistInProjectException exception = Assertions.assertThrows(EmployeeAlreadyExistInProjectException.class, () -> employeeInProjectService.createEmployeeInProject(employeeInProjectDTO));
        String expectedMessage = "Employee with id = " + 2L + " already exist in project";
        Assertions.assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void whenCreate_ThenSuccess() {
        EmployeeInProjectDTO employeeInProjectDTO = new EmployeeInProjectDTO();
        employeeInProjectDTO.setProjectId(1L);
        employeeInProjectDTO.setEmployeeId(2L);
        employeeInProjectDTO.setProjectRole("PM");
        employeeInProjectDTO.setStatus("Enable");
        EmployeeInProject saveEP = employeeInProjectConverter.toEntity(employeeInProjectDTO);
        saveEP.setProject(ProjectTestData.project1);
        saveEP.setEmployee(EmployeeTestData.employee2);
        saveEP.setProjectRole(ProjectRoleTestData.pm);
        //when
        Mockito.when(projectRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(ProjectTestData.project1));
        Mockito.when(employeeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(EmployeeTestData.employee2));
        Mockito.when(projectRoleRepository.findByName(Mockito.anyString())).thenReturn(Optional.of(ProjectRoleTestData.pm));
        Mockito.when(employeeInProjectRepository.findByProject_IdAndEmployee_Id(1L, 2L)).thenReturn(Optional.empty());
        Mockito.when(employeeInProjectRepository.save(Mockito.any(EmployeeInProject.class))).thenReturn(saveEP);
        //test
        EmployeeInProjectDTO returnDTO = employeeInProjectService.createEmployeeInProject(employeeInProjectDTO);
        Assertions.assertEquals(returnDTO.getProjectId(), employeeInProjectDTO.getProjectId());
        Assertions.assertEquals(returnDTO.getEmployeeId(), employeeInProjectDTO.getEmployeeId());
        Assertions.assertEquals(returnDTO.getProjectRole(), employeeInProjectDTO.getProjectRole());
    }

    @Test
    public void whenUpdate_WithNotFoundProjectById_ThrowElementNotExistException() {
        EmployeeInProjectDTO employeeInProjectDTO = new EmployeeInProjectDTO();
        employeeInProjectDTO.setProjectId(1L);
        employeeInProjectDTO.setEmployeeId(2L);
        employeeInProjectDTO.setProjectRole("PM");
        employeeInProjectDTO.setStatus("Enable");
        //when
        Mockito.when(projectRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        //test
        ElementNotExistException exception = Assertions.assertThrows(ElementNotExistException.class, () -> employeeInProjectService.updateEmployeeInProject(employeeInProjectDTO));
        String expectMessage = "Project not exist with id = " + 1L;
        Assertions.assertEquals(expectMessage, exception.getMessage());
    }

    @Test
    public void whenUpdate_WithNotFoundEmployeeInProject_ThrowElementNotExistException() {
        EmployeeInProjectDTO employeeInProjectDTO = new EmployeeInProjectDTO();
        employeeInProjectDTO.setProjectId(1L);
        employeeInProjectDTO.setEmployeeId(2L);
        employeeInProjectDTO.setProjectRole("PM");
        employeeInProjectDTO.setStatus("Enable");
        //when
        Mockito.when(projectRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(ProjectTestData.project1));
        Mockito.when(employeeInProjectRepository.findByProject_IdAndEmployee_Id(1L, 2L)).thenReturn(Optional.empty());
        //test
        ElementNotExistException exception = Assertions.assertThrows(ElementNotExistException.class, () -> employeeInProjectService.updateEmployeeInProject(employeeInProjectDTO));
        String expectMessage = "Employee with id = " + 2L + " not exist in project";
        Assertions.assertEquals(expectMessage, exception.getMessage());
    }

    @Test
    public void whenUpdate_WithNotFoundProjectRoleByName_ThrowElementNotExistException() {
        EmployeeInProjectDTO employeeInProjectDTO = new EmployeeInProjectDTO();
        employeeInProjectDTO.setProjectId(1L);
        employeeInProjectDTO.setEmployeeId(2L);
        employeeInProjectDTO.setProjectRole("P1");
        employeeInProjectDTO.setStatus("Enable");
        //when
        Mockito.when(projectRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(ProjectTestData.project1));
        Mockito.when(employeeInProjectRepository.findByProject_IdAndEmployee_Id(1L, 2L)).thenReturn(Optional.of(EmployeeInProjectTestData.ep2));
        Mockito.when(projectRoleRepository.findByName(Mockito.anyString())).thenReturn(Optional.empty());
        //test
        ElementNotExistException exception = Assertions.assertThrows(ElementNotExistException.class, () -> employeeInProjectService.updateEmployeeInProject(employeeInProjectDTO));
        String expectMessage = "Project role not exist with name = " + employeeInProjectDTO.getProjectRole();
        Assertions.assertEquals(expectMessage, exception.getMessage());
    }

    @Test
    public void whenUpdate_ThenSuccess() {
        EmployeeInProjectDTO employeeInProjectDTO = new EmployeeInProjectDTO();
        employeeInProjectDTO.setProjectId(1L);
        employeeInProjectDTO.setEmployeeId(2L);
        employeeInProjectDTO.setProjectRole("TESTER");
        employeeInProjectDTO.setStatus("Disable");

        EmployeeInProject updatingEmployeeInProject = employeeInProjectConverter.toEntity(employeeInProjectDTO, EmployeeInProjectTestData.ep2);
        updatingEmployeeInProject.setProjectRole(ProjectRoleTestData.tester);
        //when
        Mockito.when(projectRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(ProjectTestData.project1));
        Mockito.when(employeeInProjectRepository.findByProject_IdAndEmployee_Id(1L, 2L)).thenReturn(Optional.of(EmployeeInProjectTestData.ep2));
        Mockito.when(projectRoleRepository.findByName(Mockito.anyString())).thenReturn(Optional.of(ProjectRoleTestData.tester));
        //test
        EmployeeInProjectDTO returnDTO=employeeInProjectService.updateEmployeeInProject(employeeInProjectDTO);
        Assertions.assertEquals(returnDTO.getProjectRole(),"TESTER");
        Assertions.assertEquals(returnDTO.getStatus(),"Disable");
    }
    @Test
    public void whenDelete_WithNotFoundProjectById_ThrowElementNotExistException(){
        //when
        Mockito.when(projectRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        //test
        ElementNotExistException exception=Assertions.assertThrows(ElementNotExistException.class,()->employeeInProjectService.deleteEmployeeInProject(1L,2L));
        String expectMessage="Project not exist with id = "+1L;
        Assertions.assertEquals(expectMessage,exception.getMessage());
    }
    @Test
    public void whenDelete_WithNotFoundEmployeeInProject_ThrowElementNotExistException(){
        //when
        Mockito.when(projectRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(ProjectTestData.project1));
        Mockito.when(employeeInProjectRepository.findByProject_IdAndEmployee_Id(1L,2L)).thenReturn(Optional.empty());
        //test
        ElementNotExistException exception=Assertions.assertThrows(ElementNotExistException.class,()->employeeInProjectService.deleteEmployeeInProject(1L,2L));
        String expectMessage="Employee with id = "+2L+" not exist in project";
        Assertions.assertEquals(expectMessage,exception.getMessage());
    }
    @Test
    public void whenDelete_WithEmployeeInProjectHasEvaluationAsEvaluator_ThenChangeStatusDisable(){
        //data

        //when
        Mockito.when(projectRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(ProjectTestData.project1));
        Mockito.when(employeeInProjectRepository.findByProject_IdAndEmployee_Id(1L,2L)).thenReturn(Optional.of(EmployeeInProjectTestData.ep2));
        Mockito.when(evaluationRepository.existsByProject_IdAndEvaluator_Id(1L,2L)).thenReturn(true);
        Mockito.when(evaluationRepository.existsByProject_IdAndEvaluatee_Id(1L,2L)).thenReturn(false);
        EmployeeInProjectTestData.ep2.setStatus("Disable");
        Mockito.when(employeeInProjectRepository.save(Mockito.any(EmployeeInProject.class))).thenReturn(EmployeeInProjectTestData.ep2);
        //test
        EmployeeInProjectDTO returnDTO=employeeInProjectService.deleteEmployeeInProject(1L,2L);
        Assertions.assertEquals(returnDTO.getEmployeeId(),2L);
        Assertions.assertEquals(returnDTO.getStatus(),"Disable");
        Mockito.verify(employeeInProjectRepository).save( EmployeeInProjectTestData.ep2);
    }

    @Test
    public void whenDelete_WithEmployeeInProjectHasEvaluationAsEvaluatee_ThenChangeStatusDisable(){
        //data

        //when
        Mockito.when(projectRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(ProjectTestData.project1));
        Mockito.when(employeeInProjectRepository.findByProject_IdAndEmployee_Id(1L,2L)).thenReturn(Optional.of(EmployeeInProjectTestData.ep2));
        Mockito.when(evaluationRepository.existsByProject_IdAndEvaluator_Id(1L,2L)).thenReturn(false);
        Mockito.when(evaluationRepository.existsByProject_IdAndEvaluatee_Id(1L,2L)).thenReturn(true);
        EmployeeInProjectTestData.ep2.setStatus("Disable");
        Mockito.when(employeeInProjectRepository.save(Mockito.any(EmployeeInProject.class))).thenReturn(EmployeeInProjectTestData.ep2);
        //test
        EmployeeInProjectDTO returnDTO=employeeInProjectService.deleteEmployeeInProject(1L,2L);
        Assertions.assertEquals(returnDTO.getEmployeeId(),2L);
        Assertions.assertEquals(returnDTO.getStatus(),"Disable");
        Mockito.verify(employeeInProjectRepository).save( EmployeeInProjectTestData.ep2);
    }
    @Test
    public void whenDelete_ThenSuccess(){
        //stubbing
        Mockito.when(projectRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(ProjectTestData.project1));
        Mockito.when(employeeInProjectRepository.findByProject_IdAndEmployee_Id(1L,2L)).thenReturn(Optional.of(EmployeeInProjectTestData.ep2));
        Mockito.when(evaluationRepository.existsByProject_IdAndEvaluator_Id(1L,2L)).thenReturn(false);
        Mockito.when(evaluationRepository.existsByProject_IdAndEvaluatee_Id(1L,2L)).thenReturn(false);
        //test
        EmployeeInProjectDTO returnDTO=employeeInProjectService.deleteEmployeeInProject(1l,2L);
        Assertions.assertEquals(returnDTO.getStatus(),"Deleted");
        Mockito.verify(employeeInProjectRepository).delete(EmployeeInProjectTestData.ep2);
    }
}

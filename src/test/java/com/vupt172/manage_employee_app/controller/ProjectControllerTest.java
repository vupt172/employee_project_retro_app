package com.vupt172.manage_employee_app.controller;

import com.vupt172.controller.ProjectController;
import com.vupt172.dto.ProjectDTO;
import com.vupt172.service.itf.IProjectService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
/*@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = ManageEmployeeAppApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-dev.properties")*/
@WebMvcTest(ProjectController.class)
@ContextConfiguration(classes= ProjectController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ProjectControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private IProjectService service;

    @Before
    public void setUp() throws Exception {
    }
    @Test
    public void givenProjects_whenGetProjects_thenReturnJsonArray()
            throws Exception {
        List<ProjectDTO> allProjectDTOs = new ArrayList<>();
        allProjectDTOs.add(new ProjectDTO(1L,"Project A1","","Enable"));
        allProjectDTOs.add(new ProjectDTO(2L,"Project A2","","Enable"));
        allProjectDTOs.add(new ProjectDTO(1L,"Project A3","","Enable"));
        allProjectDTOs.add(new ProjectDTO(1L,"Project A4","","Enable"));
        allProjectDTOs.add(new ProjectDTO(1L,"Project A5","","Enable"));
        allProjectDTOs.add(new ProjectDTO(1L,"Project A6","","Enable"));
        Mockito.when(service.findAll()).thenReturn(allProjectDTOs);
        mvc.perform(get("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(6)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", is("Project A1")));

    }
    @Test
    public void whenGetProjectById_WithNotFoundProject_ReturnNotFoundStatus() throws Exception{
        //stubbing
        Mockito.when(service.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        //test
        mvc.perform(get("/api/projects/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

}

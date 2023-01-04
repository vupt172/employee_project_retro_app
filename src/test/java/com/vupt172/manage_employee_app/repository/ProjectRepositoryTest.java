package com.vupt172.manage_employee_app.repository;

import com.vupt172.entity.Project;
import com.vupt172.repository.ProjectRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;
@ExtendWith(MockitoExtension.class)
@DataJpaTest()
class ProjectRepositoryTest {
   @Autowired
   private ProjectRepository projectRepository;
    @Test
    public void whenSave_ShouldReturnEntity(){
        Project project=projectRepository.save(new Project("Project A1","","Enable"));
        Assertions.assertNotNull(project);
        Assertions.assertTrue(project.getId()>0);
        Assertions.assertTrue(true);
    }
    @Test
    public void whenFindAll_ShouldReturnListEntity(){
      List<Project> projectList=projectRepository.findAll();
      Assertions.assertEquals(projectList.size(),10);
    }
    @Test
    public void whenFindById_ShouldReturnEntity(){
        Project project=projectRepository.findById(1L).get();
        Assertions.assertEquals(project.getName(),"Project A1");
        Assertions.assertEquals(project.getDescription(),"");
        Assertions.assertEquals(project.getStatus(),"Enable");
    }
    @Test
    public void whenFindById_ShouldReturnOptionalEmpty(){
        Optional<Project> project=projectRepository.findById(11L);
        Assertions.assertTrue(project.isEmpty());
    }

}

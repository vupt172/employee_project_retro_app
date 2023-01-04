package com.vupt172.runner;

import com.vupt172.entity.Project;
import com.vupt172.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Profile("test")
public class FakeProjectDataRunner implements CommandLineRunner {
    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public void run(String... args) throws Exception {
        List<Project> projectList = new ArrayList<>();
        projectList.add(new Project("Project A1", "", "Enable"));
        projectList.add(new Project("Project A2", "", "Enable"));
        projectList.add(new Project("Project A3", "", "Enable"));
        projectList.add(new Project("Project A4", "", "Enable"));
        projectList.add(new Project("Project A5", "", "Enable"));
        projectList.add( new Project("Project A6", "", "Enable"));
        projectList.add(new Project("Project A7", "", "Enable"));
        projectList.add(new Project("Project A8", "", "Enable"));
        projectList.add(new Project("Project A9", "", "Enable"));
        projectList.add(new Project("Project A10", "", "Enable"));
        projectRepository.saveAll(projectList);
    }

}

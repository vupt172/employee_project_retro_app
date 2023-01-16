package com.vupt172.manage_employee_app.TestData;

import com.vupt172.entity.Project;

public class ProjectTestData {
    public static Project project1;

    static {
        project1 = new Project();
        project1.setId(1L);
        project1.setName("Project A1");
        project1.setDescription("");
        project1.setStatus("Enable");
    }
}

package com.vupt172.Application.TestData;

import com.vupt172.entity.EmployeeInProject;

import java.util.List;
public class EmployeeInProjectTestData {
    public static EmployeeInProject ep1;
    public static EmployeeInProject ep2;
    public static List<EmployeeInProject> employeeInProjectList;
    static{
         ep1=new EmployeeInProject();
        ep1.setProject(ProjectTestData.project1);
        ep1.setEmployee(EmployeeTestData.employee1);
        ep1.setProjectRole(ProjectRoleTestData.pm);
        ep1.setStatus("Enable");

         ep2=new EmployeeInProject();
        ep2.setProject(ProjectTestData.project1);
        ep2.setEmployee(EmployeeTestData.employee2);
        ep2.setProjectRole(ProjectRoleTestData.dev);
        ep2.setStatus("Enable");
        employeeInProjectList=List.of(ep1,ep2);
    }
}

package com.vupt172.Application.TestData;

import com.vupt172.entity.ProjectRole;

public class ProjectRoleTestData {
    public static ProjectRole pm;
    public static ProjectRole dev;
    public static ProjectRole tester;
    static{
        pm=new ProjectRole(1,"PM","");
        dev=new ProjectRole(2,"DEV","");
        tester=new ProjectRole(3,"TESTER","");
    }
}

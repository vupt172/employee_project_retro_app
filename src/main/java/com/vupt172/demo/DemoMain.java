package com.vupt172.demo;

import com.vupt172.converter.EmployeeConverter;
import org.springframework.beans.factory.annotation.Autowired;

public class DemoMain {
    @Autowired
    EmployeeConverter employeeConverter;
    public static void main(String[] args) {
/*     DemoMain b1=new DemoMain();
        System.out.println(b1.employeeConverter.toString());
        b1.employeeConverter.message="Change employee converter T1";
        DemoMain b2=new DemoMain();
        System.out.println(b2.employeeConverter.toString());*/
    }
}

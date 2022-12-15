package com.vupt172.demo;

import com.vupt172.converter.EmployeeConverter;
import com.vupt172.exception.TokenRefreshException;
import org.springframework.beans.factory.annotation.Autowired;

public class DemoMain {
    @Autowired
    EmployeeConverter employeeConverter;
    public static void main(String[] args) {
     try{
         throw new TokenRefreshException("abcd");
     }
     catch(Exception e){
         System.out.println(e.getMessage());
     }
        System.out.println("Outside trycatch");
    }
}

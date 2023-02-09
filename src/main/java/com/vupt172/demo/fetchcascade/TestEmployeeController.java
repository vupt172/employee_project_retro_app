package com.vupt172.demo.fetchcascade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/test/employee")
@Profile("demo")
public class TestEmployeeController {
    @Autowired
    TestEmployeeRepo testEmployeeRepo;
    @GetMapping
    public Object getAll(){
        List<TestEmployee> employeeList=testEmployeeRepo.findAll();
        System.out.println("===================");
        return employeeList;
    }
    @GetMapping("/{id}")
    public Object getById(@PathVariable int id){
        TestEmployee testEmployee=testEmployeeRepo.findById(id).orElse(null);
        System.out.println("===================");
        return testEmployee;
    }
}

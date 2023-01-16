package com.vupt172.test.fetchcascade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

import java.util.*;
@RestController
@RequestMapping("test/company")
@Profile("test")
public class TestCompanyController {
    @Autowired
    TestCompanyRepo testCompanyRepo;
    @PostMapping("/addCompanyAndEmployee")
    public Object addCompany(){
        TestCompany company=new TestCompany();
        company.setName("Company A");
        TestEmployee employee1=new TestEmployee();
        employee1.setName("Pham Vu");
        employee1.setCompany(company);
        company.getEmployeeList().add(employee1);
        testCompanyRepo.save(company);
        return company;
    }
    @GetMapping
    public Object getAll(){
        List<TestCompany> companyList=testCompanyRepo.findAll();
        System.out.println("===================");
        return companyList;
    }
    @GetMapping("/{id}")
    public Object getById(@PathVariable int id){
        TestCompany testCompany=testCompanyRepo.findById(id).orElse(null);
        System.out.println("===================");
        return testCompany;
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id){
        TestCompany testCompany=testCompanyRepo.findById(id).orElse(null);
        if(testCompany!=null)testCompanyRepo.delete(testCompany);
    }
}

package com.vupt172.demo.fetchcascade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("test/company")
@Profile("demo")
public class TestCompanyController {
    @Autowired
    TestCompanyRepo testCompanyRepo;
    @Autowired
    TestCompanyService testCompanyService;
    @PostMapping("create/addCompanyAndEmployee")
    public Object addCompany() throws Exception{
        TestCompany company=new TestCompany();
        company.setName("Company A");
        TestEmployee employee1=new TestEmployee();
        employee1.setName("Pham Vu");
        employee1.setCompany(company);
        company.getEmployeeList().add(employee1);
        testCompanyRepo.save(company);
        throw new Exception("Exception 1");
      //  return company;
    }
    @PostMapping("/addMultiple")
    public List<TestCompany> addMultipleCompany() throws Exception {
        List<TestCompany> list=new ArrayList<>();
        list.add(new TestCompany("C1"));
        list.add(new TestCompany("C2"));
        list.add(new TestCompany("C3"));

            testCompanyService.saveMultiple(list);

       return list;
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

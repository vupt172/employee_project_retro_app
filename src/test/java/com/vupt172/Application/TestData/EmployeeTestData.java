package com.vupt172.Application.TestData;

import com.vupt172.entity.Employee;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class EmployeeTestData {
    public static Employee employee1;
    public static Employee employee2;
    public static  Employee employee3;
    public static List<Employee> employeeList;
    static{
         employee1 = new Employee();
        employee1.setId(1L);
        employee1.setUsername("employee1");
        employee1.setPassword("12345678");
        employee1.setFullName("Employee 1");
        employee1.setEmail("user1@gmail.com");
        employee1.setPhone("0971905583");
        employee1.setStatus("Enable");
        LocalDate date = LocalDate.of(2022, 12, 17);
        employee1.setBirthDay(Date.valueOf(date));
        //
         employee2 = new Employee();
        employee2.setId(2L);
        employee2.setUsername("employee2");
        employee2.setPassword("12345678");
        employee2.setFullName("Employee 2");
        employee2.setEmail("user2@gmail.com");
        employee2.setPhone("0971905584");
        employee2.setStatus("Enable");
        LocalDate date2 = LocalDate.of(2022, 12, 18);
        employee2.setBirthDay(Date.valueOf(date));
        //
         employee3 = new Employee();
        employee3.setId(3L);
        employee3.setUsername("employee3");
        employee3.setPassword("12345678");
        employee3.setFullName("Employee 3");
        employee3.setEmail("user3@gmail.com");
        employee3.setPhone("0971905585");
        employee3.setStatus("Enable");
        LocalDate date3 = LocalDate.of(2022, 12, 18);
        employee3.setBirthDay(Date.valueOf(date));
        employeeList=List.of(employee1,employee2,employee3);
    }
}

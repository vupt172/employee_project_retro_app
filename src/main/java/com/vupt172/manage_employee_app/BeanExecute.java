package com.vupt172.manage_employee_app;

import com.vupt172.converter.EmployeeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BeanExecute {
    @Autowired
    EmployeeConverter employeeConverter;
}

package com.vupt172.config;

import com.vupt172.converter.EmployeeConverter;
import com.vupt172.converter.EvaluationConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig {

    @Bean
    public EmployeeConverter employeeConverter(){
        System.out.println("bean employee converter");
        return new EmployeeConverter();
    }
    @Bean
    public EvaluationConverter evaluationConverter(){
        return new EvaluationConverter();
    }

}

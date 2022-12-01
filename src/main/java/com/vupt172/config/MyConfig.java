package com.vupt172.config;

import com.vupt172.converter.EmployeeConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
@Configuration
public class MyConfig {
 /*   @Bean
    public PasswordEncoder passwordEncoder() {
        System.out.println("passwordEncoder");
        return new BCryptPasswordEncoder();
    }*/
    @Bean
    public EmployeeConverter employeeConverter(){
        return new EmployeeConverter();
    }
}

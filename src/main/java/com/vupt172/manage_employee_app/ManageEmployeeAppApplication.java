package com.vupt172.manage_employee_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.vupt172.entity")
@ComponentScan(basePackages = "com.vupt172")
@EnableJpaRepositories(basePackages = "com.vupt172.repository")
public class ManageEmployeeAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManageEmployeeAppApplication.class, args);
	}

}

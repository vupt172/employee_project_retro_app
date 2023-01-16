package com.vupt172.manage_employee_app;

import com.vupt172.test.Computer;
import com.vupt172.test.Person;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@SpringBootApplication()
@EntityScan(basePackages = "com.vupt172")
@ComponentScan(basePackages = {"com.vupt172","com.vupt172.test"})
@EnableJpaRepositories(basePackages = "com.vupt172.repository")
@EnableScheduling
@EnableAsync
public class ManageEmployeeAppApplication {
	public static void main(String[] args) {
		ApplicationContext context=SpringApplication.run(ManageEmployeeAppApplication.class, args);
       //testPrototypeBean(context);
		//testSingletonBean(context);
	}
	static void testPrototypeBean(ApplicationContext context){
		Person personA=context.getBean(Person.class);
		System.out.println("--PersonA--");
		System.out.println("Before :"+personA.toString());
		personA.setId(1);
		personA.setName("Pham Vu");
		personA.setMessage("This is Person A");
		System.out.println("After:"+personA.toString());
		Person personB=context.getBean(Person.class);
		System.out.println("--PersonB--");
		System.out.println("Before :"+personB.toString());
		personB.setId(2);
		personB.setName("Nguyen Tuan Anh");
		personB.setMessage("This is Person B");
		System.out.println("After:"+personB.toString());
	}
	static void testSingletonBean(ApplicationContext context){
      Computer computer1=context.getBean(Computer.class);
		System.out.println("--Computer1--");
		System.out.println("Before :"+computer1.toString());
		computer1.setId(1);
		computer1.setName("Laptop Dell");
		System.out.println("After:"+computer1.toString());
		Computer computer2=context.getBean(Computer.class);
		System.out.println("--Computer2--");
		System.out.println("Before :"+computer2.toString());

	}
	@Bean(name = "mvcHandlerMappingIntrospector")
	@Profile("test")
	public HandlerMappingIntrospector mvcHandlerMappingIntrospector() {
		return new HandlerMappingIntrospector();
	}
}

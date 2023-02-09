package com.vupt172.config;

import com.vupt172.demo.beantype.Computer;
import com.vupt172.demo.beantype.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@Profile("demo")
@EnableJpaRepositories(basePackages = "com.vupt172.demo")
public class DemoConfig {
 @Bean
    public Computer computer(){
     Computer computer=new Computer();
     computer.setMessage("This is a computer");
     return computer;
 }
 @Bean
 @Scope("prototype")
 public Person person(){
      return new Person();
 }
}

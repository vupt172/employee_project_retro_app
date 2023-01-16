package com.vupt172.test;

import lombok.Data;
import lombok.ToString;
import org.springframework.context.annotation.Profile;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Data
@ToString
@Profile("test")
public class Computer {
    public Integer id;
    public String name;
    public String message;
    @PostConstruct
    public void init(){
        System.out.println("Computer @PostConstruct is called");
    }
    @PreDestroy
    public void destroy(){
        System.out.println("Computer @PreDestroy is called");
    }
}

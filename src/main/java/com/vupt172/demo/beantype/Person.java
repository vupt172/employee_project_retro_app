package com.vupt172.demo.beantype;

import lombok.Data;
import lombok.ToString;
import org.springframework.context.annotation.Profile;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Data
@ToString
@Profile("demo")
public class Person {
  public Integer id;
  public String name;
  public String message="hello person";
  public Person(){}
  public Person(Integer id,String name,String message){
    this.id=id;
    this.name=name;
    this.message=message;
  }
  @PostConstruct
  public void init(){
    System.out.println("Person @PostConstruct is called");
  }
  @PreDestroy
  public void destroy(){
    System.out.println("Person @PreDestroy is called");
  }
}

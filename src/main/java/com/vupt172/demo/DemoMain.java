package com.vupt172.demo;

import java.util.ArrayList;
import java.util.List;

class Person{
    String name;
    public Person(String name){
        this.name=name;
    }
}
public class DemoMain {
    public static void main(String[] args) {
        List<Person> people=new ArrayList<>();
        people.add(new Person("Vu"));
        people.add(new Person("Hoang"));
        people.add(new Person("Ha"));
        people.add(new Person("Anh"));
        people.add(new Person("Tuan"));
/*        // check one name is exist in personList bylamda
        boolean isExistByName=false;
      Long count=  people.stream().filter(p->p.name.equals("Vu")).count();
        if(count>0)isExistByName=true;
        System.out.println(isExistByName);
       isExistByName= people.stream().anyMatch(p->p.name.equals("Vu"));
        System.out.println(isExistByName);*/
        //check equal
       String str1="Vu";
        String str2="Vu";
        String str3=new String("Vu");
        System.out.println( str1.equals(str3));
        Object b=new Object();
        b.equals(str1);
        /*
        System.out.println(str1.equals(str2));
        System.out.println(str1==str2);
        System.out.println("==");
        System.out.println(str1.equals(str3));
        System.out.println(str1==str3);*/
        Person p1=new Person("Vu");
        Person p2=p1;
        System.out.println(p1.equals(p2));

    }
}

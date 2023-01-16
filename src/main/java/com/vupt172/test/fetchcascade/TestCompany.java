package com.vupt172.test.fetchcascade;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="test_company")
public class TestCompany {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "company",cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    List<TestEmployee> employeeList=new ArrayList<>();
}

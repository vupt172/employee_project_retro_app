package com.vupt172.test.fetchcascade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "test_employee")
public class TestEmployee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    TestCompany company;
}

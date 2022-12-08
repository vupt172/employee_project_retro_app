package com.vupt172.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Data
@Table(name = "employee")
@Entity
public class Employee extends BaseEntity {
    @Column(name = "fullname")
    private String fullName;
    @Column
    private String username;
    @Column
    private String password;
    @Column
    private String phone;
    @Column
    private String email;
    @Column(name="birthday")
    private Date birthDay;
    private int role;
    private String status;
    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER,mappedBy ="employee")
    private List<EmployeeInProject> employeeInProjects=new ArrayList<>();
}

package com.vupt172.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name="project")
public class Project extends  BaseEntity{
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private String status;
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "project")
    Set<EmployeeInProject> employeeInProjects;
   /* @OneToMany(mappedBy="project")
    Set<Comment> comments;*/
}

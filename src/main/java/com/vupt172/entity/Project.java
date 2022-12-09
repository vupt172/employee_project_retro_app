package com.vupt172.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Data
@Entity
@Table(name="project")
public class Project extends  BaseEntity{
    public Project(){}
    public Project(Long id,String name,String description,String status){
        this.setId(id);
        this.name=name;
        this.description=description;
        this.status=status;
    }
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private String status;
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "project")
    private List<EmployeeInProject> employeeInProjects=new ArrayList<>();

}

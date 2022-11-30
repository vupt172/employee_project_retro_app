package com.vupt172.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="employee_project")
public class EmployeeInProject {
    @EmbeddedId
   private  EmployeeInProjectKey id;
    @ManyToOne
    @MapsId("project_id")
    private Project project;
    @ManyToOne
    @MapsId("employee_id")
    private Employee employee;
    @ManyToOne
    @JoinColumn(name="role_id")
    private ProjectRole projectRole;

}

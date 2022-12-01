package com.vupt172.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity
@Table(name="employee_project")

public class EmployeeInProject {
    @EmbeddedId
   private  EmployeeInProjectKey id=new EmployeeInProjectKey();
    @ManyToOne
    @MapsId("projectId")
    @JoinColumn(name="project_id")
    private Project project;
    @ManyToOne
    @MapsId("employeeId")
    @JoinColumn(name="employee_id")
    private Employee employee;
    @ManyToOne
    @JoinColumn(name="role_id")
    private ProjectRole projectRole;

}

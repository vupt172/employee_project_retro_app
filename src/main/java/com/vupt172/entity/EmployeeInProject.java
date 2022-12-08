package com.vupt172.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name="employee_project")

public class EmployeeInProject {
    @EmbeddedId
   private  EmployeeInProjectKey id=new EmployeeInProjectKey();
    @JsonIgnore
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

    @OneToMany(mappedBy = "evaluateeInProject")
    private List<Evaluation> evaluationsToMe;
    @OneToMany(mappedBy = "evaluatorInProject")
    private List<Evaluation> evaluationsToOther;


}

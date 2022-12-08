package com.vupt172.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="evaluation")
@Data
public class Evaluation extends  BaseEntity{
    @ManyToOne
    @JoinColumn(name="project_id")
    private Project project;
    @ManyToOne
    @JoinColumn(name="evaluator_id")
    private EmployeeInProject evaluatorInProject;
    @ManyToOne
    @JoinColumn(name="evaluatee_id")
    private EmployeeInProject evaluateeInProject;
    @Column
    private int point;
    @Column
    private String comment;
    @Column
    private Date date;
}

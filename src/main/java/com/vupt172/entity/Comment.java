/*
package com.vupt172.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="comment")
@Data
public class Comment extends  BaseEntity{
    @ManyToOne
    @JoinColumn(name="project_id")
    private Project project;
    @ManyToOne
    @JoinColumn(name="employee_id")
    private EmployeeInProject employeeInProject;
    @ManyToOne
    @JoinColumn(name="evaluator_id")
    private EmployeeInProject evaluatorInProject;
    @Column
    private int point;
    @Column
    private String comment;
    @Column
    private Date date;
}
*/

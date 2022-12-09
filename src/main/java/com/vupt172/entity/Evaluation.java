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
    private Employee evaluator;
    @ManyToOne
    @JoinColumn(name="evaluatee_id")
    private Employee evaluatee;
    @Column
    private int point;
    @Column
    private String comment;
    @Column
    private Date createdDate;
    @Column
    private Date updatedDate;

}

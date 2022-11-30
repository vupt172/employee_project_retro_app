package com.vupt172.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "project_role")
public class ProjectRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;
    @Column
    private String description;
}

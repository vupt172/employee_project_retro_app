package com.vupt172.dto;

import lombok.Data;

import java.util.Date;

@Data
public class EvaluationDTO {
    private Long id;
    private Long projectId;
    private Long employeeId;
    private Long evaluateeId;
    private int point;
    private String comment;
    private Date date;
}

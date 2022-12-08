package com.vupt172.dto;

import lombok.Data;

@Data
public class EmployeeInProjectDTO {
    private Long projectId;
    private Long employeeId;
    private String projectRole;
}

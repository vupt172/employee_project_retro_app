package com.vupt172.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class EmployeeInProjectDTO {
    private Long projectId;
    private Long employeeId;
    @NotBlank
    @Size(max=45,message = "projectRole must be lesser than 46 characters")
    private String projectRole;
    @NotBlank
    @Size(max=45,message = "status must be lesser than 46 characters")
    private String status;
}

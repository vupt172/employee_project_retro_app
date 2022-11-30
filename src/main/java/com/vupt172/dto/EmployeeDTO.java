package com.vupt172.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.sql.Date;

@Data
public class EmployeeDTO {
    private Long id;
    private String fullName;
    private String username;
    private String password;
    private String email;
    private String phone;
    @Min(value=1,message = "role should not be less than 1")
    @Max(value = 2,message = "role should not be greater than 2")
    private int role;
    private Date birthDay;
    private String status;
}

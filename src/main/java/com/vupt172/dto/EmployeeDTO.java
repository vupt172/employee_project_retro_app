package com.vupt172.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class EmployeeDTO {
    private Long id;
    private String fullName;
    private String username;
    private String password;
    private String email;
    private String phone;
    private int role;
    private Date birthDay;
    private String status;
}

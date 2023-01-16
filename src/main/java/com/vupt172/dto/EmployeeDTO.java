package com.vupt172.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Date;

@Data
public class EmployeeDTO {
    private Long id;
    @NotBlank
    @Size(max=255,message = "name must be lesser than 256 characters")
    private String fullName;
    @NotBlank
    @Size(max=255,message = "username must be lesser than 256 characters")
    private String username;
    @NotBlank
    @Size(max=255,message = "password must be lesser than 256 characters")
    private String password;
    @NotBlank
    @Size(max=255,message = "email must be lesser than 256 characters")
    private String email;
  /*  @NotBlank
    @Size(max=11,message = "phone must be lesser than 256 characters")*/
    private String phone;
    @Min(value=0,message = "role should not be lesser than 0")
    @Max(value = 2,message = "role should not be greater than 2")
    private int role;
    private Date birthDay;
    @NotBlank
    @Size(max=255,message = "status must be lesser than 256 characters")
    private String status;
}

package com.vupt172.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDTO {
    private Long id;
    @NotBlank
    @Size(max=255,message = "name must be lesser than 256 characters")
    private String name;
    private String description="";
    @NotBlank
    @Size(max=255,message = "status must be lesser than 256 characters")
    private String status;


    public boolean equals(ProjectDTO projectDTO){
        if(this.id.equals(projectDTO.getId())&&this.name.equals(projectDTO.getName())&&this.description.equals(projectDTO.getDescription()))return true;
        return false;
    }
}

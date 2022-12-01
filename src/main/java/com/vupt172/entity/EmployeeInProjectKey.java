package com.vupt172.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
@Embeddable
@Data
@EqualsAndHashCode
public class EmployeeInProjectKey implements Serializable {
    @Column(name="project_id")
    private Long projectId;
    @Column(name="employee_id")
    private Long employeeId;
    public EmployeeInProjectKey(){}
    public EmployeeInProjectKey(Long projectId,Long employeeId){
        this.projectId=projectId;
        this.employeeId=employeeId;
    }
}

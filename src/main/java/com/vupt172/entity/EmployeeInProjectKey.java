package com.vupt172.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
@Embeddable
@Data
public class EmployeeInProjectKey implements Serializable {
    @Column(name="project_id")
    Long projectId;
    @Column(name="employee_id")
    Long employeeId;
}

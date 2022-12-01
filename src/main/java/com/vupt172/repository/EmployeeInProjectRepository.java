package com.vupt172.repository;

import com.vupt172.entity.EmployeeInProject;
import com.vupt172.entity.EmployeeInProjectKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeInProjectRepository extends JpaRepository<EmployeeInProject, EmployeeInProjectKey> {

}

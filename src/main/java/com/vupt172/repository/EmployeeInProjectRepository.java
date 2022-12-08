package com.vupt172.repository;

import com.vupt172.entity.EmployeeInProject;
import com.vupt172.entity.EmployeeInProjectKey;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface EmployeeInProjectRepository extends JpaRepository<EmployeeInProject, EmployeeInProjectKey> {
 List<EmployeeInProject> findByProject_Id(Long projectId);
 Optional<EmployeeInProject> findByProject_IdAndEmployee_Id(Long projectId,Long employeeId);
}

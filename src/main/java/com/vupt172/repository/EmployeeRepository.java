package com.vupt172.repository;

import com.vupt172.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);

    Optional<Employee> findByUsername(String username );
    Optional<Employee> findByEmail(String email);
    Optional<Employee> findByPhone(String phone);
    @Query("select e from Employee e where (e.status=?1) and (e.role=?2)")
    List<Employee> findAllByStatusAndRole(String status,int role);
    List<Employee> findByEmployeeInProjects_Project_Id(Long projectId);
}

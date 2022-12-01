package com.vupt172.repository;

import com.vupt172.entity.ProjectRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectRoleRepository extends JpaRepository<ProjectRole,Integer> {
    Optional<ProjectRole> findByName(String roleName);
}

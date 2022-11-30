package com.vupt172.repository;

import com.vupt172.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Long> {
    boolean existsByName(String projectName);
   Optional<Project> findByName(String projectName);
}

package com.vupt172.demo.fetchcascade;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("demo")
public interface TestEmployeeRepo extends JpaRepository<TestEmployee,Integer> {
}

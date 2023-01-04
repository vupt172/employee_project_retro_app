package com.vupt172.test.fetchcascade;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("test")
public interface TestCompanyRepo extends JpaRepository<TestCompany,Integer> {
}

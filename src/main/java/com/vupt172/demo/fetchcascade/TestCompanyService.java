package com.vupt172.demo.fetchcascade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
@Service
@Transactional
@Profile("demo")
public class TestCompanyService {
    @Autowired
    TestCompanyRepo testCompanyRepo;
    @PersistenceContext
    private EntityManager em;
    @Transactional(propagation= Propagation.NESTED,
            isolation= Isolation.READ_UNCOMMITTED)
    public List<TestCompany> saveMultiple(List<TestCompany> list) throws Exception{
        testCompanyRepo.saveAll(list);
        throw new Exception("exception");
 // return  list;
    }

}

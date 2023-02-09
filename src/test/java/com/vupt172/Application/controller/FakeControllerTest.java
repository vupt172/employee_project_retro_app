package com.vupt172.Application.controller;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(FakeControllerTest.class)
@ContextConfiguration(classes = FakeControllerTest.class)
public class FakeControllerTest {
    @Autowired
    private MockMvc mvc;
    @Test
    public void test(){
        Assertions.assertTrue(true);
    }
}

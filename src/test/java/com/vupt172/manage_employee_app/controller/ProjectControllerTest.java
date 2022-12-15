package com.vupt172.manage_employee_app.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;

@ExtendWith(MockitoExtension.class)
//@ContextConfiguration(classes = { ApplicationConfig.class })
//@WebAppConfiguration
//@WebMvcTest(controllers = ProjectController.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProjectControllerTest {

    @Autowired
    private TestRestTemplate template;
    @Autowired
    PasswordEncoder passwordEncoder;

/*    @BeforeEach
    public  void setup() throws  Exception{
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }*/
    @Test
    @WithMockUser
    void whenGetAll_returnStatusOK(){
        try{
           // mvc.perform(get("/api/projects")).andExpect(status().isOk());
            ResponseEntity<?> result=template.withBasicAuth("user1", passwordEncoder.encode("12345678"))
                    .getForEntity("/api/projects", Object.class);
            Assertions.assertEquals(HttpStatus.OK,result.getStatusCode());
        }
        catch (Exception e){

        }

    }

}

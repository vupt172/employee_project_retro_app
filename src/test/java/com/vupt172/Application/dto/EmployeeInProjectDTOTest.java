package com.vupt172.Application.dto;

import com.vupt172.dto.EmployeeInProjectDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
public class EmployeeInProjectDTOTest {
    @Autowired
    static Validator validator;
    @BeforeAll
    public static void setupValidatorInstance() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }
    @Test
    void whenBlankProjectRole_ThenOneMethodArgumentNotValidException(){
        EmployeeInProjectDTO employeeInProjectDTO=new EmployeeInProjectDTO();
        employeeInProjectDTO.setProjectRole("   ");
        employeeInProjectDTO.setProjectId(2L);
        employeeInProjectDTO.setEmployeeId(8L);
        employeeInProjectDTO.setStatus("Enable");
        Set<ConstraintViolation<EmployeeInProjectDTO>> violations = validator.validate(employeeInProjectDTO);
        Assertions.assertEquals(violations.size(),1);
        Assertions.assertEquals(violations.stream().collect(Collectors.toList()).get(0).getPropertyPath().toString(),"projectRole");
        Assertions.assertEquals(violations.stream().collect(Collectors.toList()).get(0).getMessage(),"must not be blank");
    }
    @Test
    public void whenProjectRoleOver45Character_ThenOneMethodArgumentNotValidException() {
        EmployeeInProjectDTO employeeInProjectDTO=new EmployeeInProjectDTO();
        employeeInProjectDTO.setProjectRole("012345678901234567890123456789012345678901234A");
        employeeInProjectDTO.setProjectId(2L);
        employeeInProjectDTO.setEmployeeId(8L);
        employeeInProjectDTO.setStatus("Enable");

        Set<ConstraintViolation<EmployeeInProjectDTO>> violations = validator.validate(employeeInProjectDTO);
        Assertions.assertEquals(violations.size(),1);
        Assertions.assertEquals(violations.stream().collect(Collectors.toList()).get(0).getPropertyPath().toString(),"projectRole");
    }
    @Test
    void whenBlankStatusRole_ThenOneMethodArgumentNotValidException(){
        EmployeeInProjectDTO employeeInProjectDTO=new EmployeeInProjectDTO();
        employeeInProjectDTO.setProjectRole("PM");
        employeeInProjectDTO.setProjectId(2L);
        employeeInProjectDTO.setEmployeeId(8L);
        employeeInProjectDTO.setStatus("  ");
        Set<ConstraintViolation<EmployeeInProjectDTO>> violations = validator.validate(employeeInProjectDTO);
        Assertions.assertEquals(violations.size(),1);
        Assertions.assertEquals(violations.stream().collect(Collectors.toList()).get(0).getPropertyPath().toString(),"status");
        Assertions.assertEquals(violations.stream().collect(Collectors.toList()).get(0).getMessage(),"must not be blank");
    }
    @Test
    public void whenStatusOver45Character_ThenOneMethodArgumentNotValidException() {
        EmployeeInProjectDTO employeeInProjectDTO=new EmployeeInProjectDTO();
        employeeInProjectDTO.setStatus("012345678901234567890123456789012345678901234A");
        employeeInProjectDTO.setProjectId(2L);
        employeeInProjectDTO.setEmployeeId(8L);
        employeeInProjectDTO.setProjectRole("PM");

        Set<ConstraintViolation<EmployeeInProjectDTO>> violations = validator.validate(employeeInProjectDTO);
        Assertions.assertEquals(violations.size(),1);
        Assertions.assertEquals(violations.stream().collect(Collectors.toList()).get(0).getPropertyPath().toString(),"status");
    }
}

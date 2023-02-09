package com.vupt172.Application.dto;

import com.vupt172.dto.ProjectDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
public class ProjectDTOTest {
    static Validator validator;

    @BeforeAll
    public static void setupValidatorInstance() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void whenBlankName_ThenOneMethodArgumentNotValidException() {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setName(" ");
        projectDTO.setStatus("Enable");
        Set<ConstraintViolation<ProjectDTO>> violations = validator.validate(projectDTO);
        Assertions.assertThat(violations.size()).isEqualTo(1);
        Assertions.assertThat(violations.stream().collect(Collectors.toList()).get(0).getPropertyPath().toString()).isEqualTo("name");
    }

    @Test
    public void whenNameOver255Character_ThenOneMethodArgumentNotValidException() {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setName("012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234A");
        projectDTO.setStatus("Enable");
        Set<ConstraintViolation<ProjectDTO>> violations = validator.validate(projectDTO);
        Assertions.assertThat(violations.size()).isEqualTo(1);
        Assertions.assertThat(violations.stream().collect(Collectors.toList()).get(0).getPropertyPath().toString()).isEqualTo("name");
        Assertions.assertThat(violations.stream().collect(Collectors.toList()).get(0).getMessage()).isEqualTo("name must be lesser than 256 characters");
    }
    @Test
    public void whenBlankStatus_ThenOneMethodArgumentNotValidException(){
        ProjectDTO projectDTO=new ProjectDTO();
        projectDTO.setName("Project ABC");
        projectDTO.setStatus("");
        Set<ConstraintViolation<ProjectDTO>> violations = validator.validate(projectDTO);
        Assertions.assertThat(violations.size()).isEqualTo(1);
        Assertions.assertThat(violations.stream().collect(Collectors.toList()).get(0).getPropertyPath().toString()).isEqualTo("status");
        Assertions.assertThat(violations.stream().collect(Collectors.toList()).get(0).getMessage()).isEqualTo("must not be blank");
    }
    @Test
    public void whenStatusOver255Character_ThenOneMethodArgumentNotValidException(){
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setStatus("012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234A");
        projectDTO.setName("Project ABC");
        Set<ConstraintViolation<ProjectDTO>> violations = validator.validate(projectDTO);
        Assertions.assertThat(violations.size()).isEqualTo(1);
        Assertions.assertThat(violations.stream().collect(Collectors.toList()).get(0).getPropertyPath().toString()).isEqualTo("status");
        Assertions.assertThat(violations.stream().collect(Collectors.toList()).get(0).getMessage()).isEqualTo("status must be lesser than 256 characters");
    }
    @Test
    public void whenValidProjectDTO_ThenSuccess(){
        ProjectDTO projectDTO=new ProjectDTO();
        projectDTO.setName("Project ABC");
        projectDTO.setStatus("Enable");
        Set<ConstraintViolation<ProjectDTO>> violations = validator.validate(projectDTO);
        Assertions.assertThat(violations.size()).isEqualTo(0);
    }


}

package com.vupt172.manage_employee_app.dto;

import com.vupt172.dto.EmployeeDTO;
import org.assertj.core.api.Assertions;
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
public class EmployeeDTOTest {
    @Autowired
   static Validator validator;
    @BeforeAll
    public static void setupValidatorInstance() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }
    @Test
    void whenBlankFullName_ThenOneMethodArgumentNotValidException(){
        EmployeeDTO employeeDTO=new EmployeeDTO();
        employeeDTO.setFullName(" ");
        employeeDTO.setStatus("Enable");
        employeeDTO.setUsername("user1");
        employeeDTO.setEmail("user1@gmail.com");
        employeeDTO.setPhone("0971905583");
        employeeDTO.setPassword("12345678");
        employeeDTO.setRole(2);
        Set<ConstraintViolation<EmployeeDTO>> violations = validator.validate(employeeDTO);
        Assertions.assertThat(violations.size()).isEqualTo(1);
        Assertions.assertThat(violations.stream().collect(Collectors.toList()).get(0).getPropertyPath().toString()).isEqualTo("fullName");
    }
    @Test
    public void whenFullNameOver255Character_ThenOneMethodArgumentNotValidException() {
        EmployeeDTO employeeDTO=new EmployeeDTO();
        employeeDTO.setFullName("012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234A");
        employeeDTO.setStatus("Enable");
        employeeDTO.setUsername("user1");
        employeeDTO.setEmail("user1@gmail.com");
        employeeDTO.setPhone("0971905583");
        employeeDTO.setPassword("12345678");
        employeeDTO.setRole(2);
        Set<ConstraintViolation<EmployeeDTO>> violations = validator.validate(employeeDTO);
        Assertions.assertThat(violations.size()).isEqualTo(1);
        Assertions.assertThat(violations.stream().collect(Collectors.toList()).get(0).getPropertyPath().toString()).isEqualTo("fullName");
    }
    @Test
    void whenBlankUsername_ThenOneMethodArgumentNotValidException(){
        EmployeeDTO employeeDTO=new EmployeeDTO();
        employeeDTO.setFullName("Employee 1");
        employeeDTO.setStatus("Enable");
        employeeDTO.setUsername(" ");
        employeeDTO.setEmail("user1@gmail.com");
        employeeDTO.setPhone("0971905583");
        employeeDTO.setPassword("12345678");
        employeeDTO.setRole(2);
        Set<ConstraintViolation<EmployeeDTO>> violations = validator.validate(employeeDTO);
        Assertions.assertThat(violations.size()).isEqualTo(1);
        Assertions.assertThat(violations.stream().collect(Collectors.toList()).get(0).getPropertyPath().toString()).isEqualTo("username");
    }
    @Test
    public void whenUsernameOver255Character_ThenOneMethodArgumentNotValidException() {
        EmployeeDTO employeeDTO=new EmployeeDTO();
        employeeDTO.setUsername("012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234A");
        employeeDTO.setStatus("Enable");
        employeeDTO.setFullName("Employee ABC");
        employeeDTO.setEmail("user1@gmail.com");
        employeeDTO.setPhone("0971905583");
        employeeDTO.setPassword("12345678");
        employeeDTO.setRole(2);
        Set<ConstraintViolation<EmployeeDTO>> violations = validator.validate(employeeDTO);
        Assertions.assertThat(violations.size()).isEqualTo(1);
        Assertions.assertThat(violations.stream().collect(Collectors.toList()).get(0).getPropertyPath().toString()).isEqualTo("username");
    }
    @Test
    void whenBlankPassword_ThenOneMethodArgumentNotValidException(){
        EmployeeDTO employeeDTO=new EmployeeDTO();
        employeeDTO.setFullName("Employee 1");
        employeeDTO.setStatus("Enable");
        employeeDTO.setUsername("User1");
        employeeDTO.setEmail("user1@gmail.com");
        employeeDTO.setPhone("0971905583");
        employeeDTO.setPassword(" ");
        employeeDTO.setRole(2);
        Set<ConstraintViolation<EmployeeDTO>> violations = validator.validate(employeeDTO);
        Assertions.assertThat(violations.size()).isEqualTo(1);
        Assertions.assertThat(violations.stream().collect(Collectors.toList()).get(0).getPropertyPath().toString()).isEqualTo("password");
    }
    @Test
    public void whenPasswordOver255Character_ThenOneMethodArgumentNotValidException() {
        EmployeeDTO employeeDTO=new EmployeeDTO();
        employeeDTO.setPassword("012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234A");
        employeeDTO.setStatus("Enable");
        employeeDTO.setFullName("Employee ABC");
        employeeDTO.setEmail("user1@gmail.com");
        employeeDTO.setPhone("0971905583");
        employeeDTO.setUsername("User1");
        employeeDTO.setRole(2);
        Set<ConstraintViolation<EmployeeDTO>> violations = validator.validate(employeeDTO);
        Assertions.assertThat(violations.size()).isEqualTo(1);
        Assertions.assertThat(violations.stream().collect(Collectors.toList()).get(0).getPropertyPath().toString()).isEqualTo("password");
    }
    @Test
    void whenBlankEmail_ThenOneMethodArgumentNotValidException(){
        EmployeeDTO employeeDTO=new EmployeeDTO();
        employeeDTO.setFullName("Employee 1");
        employeeDTO.setStatus("Enable");
        employeeDTO.setUsername("User1");
        employeeDTO.setEmail(" ");
        employeeDTO.setPhone("0971905583");
        employeeDTO.setPassword("12345678");
        employeeDTO.setRole(2);
        Set<ConstraintViolation<EmployeeDTO>> violations = validator.validate(employeeDTO);
        Assertions.assertThat(violations.size()).isEqualTo(1);
        Assertions.assertThat(violations.stream().collect(Collectors.toList()).get(0).getPropertyPath().toString()).isEqualTo("email");
    }
    @Test
    public void whenEmailOver255Character_ThenOneMethodArgumentNotValidException() {
        EmployeeDTO employeeDTO=new EmployeeDTO();
        employeeDTO.setEmail("012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234A");
        employeeDTO.setStatus("Enable");
        employeeDTO.setFullName("Employee ABC");
        employeeDTO.setPassword("12345678");
        employeeDTO.setPhone("0971905583");
        employeeDTO.setUsername("User1");
        employeeDTO.setRole(2);
        Set<ConstraintViolation<EmployeeDTO>> violations = validator.validate(employeeDTO);
        Assertions.assertThat(violations.size()).isEqualTo(1);
        Assertions.assertThat(violations.stream().collect(Collectors.toList()).get(0).getPropertyPath().toString()).isEqualTo("email");
    }
    @Test
    void whenBlankPhone_ThenOneMethodArgumentNotValidException(){
        EmployeeDTO employeeDTO=new EmployeeDTO();
        employeeDTO.setFullName("Employee 1");
        employeeDTO.setStatus("Enable");
        employeeDTO.setUsername("User1");
        employeeDTO.setEmail("user1@gmail.com");
        employeeDTO.setPhone("   ");
        employeeDTO.setPassword("12345678");
        employeeDTO.setRole(2);
        Set<ConstraintViolation<EmployeeDTO>> violations = validator.validate(employeeDTO);
        Assertions.assertThat(violations.size()).isEqualTo(1);
        Assertions.assertThat(violations.stream().collect(Collectors.toList()).get(0).getPropertyPath().toString()).isEqualTo("phone");
    }
    @Test
    public void whenPhoneOver11Character_ThenOneMethodArgumentNotValidException() {
        EmployeeDTO employeeDTO=new EmployeeDTO();
        employeeDTO.setEmail("user1@gmail.com");
        employeeDTO.setStatus("Enable");
        employeeDTO.setFullName("Employee ABC");
        employeeDTO.setPassword("12345678");
        employeeDTO.setPhone("097190558300");
        employeeDTO.setUsername("User1");
        employeeDTO.setRole(2);
        Set<ConstraintViolation<EmployeeDTO>> violations = validator.validate(employeeDTO);
        Assertions.assertThat(violations.size()).isEqualTo(1);
        Assertions.assertThat(violations.stream().collect(Collectors.toList()).get(0).getPropertyPath().toString()).isEqualTo("phone");
    }
    @Test
    void whenRoleLesserThan0_ThenOneMethodArgumentNotValidException(){
        EmployeeDTO employeeDTO=new EmployeeDTO();
        employeeDTO.setFullName("Employee 1");
        employeeDTO.setStatus("Enable");
        employeeDTO.setUsername("User1");
        employeeDTO.setEmail("user1@gmail.com");
        employeeDTO.setPhone("0971905583");
        employeeDTO.setPassword("12345678");
        employeeDTO.setRole(-1);
        Set<ConstraintViolation<EmployeeDTO>> violations = validator.validate(employeeDTO);
        Assertions.assertThat(violations.size()).isEqualTo(1);
        Assertions.assertThat(violations.stream().collect(Collectors.toList()).get(0).getPropertyPath().toString()).isEqualTo("role");
    }
    @Test
    void whenRoleGreaterThan2_ThenOneMethodArgumentNotValidException(){
        EmployeeDTO employeeDTO=new EmployeeDTO();
        employeeDTO.setFullName("Employee 1");
        employeeDTO.setStatus("Enable");
        employeeDTO.setUsername("User1");
        employeeDTO.setEmail("user1@gmail.com");
        employeeDTO.setPhone("0971905583");
        employeeDTO.setPassword("12345678");
        employeeDTO.setRole(3);
        Set<ConstraintViolation<EmployeeDTO>> violations = validator.validate(employeeDTO);
        Assertions.assertThat(violations.size()).isEqualTo(1);
        Assertions.assertThat(violations.stream().collect(Collectors.toList()).get(0).getPropertyPath().toString()).isEqualTo("role");
    }
    @Test
    void whenBlankStatus_ThenOneMethodArgumentNotValidException(){
        EmployeeDTO employeeDTO=new EmployeeDTO();
        employeeDTO.setFullName("Employee 1");
        employeeDTO.setStatus(" ");
        employeeDTO.setUsername("User1");
        employeeDTO.setEmail("user1@gmail.com");
        employeeDTO.setPhone("0971905583");
        employeeDTO.setPassword("12345678");
        employeeDTO.setRole(2);
        Set<ConstraintViolation<EmployeeDTO>> violations = validator.validate(employeeDTO);
        Assertions.assertThat(violations.size()).isEqualTo(1);
        Assertions.assertThat(violations.stream().collect(Collectors.toList()).get(0).getPropertyPath().toString()).isEqualTo("status");
    }
    @Test
    public void whenStatusOver255Character_ThenOneMethodArgumentNotValidException() {
        EmployeeDTO employeeDTO=new EmployeeDTO();
        employeeDTO.setStatus("012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234A");
        employeeDTO.setEmail("user1@gmail.com");
        employeeDTO.setFullName("Employee ABC");
        employeeDTO.setPassword("12345678");
        employeeDTO.setPhone("0971905583");
        employeeDTO.setUsername("User1");
        employeeDTO.setRole(2);
        Set<ConstraintViolation<EmployeeDTO>> violations = validator.validate(employeeDTO);
        Assertions.assertThat(violations.size()).isEqualTo(1);
        Assertions.assertThat(violations.stream().collect(Collectors.toList()).get(0).getPropertyPath().toString()).isEqualTo("status");
    }
}

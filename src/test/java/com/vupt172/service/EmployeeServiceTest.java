package com.vupt172.service;

import com.vupt172.converter.EmployeeConverter;
import com.vupt172.dto.EmployeeDTO;
import com.vupt172.entity.Employee;
import com.vupt172.exception.DataUniqueException;
import com.vupt172.exception.ElementNotExistException;
import com.vupt172.exception.OverPermissionException;
import com.vupt172.manage_employee_app.TestData.EmployeeTestData;
import com.vupt172.repository.EmployeeInProjectRepository;
import com.vupt172.repository.EmployeeRepository;
import com.vupt172.security.service.UserDetailsImpl;
import com.vupt172.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
    @Mock
    EmployeeRepository employeeRepository;
    @Spy
    EmployeeConverter employeeConverter;
    @Mock
    EmployeeInProjectRepository employeeInProjectRepository;
    @InjectMocks
    EmployeeServiceImpl employeeService;


    @BeforeEach
    void setup() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        ReflectionTestUtils.setField(employeeConverter, "passwordEncoder", passwordEncoder);
    }

    @Test
    void whenFindAll_ShouldReturnDTOList() {
        Mockito.when(employeeRepository.findAll()).thenReturn(EmployeeTestData.employeeList);
        List<EmployeeDTO> dtoList = employeeService.findAll();
        Assertions.assertTrue(dtoList.size() == 3);
    }

    @Test
    void whenFindById_returnOptional_Empty() {
        //set up
        Mockito.when(employeeRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        //test
        Optional<EmployeeDTO> dto = employeeService.findById(1L);
        Assertions.assertTrue(dto.isEmpty());
        Mockito.verify(employeeRepository).findById(Mockito.anyLong());
    }

    @Test
    void whenFindById_returnOptional_Present() {
        //set up
        Employee employee1 = new Employee();
        employee1.setId(1L);
        employee1.setUsername("employee1");
        employee1.setPassword("12345678");
        employee1.setFullName("Employee 1");
        employee1.setEmail("user1@gmail.com");
        employee1.setPhone("0971905583");
        employee1.setStatus("Enable");
        LocalDate date = LocalDate.of(2022, 12, 17);
        employee1.setBirthDay(Date.valueOf(date));
        Mockito.when(employeeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(employee1));
        Optional<EmployeeDTO> dto = employeeService.findById(1L);
        Assertions.assertTrue(dto.isPresent());
        Mockito.verify(employeeRepository).findById(Mockito.anyLong());
    }

    @Test
    void whenCreate_WithExistUserName_ThrowDataUniqueException() {
        Mockito.when(employeeRepository.existsByUsername(Mockito.anyString())).thenReturn(true);
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setUsername("usernameabc");
        employeeDTO.setPassword("12345678");
        employeeDTO.setPhone("0971905583");
        employeeDTO.setFullName("Employee ABC");
        employeeDTO.setEmail("employeeabc@gmail.com");
        employeeDTO.setStatus("Enable");
        employeeDTO.setRole(2);
        employeeDTO.setBirthDay(Date.valueOf(LocalDate.of(2022, 02, 18)));
        //test
        DataUniqueException exception = Assertions.assertThrows(DataUniqueException.class, () -> employeeService.create(employeeDTO));
        String expectMessage = "Username is unique";
        Assertions.assertEquals(expectMessage, exception.getMessage());
    }

    @Test
    void whenCreate_WithExistPhone_ThrowDataUniqueException() {
        Mockito.when(employeeRepository.existsByUsername(Mockito.anyString())).thenReturn(false);
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setUsername("usernameabc");
        employeeDTO.setPassword("12345678");
        employeeDTO.setPhone("0971905583");
        employeeDTO.setFullName("Employee ABC");
        employeeDTO.setEmail("employeeabc@gmail.com");
        employeeDTO.setStatus("Enable");
        employeeDTO.setRole(2);
        employeeDTO.setBirthDay(Date.valueOf(LocalDate.of(2022, 02, 18)));
        Mockito.when(employeeRepository.existsByPhone(Mockito.anyString())).thenReturn(true);
        //test
        DataUniqueException exception = Assertions.assertThrows(DataUniqueException.class, () -> employeeService.create(employeeDTO));
        String expectMessage = "Phone is unique";
        Assertions.assertEquals(expectMessage, exception.getMessage());
    }

    @Test
    void whenCreate_WithExistEmail_ThrowDataUniqueException() {
        Mockito.when(employeeRepository.existsByUsername(Mockito.anyString())).thenReturn(false);
        Mockito.when(employeeRepository.existsByPhone(Mockito.anyString())).thenReturn(false);
        Mockito.when(employeeRepository.existsByEmail(Mockito.anyString())).thenReturn(true);
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setUsername("usernameabc");
        employeeDTO.setPassword("12345678");
        employeeDTO.setPhone("0971905583");
        employeeDTO.setFullName("Employee ABC");
        employeeDTO.setEmail("employeeabc@gmail.com");
        employeeDTO.setStatus("Enable");
        employeeDTO.setRole(2);
        employeeDTO.setBirthDay(Date.valueOf(LocalDate.of(2022, 02, 18)));
        //test
        DataUniqueException exception = Assertions.assertThrows(DataUniqueException.class, () -> employeeService.create(employeeDTO));
        String expectMessage = "Email is unique";
        Assertions.assertEquals(expectMessage, exception.getMessage());
    }

    @Test
    void whenCreate_WithSuperAdminRole_ThrowOverPermissionException() {
        Mockito.when(employeeRepository.existsByUsername(Mockito.anyString())).thenReturn(false);
        Mockito.when(employeeRepository.existsByPhone(Mockito.anyString())).thenReturn(false);
        Mockito.when(employeeRepository.existsByEmail(Mockito.anyString())).thenReturn(false);
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setUsername("usernameabc");
        employeeDTO.setPassword("12345678");
        employeeDTO.setPhone("0971905583");
        employeeDTO.setFullName("Employee ABC");
        employeeDTO.setEmail("employeeabc@gmail.com");
        employeeDTO.setStatus("Enable");
        employeeDTO.setRole(0);
        employeeDTO.setBirthDay(Date.valueOf(LocalDate.of(2022, 02, 18)));
        OverPermissionException exception = Assertions.assertThrows(OverPermissionException.class, () -> employeeService.create(employeeDTO));
        String expectMessage = "Cannot create employee with SUPERADMIN role";
        Assertions.assertEquals(expectMessage, exception.getMessage());
    }

    @Test
    void whenUpdate_NotFoundEmployeeById_ThrowElementNotExistException() {
        Mockito.when(employeeRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(1L);
        employeeDTO.setUsername("usernameabc");
        employeeDTO.setPassword("12345678");
        employeeDTO.setPhone("0971905583");
        employeeDTO.setFullName("Employee ABC");
        employeeDTO.setEmail("employeeabc@gmail.com");
        employeeDTO.setStatus("Enable");
        employeeDTO.setRole(0);
        ElementNotExistException exception = Assertions.assertThrows(ElementNotExistException.class, () -> employeeService.update(employeeDTO));
        String expectMessage = "Employee is not exist with id = " + 1L;
        Assertions.assertEquals(expectMessage, exception.getMessage());
    }

    @Test
    void whenUpdate_WithSuperAdminRole_DownGradeItSelf_ThrowOverPermissionException() {
        Employee dbEmployee = new Employee();
        dbEmployee.setId(3L);
        dbEmployee.setFullName("Super Admin");
        dbEmployee.setUsername("root");
        dbEmployee.setPassword("12345678");
        dbEmployee.setEmail("root101@gmail.com");
        dbEmployee.setPhone("0971905585");
        dbEmployee.setStatus("Enable");
        dbEmployee.setRole(0);
        dbEmployee.setBirthDay(Date.valueOf(LocalDate.of(2022, 02, 18)));
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(3L);
        employeeDTO.setUsername("root");
        employeeDTO.setPassword("12345678");
        employeeDTO.setPhone("0971905583");
        employeeDTO.setFullName("Super Admin");
        employeeDTO.setEmail("root102@gmail.com");
        employeeDTO.setStatus("Enable");
        employeeDTO.setRole(2);
        UserDetailsImpl userDetails = UserDetailsImpl.build(dbEmployee);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Mockito.when(employeeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(dbEmployee));
        //   Mockito.when()
        OverPermissionException exception = Assertions.assertThrows(OverPermissionException.class, () -> employeeService.update(employeeDTO));
    }

    @Test
    void whenUpdate_WithAdminRole_UpdateSuperAdminAccount_ThrowOverPermissionException() {
        Employee dbEmployee = new Employee();
        dbEmployee.setId(3L);
        dbEmployee.setFullName("Super Admin");
        dbEmployee.setUsername("root");
        dbEmployee.setPassword("12345678");
        dbEmployee.setEmail("root101@gmail.com");
        dbEmployee.setPhone("0971905585");
        dbEmployee.setStatus("Enable");
        dbEmployee.setRole(0);
        dbEmployee.setBirthDay(Date.valueOf(LocalDate.of(2022, 02, 18)));
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(3L);
        employeeDTO.setUsername("root");
        employeeDTO.setPassword("12345678");
        employeeDTO.setPhone("0971905583");
        employeeDTO.setFullName("Super Admin");
        employeeDTO.setEmail("root102@gmail.com");
        employeeDTO.setStatus("Enable");
        employeeDTO.setRole(2);
        //authentication
        UserDetailsImpl userDetails = new UserDetailsImpl(1L, "admin1", "vupt101@gmaiil.com", "12345678", List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //when
        Mockito.when(employeeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(dbEmployee));
        //test
        OverPermissionException exception = Assertions.assertThrows(OverPermissionException.class, () -> employeeService.update(employeeDTO));
        String expectMessage = "ADMIN cannot update SUPER ADMIN";
        Assertions.assertEquals(expectMessage, exception.getMessage());
    }

    @Test
    void whenUpdate_WithAdminRole_UpdateAdminAccount_ThrowOverPermissionException() {
        Employee dbEmployee = new Employee();
        dbEmployee.setId(1L);
        dbEmployee.setFullName("Pham Tuan Vu");
        dbEmployee.setUsername("admin2");
        dbEmployee.setPassword("12345678");
        dbEmployee.setEmail("vupt102@gmail.com");
        dbEmployee.setPhone("0971905585");
        dbEmployee.setStatus("Enable");
        dbEmployee.setRole(1);
        dbEmployee.setBirthDay(Date.valueOf(LocalDate.of(2022, 02, 18)));
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(1L);
        employeeDTO.setUsername("admin2");
        employeeDTO.setPassword("12345678");
        employeeDTO.setPhone("0971905583");
        employeeDTO.setFullName("Admin 2");
        employeeDTO.setEmail("vupt102@gmail.com");
        employeeDTO.setStatus("Enable");
        employeeDTO.setRole(2);
        //authentication
        UserDetailsImpl userDetails = new UserDetailsImpl(1L, "admin1", "vupt101@gmaiil.com", "12345678", List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //when
        Mockito.when(employeeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(dbEmployee));
        //test
        OverPermissionException exception = Assertions.assertThrows(OverPermissionException.class, () -> employeeService.update(employeeDTO));
        String expectMessage = "ADMIN cannot update ADMIN";
        Assertions.assertEquals(expectMessage, exception.getMessage());
    }

    @Test
    void whenUpdate_WithMoreOneSuperAdmin_ThrowOverPermissionException() {
        Employee dbEmployee = new Employee();
        dbEmployee.setId(1L);
        dbEmployee.setFullName("Pham Tuan Vu");
        dbEmployee.setUsername("admin2");
        dbEmployee.setPassword("12345678");
        dbEmployee.setEmail("vupt102@gmail.com");
        dbEmployee.setPhone("0971905585");
        dbEmployee.setStatus("Enable");
        dbEmployee.setRole(1);
        dbEmployee.setBirthDay(Date.valueOf(LocalDate.of(2022, 02, 18)));
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(1L);
        employeeDTO.setUsername("admin2");
        employeeDTO.setPassword("12345678");
        employeeDTO.setPhone("0971905583");
        employeeDTO.setFullName("Admin 2");
        employeeDTO.setEmail("vupt102@gmail.com");
        employeeDTO.setStatus("Enable");
        employeeDTO.setRole(0);
        //authentication
        UserDetailsImpl userDetails = new UserDetailsImpl(3L, "root", "root101@gmaiil.com", "12345678", List.of(new SimpleGrantedAuthority("ROLE_SUPERADMIN")));
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //when
        Mockito.when(employeeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(dbEmployee));
        //test
        OverPermissionException exception = Assertions.assertThrows(OverPermissionException.class, () -> employeeService.update(employeeDTO));
        String expectMessage = "Super Admin is only";
        Assertions.assertEquals(expectMessage, exception.getMessage());
    }

    @Test
    void whenUpdate_HasAnyUniqueField_ThrowDataUniqueException() {
        Employee dbEmployee = new Employee();
        dbEmployee.setId(3L);
        dbEmployee.setFullName("Super Admin");
        dbEmployee.setUsername("root");
        dbEmployee.setPassword("12345678");
        dbEmployee.setEmail("root101@gmail.com");
        dbEmployee.setPhone("0971905585");
        dbEmployee.setStatus("Enable");
        dbEmployee.setRole(0);
        dbEmployee.setBirthDay(Date.valueOf(LocalDate.of(2022, 02, 18)));

        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(3L);
        employeeDTO.setUsername("root");
        employeeDTO.setPassword("12345678");
        employeeDTO.setPhone("0971905584");
        employeeDTO.setFullName("Super Admin");
        employeeDTO.setEmail("vupt101@gmail.com");
        employeeDTO.setStatus("Enable");
        employeeDTO.setRole(0);
        //employeeByEmail
        Employee employeeByEmail = new Employee();
        employeeByEmail.setId(1L);
        employeeByEmail.setFullName("Pham Tuan Vu");
        employeeByEmail.setUsername("admin1");
        employeeByEmail.setPassword("12345678");
        employeeByEmail.setEmail("vupt101@gmail.com");
        employeeByEmail.setPhone("097190553");
        employeeByEmail.setStatus("Enable");
        employeeByEmail.setRole(1);
        employeeByEmail.setBirthDay(Date.valueOf(LocalDate.of(2022, 02, 18)));
        //employeeByPhone
        Employee employeeByPhone = new Employee();
        employeeByPhone.setId(1L);
        employeeByPhone.setFullName("Pham Tuan Vu");
        employeeByPhone.setUsername("admin2");
        employeeByPhone.setPassword("12345678");
        employeeByPhone.setEmail("vupt102@gmail.com");
        employeeByPhone.setPhone("097190554");
        employeeByPhone.setStatus("Enable");
        employeeByPhone.setRole(0);
        employeeByPhone.setBirthDay(Date.valueOf(LocalDate.of(2022, 02, 18)));
        //authentication
        UserDetailsImpl userDetails = new UserDetailsImpl(3L, "root", "root101@gmaiil.com", "12345678", List.of(new SimpleGrantedAuthority("ROLE_SUPERADMIN")));
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //when
        Mockito.when(employeeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(dbEmployee));
        Mockito.when(employeeRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(employeeByEmail));
        Mockito.when(employeeRepository.findByPhone(Mockito.anyString())).thenReturn(Optional.of(employeeByPhone));
        //test
        DataUniqueException exception=Assertions.assertThrows(DataUniqueException.class,()->employeeService.update(employeeDTO));
    }
    @Test
    void whenUpdate_ThenSuccess(){
        Employee dbEmployee = new Employee();
        dbEmployee.setId(3L);
        dbEmployee.setFullName("Super Admin");
        dbEmployee.setUsername("root");
        dbEmployee.setPassword("12345678");
        dbEmployee.setEmail("root101@gmail.com");
        dbEmployee.setPhone("0971905585");
        dbEmployee.setStatus("Enable");
        dbEmployee.setRole(0);
        dbEmployee.setBirthDay(Date.valueOf(LocalDate.of(2022, 02, 18)));

        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(3L);
        employeeDTO.setUsername("root");
        employeeDTO.setPassword("12345678");
        employeeDTO.setPhone("0971905591");
        employeeDTO.setFullName("Super Admin");
        employeeDTO.setEmail("root1011@gmail.com");
        employeeDTO.setStatus("Enable");
        employeeDTO.setRole(0);
        //authentication
        UserDetailsImpl userDetails = new UserDetailsImpl(3L, "root", "root101@gmaiil.com", "12345678", List.of(new SimpleGrantedAuthority("ROLE_SUPERADMIN")));
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //when
        Mockito.when(employeeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(dbEmployee));
        Mockito.when(employeeRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(employeeRepository.findByPhone(Mockito.anyString())).thenReturn(Optional.empty());
        Employee updatingEmployee=employeeConverter.toEntity(employeeDTO,dbEmployee);
        Mockito.when(employeeRepository.save(Mockito.any(Employee.class))).thenReturn(updatingEmployee);
        //test
        EmployeeDTO returnDTO=employeeService.update(employeeDTO);
        Assertions.assertEquals(returnDTO.getId(),updatingEmployee.getId());
    }
    @Test
    public void whenDelete_WithNotExistEmployeeById_ThrowElementException(){
        //when
        Mockito.when(employeeRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        //test
        ElementNotExistException exception=Assertions.assertThrows(ElementNotExistException.class,()->employeeService.delete(1L));
        Mockito.verify(employeeRepository).findById(Mockito.anyLong());
    }
    @Test
    public void whenDelete_WithSuperAdminRole_DeleteItSelf_ThrowOverPermissionException(){
        Employee dbEmployee = new Employee();
        dbEmployee.setId(3L);
        dbEmployee.setFullName("Super Admin");
        dbEmployee.setUsername("root");
        dbEmployee.setPassword("12345678");
        dbEmployee.setEmail("root101@gmail.com");
        dbEmployee.setPhone("0971905585");
        dbEmployee.setStatus("Enable");
        dbEmployee.setRole(0);
        dbEmployee.setBirthDay(Date.valueOf(LocalDate.of(2022, 02, 18)));
        //when
        Mockito.when(employeeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(dbEmployee));
        //authentication
        UserDetailsImpl userDetails = new UserDetailsImpl(3L, "root", "root101@gmaiil.com", "12345678", List.of(new SimpleGrantedAuthority("ROLE_SUPERADMIN")));
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //test
        OverPermissionException exception=Assertions.assertThrows(OverPermissionException.class,()->employeeService.delete(3L));
        Mockito.verify(employeeRepository).findById(3L);
        String expectMessage="SUPER ADMIN cannot delete own itself";
        Assertions.assertEquals(expectMessage,exception.getMessage());
    }
    @Test
    public void whenDelete_WithAdminRole_DeleteSuperAdmin_ThrowOverPermissionException(){
        Employee dbEmployee = new Employee();
        dbEmployee.setId(3L);
        dbEmployee.setFullName("Super Admin");
        dbEmployee.setUsername("root");
        dbEmployee.setPassword("12345678");
        dbEmployee.setEmail("root101@gmail.com");
        dbEmployee.setPhone("0971905585");
        dbEmployee.setStatus("Enable");
        dbEmployee.setRole(0);
        dbEmployee.setBirthDay(Date.valueOf(LocalDate.of(2022, 02, 18)));
        //authentication
        UserDetailsImpl userDetails = new UserDetailsImpl(1L, "admin1", "admin101@gmaiil.com", "12345678", List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //when
        Mockito.when(employeeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(dbEmployee));
        //test
        OverPermissionException exception=Assertions.assertThrows(OverPermissionException.class,()->employeeService.delete(3L));
        String expectMessage="ADMIN cannot delete SUPER ADMIN";
        Assertions.assertEquals(expectMessage,exception.getMessage());
    }
    @Test
    public void whenDelete_WithAdminRole_DeleteAdmin_ThrowOverPermissionException(){
        Employee dbEmployee = new Employee();
        dbEmployee.setId(1L);
        dbEmployee.setFullName("Pham Tuan Vu");
        dbEmployee.setUsername("admin2");
        dbEmployee.setPassword("12345678");
        dbEmployee.setEmail("vupt102@gmail.com");
        dbEmployee.setPhone("0971905585");
        dbEmployee.setStatus("Enable");
        dbEmployee.setRole(1);
        dbEmployee.setBirthDay(Date.valueOf(LocalDate.of(2022, 02, 18)));
        //authentication
        UserDetailsImpl userDetails = new UserDetailsImpl(1L, "admin1", "admin101@gmaiil.com", "12345678", List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //when
        Mockito.when(employeeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(dbEmployee));
        //test
        OverPermissionException exception=Assertions.assertThrows(OverPermissionException.class,()->employeeService.delete(1L));
       String expectMessage="ADMIN cannot delete ADMIN";
       Assertions.assertEquals(expectMessage,exception.getMessage());
    }
    @Test
    public void whenDelete_WithEmployeeInProject_ThenChangeStatusDisable(){
        //data
        Employee dbEmployee = new Employee();
        dbEmployee.setId(1L);
        dbEmployee.setFullName("Pham Tuan Vu");
        dbEmployee.setUsername("user1");
        dbEmployee.setPassword("12345678");
        dbEmployee.setEmail("user1@gmail.com");
        dbEmployee.setPhone("0971905585");
        dbEmployee.setStatus("Enable");
        dbEmployee.setRole(2);

        Employee deletingEmployee=new Employee();
        deletingEmployee.setId(1L);
        deletingEmployee.setFullName("Pham Tuan Vu");
        deletingEmployee.setUsername("user1");
        deletingEmployee.setPassword("12345678");
        deletingEmployee.setEmail("user1@gmail.com");
        deletingEmployee.setPhone("0971905585");
        deletingEmployee.setStatus("Disable");
        deletingEmployee.setRole(2);
        //authentication
        UserDetailsImpl userDetails = new UserDetailsImpl(1L, "admin1", "admin101@gmaiil.com", "12345678", List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //when
        Mockito.when(employeeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(dbEmployee));
        Mockito.when(employeeInProjectRepository.existsByEmployee_Id(Mockito.anyLong())).thenReturn(true);
       Mockito.when(employeeRepository.save(Mockito.any(Employee.class))).thenReturn(deletingEmployee);
       //test
        EmployeeDTO returnDTO=employeeService.delete(1L);
        Assertions.assertEquals(returnDTO.getStatus(),deletingEmployee.getStatus());
    }
    @Test
    public void whenDelete_ThenSuccess(){
        //data
        Employee dbEmployee = new Employee();
        dbEmployee.setId(7L);
        dbEmployee.setFullName("Pham Tuan Vu");
        dbEmployee.setUsername("user1");
        dbEmployee.setPassword("12345678");
        dbEmployee.setEmail("user1@gmail.com");
        dbEmployee.setPhone("0971905585");
        dbEmployee.setStatus("Enable");
        dbEmployee.setRole(2);
        //authentication
        UserDetailsImpl userDetails = new UserDetailsImpl(1L, "admin1", "admin101@gmaiil.com", "12345678", List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //when
        Mockito.when(employeeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(dbEmployee));
        Mockito.when(employeeInProjectRepository.existsByEmployee_Id(Mockito.anyLong())).thenReturn(false);
        //Mockito.when(employeeRepository.delete(Mockito.any(Employee.class));).thenReturn(deletingEmployee);
        //test
        EmployeeDTO returnDTO=employeeService.delete(7L);
        Assertions.assertEquals(returnDTO.getStatus(),"Deleted");
        Mockito.verify(employeeRepository).delete(Mockito.any(Employee.class));
    }
}

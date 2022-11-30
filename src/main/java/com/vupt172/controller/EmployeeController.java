package com.vupt172.controller;

import com.vupt172.dto.EmployeeDTO;
import com.vupt172.security.jwt.JwtUtils;
import com.vupt172.service.IEmployeeService;
import com.vupt172.utils.UserDetailUtil;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    private IEmployeeService employeeService;
    @GetMapping("")
    public ResponseEntity<List<EmployeeDTO>> getAll(){
        return ResponseEntity.ok(employeeService.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id){
        EmployeeDTO employeeDTO=employeeService.findById(id).orElse(null);
        if(employeeDTO==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(employeeDTO);
    }
    @PostMapping("")
    public ResponseEntity<EmployeeDTO> createEmployee(@Valid  @RequestBody EmployeeDTO employeeDTO){
        return ResponseEntity.ok(employeeService.create(employeeDTO));
    }
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable Long id,@RequestBody EmployeeDTO employeeDTO){
        employeeDTO.setId(id);
        return ResponseEntity.ok(employeeService.update(employeeDTO));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<EmployeeDTO> deleteEmployee(@PathVariable Long id){
        return ResponseEntity.ok(employeeService.delete(id));
    }


}

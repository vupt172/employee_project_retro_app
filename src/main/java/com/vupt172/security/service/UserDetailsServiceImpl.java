package com.vupt172.security.service;

import com.vupt172.entity.Employee;
import com.vupt172.exception.ElementNotExistException;
import com.vupt172.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    EmployeeRepository employeeRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee =employeeRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("User not found with username"+username));
        return UserDetailsImpl.build(employee);
    }
    public UserDetails loadUserByEmail(String email){
        Employee employee=employeeRepository.findByEmail(email)
                .orElseThrow(()-> new ElementNotExistException("Employee not exist with email = "+email));
        return UserDetailsImpl.build(employee);
    }
}

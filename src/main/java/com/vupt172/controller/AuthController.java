package com.vupt172.controller;

import com.vupt172.exception.exceptionHandling.ApiError;
import com.vupt172.repository.EmployeeRepository;
import com.vupt172.security.dto.LoginRequest;
import com.vupt172.security.dto.LoginResponse;
import com.vupt172.security.dto.TokenRefreshRequest;
import com.vupt172.security.dto.TokenRefreshResponse;
import com.vupt172.security.jwt.JwtUtils;
import com.vupt172.security.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtUtils jwtUtils;
    @PostMapping("/login")
    public ResponseEntity<?> authenticateuser( @RequestBody LoginRequest loginRequest){
        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword())
        );
        /*SecurityContextHolder.getContext().setAuthentication(authentication);*/
        String jwt=jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails=(UserDetailsImpl) authentication.getPrincipal();
        Set<String> roles=userDetails.getAuthorities().stream()
                .map(item->item.getAuthority())
                .collect(Collectors.toSet());
        //create RefreshToken
        String refreshToken=jwtUtils.generateRefreshToken(authentication);
        return ResponseEntity.ok(new LoginResponse(
                jwt,
                refreshToken,
                "Bearer",
                userDetails.getId(),
                userDetails.getEmail(),
                roles
        ));
    }
    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshRequest tokenRefreshRequest, WebRequest request
            ){
       HashMap<String,Object> validateResult=jwtUtils.validateJwtToken1(tokenRefreshRequest.getRefreshToken());
       if(!(boolean) validateResult.get("isValidate")){
           ApiError apiError=new ApiError(HttpStatus.BAD_REQUEST.value(), new Date(),"JWT Exception",validateResult.get("message"),request.getDescription(false)
           );
           return new ResponseEntity<>(apiError,HttpStatus.BAD_REQUEST);
       }
          String username= jwtUtils.getUsernameFromJwtToken(tokenRefreshRequest.getRefreshToken());
          String newJwtToken=jwtUtils.generateJwtTokenFromUsername(username);
          return ResponseEntity.ok(new TokenRefreshResponse(
                  newJwtToken,
                  tokenRefreshRequest.getRefreshToken(),
                  "Bearer"
          ));

    }
    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(){
        SecurityContextHolder.getContext().setAuthentication(null);
        return ResponseEntity.ok("Logout successfully.");
    }
}

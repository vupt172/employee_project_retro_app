package com.vupt172.security.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vupt172.exception.exceptionHandling.ApiError;
import com.vupt172.repository.EmployeeRepository;
import com.vupt172.security.dto.LoginResponse;
import com.vupt172.security.jwt.JwtUtils;
import com.vupt172.security.oauth2.google.GoogleMailInfo;
import com.vupt172.security.oauth2.google.GoogleOAuth2Util;
import com.vupt172.security.service.UserDetailsImpl;
import com.vupt172.security.service.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/oauth2/google")
public class GoogleOAuth2AuthorizationController  {
    @Autowired
    GoogleOAuth2Util googleOAuth2Util;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    JwtUtils jwtUtils;
    public static Logger logger= LoggerFactory.getLogger(GoogleOAuth2AuthorizationController.class) ;
    @GetMapping("/response-code")
    public Object getResponseCodeAfterLoginWithGoogle(HttpServletRequest request, HttpServletResponse response) throws Exception{
     googleOAuth2Util.extractResponseCodeURL(request);
     googleOAuth2Util.getTokenFromResponseCode();
     googleOAuth2Util.buildCredential();
     GoogleMailInfo googleMailInfo=googleOAuth2Util.getGoogleMailInfo();
     if(!employeeRepository.existsByEmail(googleMailInfo.getEmail())){
         ApiError apiError=new ApiError(HttpStatus.BAD_REQUEST.value(),new Date(),
                 "Login Google Exception",
                 "Employee not exist with email = "+googleMailInfo.getEmail(),
                 request.getServletPath()
                 );
         return apiError;
     }
        UserDetailsImpl userDetails= (UserDetailsImpl) userDetailsService.loadUserByEmail(googleMailInfo.getEmail());
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt=jwtUtils.generateJwtToken(authentication);
        String refreshToken=jwtUtils.generateRefreshToken(authentication);
        return ResponseEntity.ok(new LoginResponse(
                jwt,
                refreshToken,
                "Bearer",
                userDetails.getId(),
                userDetails.getEmail(),
                userDetails.getAuthorities().stream().map(e->e.getAuthority()).collect(Collectors.toList())
        ));
    }
    public String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}

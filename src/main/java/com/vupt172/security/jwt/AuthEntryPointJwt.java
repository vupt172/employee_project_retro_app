package com.vupt172.security.jwt;

import com.vupt172.exception.exceptionHandling.ApiError;
import com.vupt172.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {
    private static final Logger logger= LoggerFactory.getLogger(AuthEntryPointJwt.class);
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
     logger.error("Unauthorized error: {}",authException.getMessage());
        ApiError apiError=new ApiError(
                HttpStatus.UNAUTHORIZED.value(),
                new Date(),
                "Authentication Exception",
                authException.getMessage(),
                request.getServletPath()
        );
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("text/json");
        response.getWriter().write(JsonUtil.convertObjectToJson(apiError));
     return ;
    }
}

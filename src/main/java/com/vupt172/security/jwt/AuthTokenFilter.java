package com.vupt172.security.jwt;

import com.vupt172.exception.JwtValidateException;
import com.vupt172.exception.exceptionHandling.ApiError;
import com.vupt172.security.service.UserDetailsServiceImpl;
import com.vupt172.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    private static final Logger logger= LoggerFactory.getLogger(AuthTokenFilter.class);
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException,JwtValidateException {
   try {
       System.out.println(SecurityContextHolder.getContext().getAuthentication());
       System.out.println("doFilterInternal");
       //throw new JwtValidateException("Jwt validate exception");
       String jwt = parseJwt(request);
       if (jwt!=null&&jwtUtils.validateJwtToken(jwt)) {
           String username = jwtUtils.getUsernameFromJwtToken(jwt);
           UserDetails userDetails = userDetailsService.loadUserByUsername(username);
           UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
           authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
           SecurityContextHolder.getContext().setAuthentication(authentication);

       }
       filterChain.doFilter(request,response);
   }
   catch(JwtValidateException e){
      System.out.println("-JwtValidateException-"+e.getMessage());
       ApiError apiError=new ApiError(
               HttpStatus.UNAUTHORIZED.value(),
               new Date(),
               "Jwt Validation Exception",
               e.getMessage(),
               request.getServletPath()
       );
       response.setStatus(HttpStatus.UNAUTHORIZED.value());
       response.setContentType("text/json");
       response.getWriter().write(JsonUtil.convertObjectToJson(apiError));
   }
    }
    private String parseJwt(HttpServletRequest request){

        String headerAuth=request.getHeader("Authorization");
        if(StringUtils.hasText(headerAuth)&&headerAuth.startsWith("Bearer ")){
            return headerAuth.substring(7,headerAuth.length());
        }
        return null;
    }

}

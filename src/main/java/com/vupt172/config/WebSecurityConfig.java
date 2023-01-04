package com.vupt172.config;

import com.vupt172.security.jwt.AuthEntryPointJwt;
import com.vupt172.security.jwt.AuthTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
//@Profile(value = {"dev","local"})
public class WebSecurityConfig {
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;
    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter(){
        return new AuthTokenFilter();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        System.out.println("filterChain");
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests().antMatchers("/test/**","/sync").permitAll().and()
                .authorizeRequests().antMatchers("/api/auth/login","/api/auth/refreshtoken").permitAll()
                .antMatchers("/api/employees/**").hasAnyRole("SUPERADMIN","ADMIN")
                .antMatchers(HttpMethod.GET,"/api/projects/**").hasAnyRole("USER","SUPERADMIN","ADMIN")
                .antMatchers("/api/projects/**").hasAnyRole("SUPERADMIN","ADMIN")
                .antMatchers(HttpMethod.DELETE,"/api/evaluations/**").hasAnyRole("SUPERADMIN","ADMIN")
                .anyRequest().authenticated();

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();

    }
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        System.out.println("webSecurityCustomizer");
        return (web -> web.ignoring().antMatchers("/fake/**"));
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        System.out.println("passwordEncoder");
        return new BCryptPasswordEncoder();
    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        System.out.println("authenticationProvider");
        DaoAuthenticationProvider authProvider=new DaoAuthenticationProvider();
        authProvider.setUserDetailsService((UserDetailsService) userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration) throws Exception {
        System.out.println("authenticationManager");
        return authConfiguration.getAuthenticationManager();
    }
}


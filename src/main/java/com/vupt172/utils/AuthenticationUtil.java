package com.vupt172.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
public class AuthenticationUtil {
    private Authentication authentication;
    private UserDetails userDetails;


    public AuthenticationUtil(Authentication authentication){
    this.authentication=authentication;
    this.userDetails=(UserDetails) authentication.getPrincipal();
    }
   public String getAuthUsername(){return userDetails.getUsername();}
    public boolean hasSuperAdminRole(){
       return this.userDetails.getAuthorities().stream().anyMatch(authority->authority.getAuthority().equals("ROLE_SUPERADMIN"));
    }
    public boolean hasAdminRole(){
        return this.userDetails.getAuthorities().stream().anyMatch(authority->authority.getAuthority().equals("ROLE_ADMIN"));
    }
    public boolean hasUserRole(){
        return this.userDetails.getAuthorities().stream().anyMatch(authority->authority.getAuthority().equals("ROLE_USER"));
    }

}

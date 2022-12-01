package com.vupt172.utils;

import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;
public class AuthenticationUtil {
    private  UserDetails userDetails;


    public AuthenticationUtil(UserDetails userDetails){
    this.userDetails=userDetails;
    }
   public String getAuthUsername(){return userDetails.getUsername();}
    public boolean hasSuperAdminRole(){
       return this.userDetails.getAuthorities().stream().anyMatch(authority->authority.getAuthority().equals("SUPERADMIN"));
    }
    public boolean hasAdminRole(){
        return this.userDetails.getAuthorities().stream().anyMatch(authority->authority.getAuthority().equals("ADMIN"));
    }
    public boolean hasUserRole(){
        return this.userDetails.getAuthorities().stream().anyMatch(authority->authority.getAuthority().equals("USER"));
    }

}

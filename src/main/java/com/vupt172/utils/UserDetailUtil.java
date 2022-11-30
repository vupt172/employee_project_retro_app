package com.vupt172.utils;

import com.vupt172.security.service.UserDetailsImpl;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;
@Data
public class UserDetailUtil {
    private  UserDetails userDetails;
    public UserDetailUtil(UserDetails userDetails){
    this.userDetails=userDetails;
    }

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

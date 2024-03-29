package com.vupt172.security.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vupt172.entity.Employee;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
@Data
public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String username;
    private String email;
    private String status;
    @JsonIgnore
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Long id, String username, String email, String password,String status, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.status=status;
    }

    public static UserDetailsImpl build(Employee employee) {
 /*       List<GrantedAuthority> authorities = employee.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());*/
        List<GrantedAuthority> authorities=new ArrayList<>();
        if(employee.getRole()==0)authorities.add(new SimpleGrantedAuthority("ROLE_SUPERADMIN"));
        else if(employee.getRole()==1)authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        else if(employee.getRole()==2)authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return new UserDetailsImpl(employee.getId(), employee.getUsername(), employee.getEmail(), employee.getPassword(),employee.getStatus(), authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }



    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return status.equals("Enable");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }


}

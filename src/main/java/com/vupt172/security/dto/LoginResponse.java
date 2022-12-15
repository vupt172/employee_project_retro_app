package com.vupt172.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;
@Data
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private String refreshToken;
    private String type;
    private Long id;
    private String email;
    private Set<String> roles;
}

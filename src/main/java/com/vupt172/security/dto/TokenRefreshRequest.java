package com.vupt172.security.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
@Data
public class TokenRefreshRequest {
    @NotBlank
    private String refreshToken;
}

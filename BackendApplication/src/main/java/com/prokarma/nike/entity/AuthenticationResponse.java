package com.prokarma.nike.entity;

import lombok.Data;

import java.util.Date;

@Data
public class AuthenticationResponse {
    private String token;
    private Long expTime;
    private String role;
}

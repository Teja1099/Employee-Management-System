package com.prokarma.nike.entity;

import lombok.Data;

@Data
public class AuthenticationRequest {

    private String userName;
    private String password;
}

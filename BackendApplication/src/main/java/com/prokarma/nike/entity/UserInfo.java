package com.prokarma.nike.entity;

import lombok.Data;

@Data
public class UserInfo {

    private String name;
    private Integer empId;
    private String userName;

    private Object roles;
}

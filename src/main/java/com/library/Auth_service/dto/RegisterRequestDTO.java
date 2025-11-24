package com.library.Auth_service.dto;

import lombok.Data;

@Data
public class RegisterRequestDTO {
    private String name;
    private Integer age;
    private String address;
    private String phoneNumber;
    private String username;
    private String password;
    private String role;
}

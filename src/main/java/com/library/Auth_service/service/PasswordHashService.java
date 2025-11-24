package com.library.Auth_service.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordHashService {
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String hashPassword(String plainPassword){
        return passwordEncoder.encode(plainPassword);
    }

    public boolean verifyPassword(String plainPassword , String hashedPassword) {
        return passwordEncoder.matches(plainPassword,hashedPassword);
    }
}

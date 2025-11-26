package com.library.Auth_service.service;

import com.library.Auth_service.dto.LogInRequestDTO;
import com.library.Auth_service.dto.LogInResponseDTO;
import com.library.Auth_service.dto.RegisterRequestDTO;
import com.library.Auth_service.entity.CredentialEntity;
import com.library.Auth_service.entity.UserEntity;
import com.library.Auth_service.enums.Role;
import com.library.Auth_service.repo.CredentialRepository;
import com.library.Auth_service.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CredentialRepository credentialRepository;

    @Autowired
    PasswordHashService passwordHashService;

    @Autowired
    JwtService jwtService;

    public void register(RegisterRequestDTO dto) throws RuntimeException {
        CredentialEntity existingUser = credentialRepository.findByUsername(dto.getUsername());

        if(existingUser != null){

            throw new RuntimeException("User already exists!");
        }


        CredentialEntity newCredentialEntity = new CredentialEntity();


        newCredentialEntity.setUsername(dto.getUsername());

        String roleStr = dto.getRole().trim().toUpperCase();
        Role role = null;

        if (roleStr.equals("USER")) {
            role = Role.USER;
        } else if (roleStr.equals("ADMIN")) {
            role = Role.ADMIN;
        } else {
            throw new RuntimeException("Invalid role: " + dto.getRole());
        }
        newCredentialEntity.setRole(role);
        newCredentialEntity.setPasswordHash(passwordHashService.hashPassword(dto.getPassword()));

        UserEntity newUSer = new UserEntity();
        newUSer.setAddress(dto.getAddress());
        newUSer.setAge(dto.getAge());
        newUSer.setName(dto.getName());
        newUSer.setPhoneNumber(dto.getPhoneNumber());

        userRepository.save(newUSer);
        newCredentialEntity.setUser(newUSer);
        credentialRepository.save(newCredentialEntity);

    }

    public LogInResponseDTO login(LogInRequestDTO dto) {
        CredentialEntity existingUser = credentialRepository.findByUsername(dto.getUsername());

        if(existingUser == null){

            throw new RuntimeException("User is not registered!");
        }
        if(!passwordHashService.verifyPassword(dto.getPassword(), existingUser.getPasswordHash())){
            throw new RuntimeException("Wrong Credential");
        }

        String role = existingUser.getRole().name();
        System.out.println("Role is "+role);
        return new LogInResponseDTO(jwtService.generateToken(dto.getUsername(),existingUser.getRole().name()));
    }
}

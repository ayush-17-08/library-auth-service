package com.library.Auth_service.contoller;

import com.library.Auth_service.dto.LogInRequestDTO;
import com.library.Auth_service.dto.LogInResponseDTO;
import com.library.Auth_service.dto.RegisterRequestDTO;
import com.library.Auth_service.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    AuthService authService;

    @GetMapping("health-check")
    ResponseEntity<?> healthCheck(){
        return ResponseEntity.status(HttpStatus.OK).body("Live");
    }
    @PostMapping("/register")
    ResponseEntity<?> register(@RequestBody RegisterRequestDTO dto){

        try{
            authService.register(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body("User Registered Successfully!");
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/log-in")
    ResponseEntity<?> login(@RequestBody LogInRequestDTO dto){

        try{
            LogInResponseDTO token = authService.login(dto);
            return ResponseEntity.status(HttpStatus.OK).body(token);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}

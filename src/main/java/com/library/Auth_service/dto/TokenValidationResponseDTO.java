package com.library.Auth_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenValidationResponseDTO {
    private boolean valid;
    private String username;
    private String role;
}

package com.genosentinel.authentication.models.dto.authDTO;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String email;
}

package com.example.ingress.service.dto.user;

import lombok.Data;

@Data
public class UserDto {
    private String email;
    private String password;
    private String role;
}

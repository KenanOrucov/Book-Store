package com.example.ingress.service.dto.author;

import lombok.Data;

@Data
public class AuthorRequestDto {
    private String name;
    private Integer age;
    private String email;
}

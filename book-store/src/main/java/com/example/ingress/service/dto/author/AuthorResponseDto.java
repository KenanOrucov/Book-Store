package com.example.ingress.service.dto.author;

import com.example.ingress.service.dto.book.BookResponseDto;
import lombok.Data;

import java.util.List;

@Data
public class AuthorResponseDto {

    private Long id;
    private String name;
    private Integer age;
    private String email;
}

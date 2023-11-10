package com.example.ingress.service.dto.book;

import com.example.ingress.service.dto.author.AuthorResponseDto;
import lombok.Data;

@Data
public class BookResponseDto {
    private Long id;
    private String name;
    private AuthorResponseDto author;
}

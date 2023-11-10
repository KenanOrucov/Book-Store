package com.example.ingress.service.dto.book;

import lombok.Data;

@Data
public class BookRequestDto {
    private String name;
    private Long authorId;
}

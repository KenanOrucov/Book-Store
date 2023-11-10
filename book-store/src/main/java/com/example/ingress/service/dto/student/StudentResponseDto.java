package com.example.ingress.service.dto.student;

import com.example.ingress.service.dto.book.BookResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponseDto {
    private Long id;
    private String name;
    private Integer age;
    private String email;
    private List<BookResponseDto> books;
}

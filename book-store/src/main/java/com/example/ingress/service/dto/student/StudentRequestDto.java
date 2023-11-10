package com.example.ingress.service.dto.student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentRequestDto {
    private String name;
    private Integer age;
    private String email;
    private List<Long> books;
}

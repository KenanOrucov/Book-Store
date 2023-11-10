package com.example.ingress.mapper;

import com.example.ingress.domain.AuthorEntity;
import com.example.ingress.domain.BookEntity;
import com.example.ingress.domain.StudentEntity;
import com.example.ingress.domain.UserEntity;
import com.example.ingress.service.dto.user.UserDto;
import com.example.ingress.service.dto.author.AuthorRequestDto;
import com.example.ingress.service.dto.book.BookResponseDto;
import com.example.ingress.service.dto.student.StudentRequestDto;
import com.example.ingress.service.dto.student.StudentResponseDto;

public interface ManualIngressMapping {
    BookResponseDto convertToBookResponse(BookEntity bookEntity);
    StudentResponseDto convertToStudentResponse(StudentEntity studentEntity);
    StudentEntity convertToStudent(StudentRequestDto studentRequestDto);
    StudentEntity convertToStudent(UserDto userDto);
    AuthorEntity convertToAuthor(AuthorRequestDto authorRequestDto);
    AuthorEntity convertToAuthor(UserDto userDto);

    UserDto convertToUser(StudentRequestDto studentRequestDto);

    UserEntity convertToUser(UserDto userDto);
}

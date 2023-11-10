package com.example.ingress.mapper.impl;

import com.example.ingress.domain.*;
import com.example.ingress.mapper.ManualIngressMapping;
import com.example.ingress.service.dto.user.UserDto;
import com.example.ingress.service.dto.author.AuthorRequestDto;
import com.example.ingress.service.dto.author.AuthorResponseDto;
import com.example.ingress.service.dto.book.BookResponseDto;
import com.example.ingress.service.dto.student.StudentRequestDto;
import com.example.ingress.service.dto.student.StudentResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class ManualIngressMappingImpl implements ManualIngressMapping {
    private final ObjectMapper objectMapper;

    @Override
    public BookResponseDto convertToBookResponse(BookEntity bookEntity) {
        BookResponseDto bookResponseDto = objectMapper.convertValue(bookEntity, BookResponseDto.class);
        bookResponseDto.setAuthor(objectMapper.convertValue(bookEntity.getAuthorEntity(), AuthorResponseDto.class));
        return bookResponseDto;
    }

    @Override
    public StudentResponseDto convertToStudentResponse(StudentEntity studentEntity) {
        StudentResponseDto studentResponseDto = objectMapper.convertValue(studentEntity, StudentResponseDto.class);
        studentResponseDto.setBooks(studentEntity
                .getBookEntities()
                .stream()
                .map(book -> convertToBookResponse(book))
                .collect(Collectors.toList()));
        return studentResponseDto;
    }

    @Override
    public StudentEntity convertToStudent(StudentRequestDto studentRequestDto) {
        StudentEntity studentEntity = StudentEntity.builder()
                .name(studentRequestDto.getName())
                .age(studentRequestDto.getAge())
                .email(studentRequestDto.getEmail())
                .build();
        return studentEntity;
    }

    @Override
    public StudentEntity convertToStudent(UserDto userDto) {
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setEmail(userDto.getEmail());
        studentEntity.setPassword(userDto.getPassword());
        return studentEntity;
    }

    @Override
    public AuthorEntity convertToAuthor(AuthorRequestDto authorRequestDto) {
        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setName(authorRequestDto.getName());
        authorEntity.setAge(authorRequestDto.getAge());
        authorEntity.setEmail(authorRequestDto.getEmail());
        return authorEntity;
    }

    @Override
    public AuthorEntity convertToAuthor(UserDto userDto) {
        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setEmail(userDto.getEmail());
        authorEntity.setPassword(userDto.getPassword());
        return authorEntity;
    }

    @Override
    public UserDto convertToUser(StudentRequestDto studentRequestDto) {
        UserDto userDto = new UserDto();
        userDto.setEmail(studentRequestDto.getEmail());
        userDto.setRole(UserAuthority.STUDENT);
        return userDto;
    }

    @Override
    public UserEntity convertToUser(UserDto userDto) {
        UserEntity user = new UserEntity();
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        return user;
    }
}

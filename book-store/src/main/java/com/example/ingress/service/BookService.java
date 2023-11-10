package com.example.ingress.service;

import com.example.ingress.domain.AuthorEntity;
import com.example.ingress.domain.BookEntity;
import com.example.ingress.domain.BookStatus;
import com.example.ingress.exception.AuthorException;
import com.example.ingress.exception.BookException;
import com.example.ingress.exception.StudentException;
import com.example.ingress.mapper.ManualIngressMapping;
import com.example.ingress.repository.AuthorRepository;
import com.example.ingress.repository.BookRepository;
import com.example.ingress.service.dto.book.BookRequestDto;
import com.example.ingress.service.dto.book.BookResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final ObjectMapper objectMapper;
    private final ManualIngressMapping manualIngressMapping;

    @Transactional
    public List<BookResponseDto> getAllBooks(){
        return bookRepository.findAll().stream().map(book -> manualIngressMapping.convertToBookResponse(book)).collect(Collectors.toList());
    }


    public BookResponseDto getBookById(Long id){
        return bookRepository.findById(id)
                .map(book -> manualIngressMapping.convertToBookResponse(book))
                .orElseThrow(() -> new StudentException(id));
    }

    @Transactional
    public BookResponseDto createBook(BookRequestDto bookRequestDto){
        BookEntity bookEntity = objectMapper.convertValue(bookRequestDto, BookEntity.class);

        AuthorEntity authorEntity = authorRepository.findById(bookRequestDto.getAuthorId())
                .orElseThrow(() -> new AuthorException(bookRequestDto.getAuthorId()));

        bookEntity.setStatus(BookStatus.CREATED);
        bookEntity.setAuthorEntity(authorEntity);
        return manualIngressMapping.convertToBookResponse(bookRepository.save(bookEntity));
    }

    @Transactional
    public BookResponseDto updateBook(Long id, BookRequestDto bookRequestDto){
        BookEntity bookEntity = bookRepository.findById(id)
                .orElseThrow(() -> new BookException(id));

        AuthorEntity authorEntity = authorRepository.findById(bookRequestDto.getAuthorId())
                .orElseThrow(() -> new AuthorException(id));

        bookEntity.setName(bookRequestDto.getName());
        bookEntity.setAuthorEntity(authorEntity);
        return manualIngressMapping.convertToBookResponse(bookEntity);
    }

    public void deleteBook(Long id){
        BookEntity bookEntity = bookRepository.findById(id)
                .orElseThrow(() -> new BookException(id));
        bookEntity.setStatus(BookStatus.DELETED);
    }


}

package com.example.ingress.service;

import com.example.ingress.domain.AuthorEntity;
import com.example.ingress.domain.BookEntity;
import com.example.ingress.domain.BookStatus;
import com.example.ingress.exception.AuthorCanNotDeleteBook;
import com.example.ingress.exception.AuthorException;
import com.example.ingress.exception.BookException;
import com.example.ingress.exception.StudentException;
import com.example.ingress.mapper.ManualIngressMapping;
import com.example.ingress.repository.AuthorRepository;
import com.example.ingress.repository.BookRepository;
import com.example.ingress.service.dto.user.UserDto;
import com.example.ingress.service.dto.author.AuthorRequestDto;
import com.example.ingress.service.dto.author.AuthorResponseDto;
import com.example.ingress.service.dto.book.BookRequestDto;
import com.example.ingress.service.dto.book.BookResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final ObjectMapper objectMapper;
//    private final MailService mailService;
    private final ManualIngressMapping manualIngressMapping;

    @Transactional
    public List<AuthorResponseDto> getAllAuthors(){
        return authorRepository.findAll().stream().map(student -> objectMapper.convertValue(student, AuthorResponseDto.class)).collect(Collectors.toList());
    }

    @Transactional
    public AuthorResponseDto getAuthorById(Long id){
        return authorRepository.findById(id)
                .map(author -> objectMapper.convertValue(author, AuthorResponseDto.class))
                .orElseThrow(() -> new StudentException(id));
    }

    @Transactional
    public BookResponseDto addBook(BookRequestDto bookRequestDto){
        BookEntity bookEntity = objectMapper.convertValue(bookRequestDto, BookEntity.class);

        AuthorEntity authorEntity = authorRepository.findById(bookRequestDto.getAuthorId())
                .orElseThrow(() -> new AuthorException(bookRequestDto.getAuthorId()));

        bookEntity.setStatus(BookStatus.CREATED);
        bookEntity.setAuthorEntity(authorEntity);
        authorEntity.notifyObserver(bookEntity, authorEntity /* , mailService */);
//        notifyObserver(bookEntity.getName());
        return manualIngressMapping.convertToBookResponse(bookRepository.save(bookEntity));
    }

    public AuthorResponseDto createAuthor(AuthorRequestDto authorRequestDto){
        AuthorEntity authorEntity = manualIngressMapping.convertToAuthor(authorRequestDto);
        return objectMapper.convertValue(authorRepository.save(authorEntity), AuthorResponseDto.class);
    }

    public AuthorResponseDto createAuthor(UserDto userDto){
        AuthorEntity authorEntity = manualIngressMapping.convertToAuthor(userDto);
        return objectMapper.convertValue(authorRepository.save(authorEntity), AuthorResponseDto.class);
    }

    @Transactional
    public AuthorResponseDto updateAuthor(Long id, AuthorRequestDto studentRequestDto){
        AuthorEntity authorEntity = authorRepository.findById(id)
                .orElseThrow(() -> new AuthorException(id));
        authorEntity.setAge(studentRequestDto.getAge());
        authorEntity.setName(studentRequestDto.getName());
        authorEntity.setEmail(studentRequestDto.getEmail());
        return objectMapper.convertValue(authorRepository.save(authorEntity), AuthorResponseDto.class);
    }

    public void deleteAuthor(Long id){
        AuthorEntity authorEntity = authorRepository.findById(id)
                .orElseThrow(() -> new AuthorException(id));
        authorRepository.delete(authorEntity);
    }

    @Transactional
    public void deleteBook(Long authorId, Long bookId){
        BookEntity bookEntity = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookException(bookId));

        if (bookEntity.getAuthorEntity().getId() != authorId){
            throw new AuthorCanNotDeleteBook(authorId, bookId);
        }
        bookEntity.setStatus(BookStatus.DELETED);
    }

}















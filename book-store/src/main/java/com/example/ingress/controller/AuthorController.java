package com.example.ingress.controller;

import com.example.ingress.service.AuthorService;
import com.example.ingress.service.dto.author.AuthorRequestDto;
import com.example.ingress.service.dto.author.AuthorResponseDto;
import com.example.ingress.service.dto.book.BookRequestDto;
import com.example.ingress.service.dto.book.BookResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping
    public ResponseEntity<List<AuthorResponseDto>> getAllStudents(){
        return ResponseEntity.ok(authorService.getAllAuthors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponseDto> getStudentById(@PathVariable Long id){
        return ResponseEntity.ok(authorService.getAuthorById(id));
    }

    @PostMapping
    public ResponseEntity<AuthorResponseDto> addStudent(@RequestBody AuthorRequestDto authorRequestDto){
        System.out.println(authorRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authorService.createAuthor(authorRequestDto));
    }

    @PostMapping("/add-book")
    public ResponseEntity<BookResponseDto> addBook(@RequestBody BookRequestDto bookRequestDto){
        return ResponseEntity.ok(authorService.addBook(bookRequestDto));
    }

    @PutMapping("/{id}")
    public AuthorResponseDto updateStudent(@PathVariable Long id, @RequestBody AuthorRequestDto authorRequestDto){
        return authorService.updateAuthor(id,authorRequestDto);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id){
        authorService.deleteAuthor(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{authorId}/")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long authorId, @RequestParam Long bookId){
        authorService.deleteBook(authorId, bookId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

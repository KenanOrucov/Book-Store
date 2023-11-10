package com.example.ingress.controller;

import com.example.ingress.service.StudentService;
import com.example.ingress.service.dto.book.BookResponseDto;
import com.example.ingress.service.dto.student.StudentRequestDto;
import com.example.ingress.service.dto.student.StudentResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;


    @GetMapping
    public ResponseEntity<List<StudentResponseDto>> getAllStudents(){
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDto> getStudentById(@PathVariable Long id){
        return ResponseEntity.ok(studentService.getStudentById(id));
    }
    @GetMapping("/book-id/{id}")
    public ResponseEntity<List<StudentResponseDto>> getStudentsWithBookId(@PathVariable Long id){
        return ResponseEntity.ok(studentService.getStudentsWithBookId(id));
    }

    @GetMapping("/reading-books/{id}")
    public ResponseEntity<List<BookResponseDto>> getReadingBooks(@PathVariable Long id){
        return ResponseEntity.ok(studentService.getBooks(id));
    }

    @PostMapping("/{id}")
    public ResponseEntity<String> followAuthor(@PathVariable Long id, @RequestParam Long authorId){
        studentService.followAuthor(authorId, id);
        return ResponseEntity.ok("You are following");
    }

    @PostMapping
    public ResponseEntity<StudentResponseDto> addStudent(@RequestBody StudentRequestDto student){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(studentService.createStudent(student));
    }

    @PutMapping("/{id}")
    public StudentResponseDto updateStudent(@PathVariable Long id, @RequestBody StudentRequestDto studentRequestDto){
        return studentService.updateStudent(id,studentRequestDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id){
        studentService.deleteStudent(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}

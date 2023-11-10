package com.example.ingress.service;

import com.example.ingress.domain.*;
import com.example.ingress.exception.AuthorException;
import com.example.ingress.exception.BookException;
import com.example.ingress.exception.StudentException;
import com.example.ingress.mapper.ManualIngressMapping;
import com.example.ingress.repository.AuthorRepository;
import com.example.ingress.repository.AuthorityRepository;
import com.example.ingress.repository.BookRepository;
import com.example.ingress.repository.StudentRepository;
import com.example.ingress.service.dto.user.UserDto;
import com.example.ingress.service.dto.book.BookResponseDto;
import com.example.ingress.service.dto.student.StudentRequestDto;
import com.example.ingress.service.dto.student.StudentResponseDto;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService{

    private final StudentRepository studentRepository;
    private final BookRepository bookRepository;
//    private final UserService userService;
    private final AuthorityRepository authorityRepository;
    private final AuthorRepository authorRepository;
//    private final PasswordEncoder passwordEncoder;
    private final ManualIngressMapping manualIngressMapping;
    private static  int observerIDTrack = 0;
    private int observerID;
    private AuthorEntity author;


    @Transactional
    public List<StudentResponseDto> getAllStudents(){
        return studentRepository.findAll()
                .stream()
                .map(student -> manualIngressMapping.convertToStudentResponse(student))
                .collect(Collectors.toList());
    }

    @Transactional
    public StudentResponseDto getStudentById(Long id){
        return studentRepository.findById(id)
                .map(student -> manualIngressMapping.convertToStudentResponse(student))
                .orElseThrow(() -> new StudentException(id));
    }

    @Transactional
    public List<StudentResponseDto> getStudentsWithBookId(Long id){
        return studentRepository.getStudentsWithBookId(id)
                .stream()
                .map(student -> manualIngressMapping.convertToStudentResponse(student))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<BookResponseDto> getBooks(Long id){
        StudentEntity studentEntity = studentRepository.findById(id)
                .orElseThrow(() -> new StudentException(id));

        return studentEntity.getBookEntities()
                .stream()
                .map(book -> manualIngressMapping.convertToBookResponse(book))
                .collect(Collectors.toList());
    }

    @Transactional
    public StudentResponseDto createStudent(StudentRequestDto studentRequestDto){
        StudentEntity studentEntity = manualIngressMapping.convertToStudent(studentRequestDto);
//        UserDto userDto = manualIngressMapping.convertToUser(studentRequestDto);

        studentEntity.setAuthorityEntities(createAuthorities(UserAuthority.STUDENT));
//        studentEntity.setPassword(studentRequestDto.getPassword());
//        userDto.setPassword(studentEntity.getPassword());
//        userService.saveUser(userDto);
        studentEntity.setBookEntities(addBooksToStudent(studentRequestDto));
        return manualIngressMapping.convertToStudentResponse(studentRepository.save(studentEntity));
    }

    @Transactional
    public StudentEntity createStudent(UserDto studentRequestDto){
        StudentEntity studentEntity = manualIngressMapping.convertToStudent(studentRequestDto);
        studentEntity.setAuthorityEntities(createAuthorities(UserAuthority.STUDENT));
        return studentRepository.save(studentEntity);
    }

    @Transactional
    public StudentResponseDto updateStudent(Long id, StudentRequestDto studentRequestDto){
        StudentEntity myStudentEntity = studentRepository.findById(id)
                .orElseThrow(() -> new StudentException(id));

        myStudentEntity.setAge(studentRequestDto.getAge());
        myStudentEntity.setName(studentRequestDto.getName());
        myStudentEntity.setEmail(studentRequestDto.getEmail());
//        myStudentEntity.setPassword(studentRequestDto.getPassword());
        myStudentEntity.setAuthorityEntities(createAuthorities(UserAuthority.STUDENT));
        myStudentEntity.setBookEntities(addBooksToStudent(studentRequestDto));
        return manualIngressMapping.convertToStudentResponse(studentRepository.save(myStudentEntity));
    }

    public void deleteStudent(Long id){
        StudentEntity studentEntity = studentRepository.findById(id)
                .orElseThrow(() -> new StudentException(id));
        studentRepository.delete(studentEntity);
    }

    private List<BookEntity> addBooksToStudent(StudentRequestDto studentRequestDto){
        List<BookEntity> bookEntities = new ArrayList<>();
        if (studentRequestDto.getBooks() != null) {
            for (Long bookId : studentRequestDto.getBooks()) {
                BookEntity bookEntity = bookRepository.findById(bookId)
                        .orElseThrow(() -> new BookException(bookId));

                bookEntities.add(bookEntity);
            }
        }
        return bookEntities;
    }

    private Set<AuthorityEntity> createAuthorities(String authority) {
        Set<AuthorityEntity> authorities = new HashSet<>();
        AuthorityEntity authorityEntity = authorityRepository
                .findAll()
                .stream()
                .filter(t -> t.getAuthority().equals(authority))
                .findFirst()
                .orElseGet(() -> AuthorityEntity.builder().authority(authority).build());
         authorities.add(authorityEntity);
         return authorities;
    }

    @Transactional
    public void followAuthor(Long authorId, Long studentId){
        AuthorEntity authorEntity = authorRepository.findById(authorId)
                .orElseThrow(() -> new AuthorException(authorId));

        StudentEntity studentEntity = studentRepository.findById(authorId)
                .orElseThrow(() -> new StudentException(studentId));

        this.author = authorEntity;
        this.observerID =++observerIDTrack;
        System.out.println("New observer " + this.observerID);
        author.register(studentEntity);
    }
}

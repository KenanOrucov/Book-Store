package com.example.ingress.exception;

import com.example.ingress.exception.model.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class GeneralExceptionHandler {

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(BookException.class)
    public ResponseEntity<ErrorResponse> bookExceptionHandler(BookException ex){
        return ResponseEntity.status(NOT_FOUND).body(new ErrorResponse(NOT_FOUND.value(), ex.getMessage()));
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(StudentException.class)
    public ResponseEntity<ErrorResponse> studentExceptionHandler(StudentException ex){
        return ResponseEntity.status(NOT_FOUND).body(new ErrorResponse(NOT_FOUND.value(), ex.getMessage()));
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(AuthorException.class)
    public ResponseEntity<ErrorResponse> authorExceptionHandler(AuthorException ex){
        return ResponseEntity.status(NOT_FOUND).body(new ErrorResponse(NOT_FOUND.value(), ex.getMessage()));
    }

    @ResponseStatus(NOT_ACCEPTABLE)
    @ExceptionHandler(AuthorCanNotDeleteBook.class)
    public ResponseEntity<ErrorResponse> authorCanNotDeleteBookHandler(AuthorCanNotDeleteBook ex){
        return ResponseEntity.status(NOT_FOUND).body(new ErrorResponse(NOT_ACCEPTABLE.value(), ex.getMessage()));
    }

    @ResponseStatus(NOT_ACCEPTABLE)
    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ErrorResponse> sqlException(SQLException ex){
        return ResponseEntity.status(NOT_FOUND).body(new ErrorResponse(NOT_ACCEPTABLE.value(), ex.getMessage()));
    }


}

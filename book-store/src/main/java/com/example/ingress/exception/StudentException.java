package com.example.ingress.exception;

public class StudentException extends RuntimeException{

    public static final String MESSAGE = "Student %s does not exist";

    public StudentException(Long studentId) {
        super(String.format(MESSAGE, studentId));
    }
}

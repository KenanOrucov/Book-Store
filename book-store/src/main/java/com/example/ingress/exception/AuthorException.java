package com.example.ingress.exception;

public class AuthorException extends RuntimeException{

    public static final String MESSAGE = "Author %s does not exist";

    public AuthorException(Long authorId) {
        super(String.format(MESSAGE, authorId));
    }
}

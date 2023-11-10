package com.example.ingress.exception;

public class AuthorCanNotDeleteBook extends RuntimeException{

    public static final String MESSAGE = "Author %s can not delete this book %s";

    public AuthorCanNotDeleteBook(Long authorId, Long bookId) {
        super(String.format(MESSAGE, authorId, bookId));
    }
}

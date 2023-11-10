package com.example.ingress.observer;

import com.example.ingress.domain.AuthorEntity;
import com.example.ingress.domain.BookEntity;
import com.example.ingress.domain.StudentEntity;
//import com.example.ingress.service.MailService;

public interface Subject {
    void register(StudentEntity observer);
    void unregister(StudentEntity observer);
    void notifyObserver(BookEntity bookEntity, AuthorEntity authorEntity /* , MailService mailService */);
}

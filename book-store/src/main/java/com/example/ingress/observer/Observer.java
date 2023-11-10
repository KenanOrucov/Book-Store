package com.example.ingress.observer;

import com.example.ingress.domain.AuthorEntity;
import com.example.ingress.domain.BookEntity;
//import com.example.ingress.service.MailService;

public interface Observer {

    public void update(BookEntity bookEntity, AuthorEntity authorEntity /* , MailService mailService */);
}

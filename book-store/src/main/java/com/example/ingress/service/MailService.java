//package com.example.ingress.service;
//
//import com.example.ingress.domain.BookEntity;
//import com.example.ingress.domain.StudentEntity;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Service;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class MailService {
//
//    private final JavaMailSender javaMailSender;
//
//    private final String sender = "kenannorucov19@gmail.com";
//
//    public void sendMail(BookEntity bookEntity, StudentEntity studentEntity){
//        try {
//            SimpleMailMessage message =new SimpleMailMessage();
//            message.setFrom(sender);
//            message.setTo(studentEntity.getEmail());
//            message.setSubject("Author publishes new book");
//            message.setText("Book name is: " + bookEntity.getName() + "\nAuthor is: " + bookEntity.getAuthorEntity().getName());
//            javaMailSender.send(message);
//            log.trace("You mail: " + "Book name is: " + bookEntity.getName() + "\nAuthor is: " + bookEntity.getAuthorEntity().getName());
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//}

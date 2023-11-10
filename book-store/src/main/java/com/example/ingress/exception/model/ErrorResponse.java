package com.example.ingress.exception.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@ToString
@Getter
@AllArgsConstructor
public class ErrorResponse {
    private Integer httpStatus;
    private final String message;

}
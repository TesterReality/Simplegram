package com.simplegram.exceptions.controller;

import com.simplegram.exceptions.LoginAlreadyTakenException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Locale;

@RequiredArgsConstructor
@ControllerAdvice
public class AlreadyExistsException {

    private final MessageSource messageSource;

    @ExceptionHandler(value = {LoginAlreadyTakenException.class})
    protected ResponseEntity<Object> loginAlreadyTakenException() {

        return ResponseEntity
                .badRequest()//402 нужно?
                .body(messageSource.getMessage("exception.loginAlreadyTaken",
                        null, Locale.getDefault()));
    }
}

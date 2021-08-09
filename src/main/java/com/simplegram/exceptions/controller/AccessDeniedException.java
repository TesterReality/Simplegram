package com.simplegram.exceptions.controller;

import com.simplegram.exceptions.UserUnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Locale;

@RequiredArgsConstructor
@ControllerAdvice
public class AccessDeniedException {

    private final MessageSource messageSource;

    @ExceptionHandler(value = {UserUnauthorizedException.class})
    protected ResponseEntity<Object> userUnauthorizedException() {

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(messageSource.getMessage("exception.unauthorized",
                        null, Locale.ENGLISH));
    }
}

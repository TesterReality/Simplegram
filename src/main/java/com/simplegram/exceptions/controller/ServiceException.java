package com.simplegram.exceptions.controller;

import com.simplegram.exceptions.ImageGenerationException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Locale;

@RequiredArgsConstructor
@ControllerAdvice
public class ServiceException {
    private final MessageSource messageSource;

    @ExceptionHandler(value = {ImageGenerationException.class})
    protected ResponseEntity<Object> imageGenerationException() {

        return ResponseEntity
                .badRequest()
                .body(messageSource.getMessage("user.answer.errorCreateAccount",
                        null, Locale.getDefault()));
    }
}

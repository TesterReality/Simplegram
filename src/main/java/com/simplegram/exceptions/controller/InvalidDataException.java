package com.simplegram.exceptions.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.simplegram.exceptions.jsonAnswer.JsonAnswerInvalidData;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Locale;

@RequiredArgsConstructor
@ControllerAdvice
public class InvalidDataException {
    private final MessageSource messageSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> test(MethodArgumentNotValidException e) {
        HashMap<String, String> validation = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        JsonAnswerInvalidData jsonAnswer = new JsonAnswerInvalidData();
        String result = null;

        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            validation.put(fieldError.getField(), messageSource.getMessage("error.invalid-" + fieldError.getField(),
                    null, Locale.ENGLISH));
        }
        jsonAnswer.setStatus(HttpStatus.BAD_REQUEST.value());
        jsonAnswer.setMessage(messageSource.getMessage("error.validation",
                null, Locale.ENGLISH));
        jsonAnswer.setValidation(validation);
        try {
            result = mapper.writeValueAsString(jsonAnswer);
        } catch (JsonProcessingException jsonProcessingException) {
            jsonProcessingException.printStackTrace();
        }
        return ResponseEntity
                .badRequest()
                .body(result);
    }
}

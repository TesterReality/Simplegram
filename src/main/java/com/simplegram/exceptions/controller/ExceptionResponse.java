package com.simplegram.exceptions.controller;

import com.simplegram.dto.JsonAnswerInvalidData;
import com.simplegram.exceptions.AccessDeniedException;
import com.simplegram.exceptions.AlreadyExistsException;
import com.simplegram.exceptions.ServiceException;
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
public class ExceptionResponse {
    private final MessageSource messageSource;

    @ExceptionHandler(value = {AlreadyExistsException.class})
    protected ResponseEntity loginAlreadyTakenException(AlreadyExistsException e) {

        return ResponseEntity
                .badRequest()
                .body(messageSource.getMessage(e.getMessage(),
                        null, Locale.getDefault()));
    }

    @ExceptionHandler(value = {AccessDeniedException.class})
    protected ResponseEntity<Object> userUnauthorizedException() {

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(messageSource.getMessage("exception.unauthorized",
                        null, Locale.getDefault()));
    }

    @ExceptionHandler(value = {ServiceException.class})
    protected ResponseEntity<Object> imageGenerationException(ServiceException e) {

        return ResponseEntity
                .badRequest()
                .body(messageSource.getMessage(e.getMessage(),
                        null, Locale.getDefault()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> invalidDataAnswer(MethodArgumentNotValidException e) {
        HashMap<String, String> validation = new HashMap<>();
        JsonAnswerInvalidData jsonAnswer = new JsonAnswerInvalidData();

        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            String code = "error.invalid-" + fieldError.getField();
            String message = messageSource.getMessage(code, null, Locale.getDefault());
            validation.put(fieldError.getField(), message);
        }

        String message = messageSource.getMessage("error.validation", null, Locale.getDefault());

        jsonAnswer.setStatus(HttpStatus.BAD_REQUEST.value());
        jsonAnswer.setMessage(message);
        jsonAnswer.setValidation(validation);

        return ResponseEntity
                .badRequest()
                .body(jsonAnswer);
    }


}

package com.simplegram.advice;

import com.simplegram.exceptions.BadRequestException;
import com.simplegram.exceptions.ServiceException;
import com.simplegram.exceptions.UnauthorizedException;
import com.simplegram.payload.response.ExceptionResponse;
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
public class ExceptionAdvice {
    private final MessageSource messageSource;

    @ExceptionHandler({BadRequestException.class, UnauthorizedException.class})
    protected ResponseEntity<String> errorAnswer(ServiceException e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(messageSource.getMessage(e.getMessage(),
                        null, Locale.getDefault()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ExceptionResponse> invalidDataAnswer(MethodArgumentNotValidException e) {
        HashMap<String, String> validation = new HashMap<>();
        ExceptionResponse jsonAnswer = new ExceptionResponse();
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

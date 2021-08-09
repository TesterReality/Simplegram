package com.simplegram.exceptions.controller;

import com.simplegram.exceptions.ApiError;
import com.simplegram.exceptions.LoginAlreadyTakenException;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.json.JSONArray;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.*;
import org.json.JSONException;
import org.json.JSONObject;
@RequiredArgsConstructor
@ControllerAdvice
public class InvalidDataException {
    private final MessageSource messageSource;

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> test(ConstraintViolationException e) {

        List<String> details = new ArrayList<String>();
        details.add(e.getMessage());

        ApiError err = new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST,
                "Constraint Violations" ,
                details);

        JSONObject info = new JSONObject();
        JSONArray ja = new JSONArray();
        ja.put("Validation");
        JSONObject jo = new JSONObject();

        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {

            jo = new JSONObject();
            info.put(violation.getPropertyPath().toString(), violation.getMessage());

            jo.put(violation.getPropertyPath().toString(), violation.getInvalidValue().toString());
            jo.put("message", violation.getMessage());
            ja.put(jo);
        }
        ResponseEntity test = ResponseEntity
                .badRequest()//402 нужно?
                .body(ja.toString());
        return test;
    }
}

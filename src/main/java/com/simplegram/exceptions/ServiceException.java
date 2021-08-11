package com.simplegram.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ServiceException extends RuntimeException {
    private final String message;
    private final HttpStatus status;

    public ServiceException(String message, HttpStatus status) {
        this.message=message;
        this.status=status;
    }
}

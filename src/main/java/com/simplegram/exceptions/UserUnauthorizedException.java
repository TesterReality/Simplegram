package com.simplegram.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UserUnauthorizedException extends RuntimeException{
    public UserUnauthorizedException(String message) {
        super(message);
    }
}

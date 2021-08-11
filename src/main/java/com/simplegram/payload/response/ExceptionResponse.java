package com.simplegram.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ExceptionResponse {
    private int status;
    private String message;
    private Map<String, String> validation;
}

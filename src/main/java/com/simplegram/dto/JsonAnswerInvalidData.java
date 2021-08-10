package com.simplegram.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class JsonAnswerInvalidData {
    private int status;
    private String message;
    private Map<String, String> validation;
}
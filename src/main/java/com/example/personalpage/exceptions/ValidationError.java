package com.example.personalpage.exceptions;

import java.util.HashMap;
import java.util.Map;

public class ValidationError {
    private final Map<String, String> fieldErrors = new HashMap<>();

    public Map<String, String> getFieldErrors() {
        return fieldErrors;
    }

    public void addFieldError(String field, String message) {
        fieldErrors.put(field, message);
    }
}

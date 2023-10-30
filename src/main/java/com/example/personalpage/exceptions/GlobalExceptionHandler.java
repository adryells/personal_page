package com.example.personalpage.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> handleValidationException(MethodArgumentNotValidException ex) {
        ValidationError validationError = new ValidationError();
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            validationError.addFieldError(fieldError.getField(), fieldError.getDefaultMessage());
        });
        return new ResponseEntity<>(validationError, HttpStatus.BAD_REQUEST);
    }
}

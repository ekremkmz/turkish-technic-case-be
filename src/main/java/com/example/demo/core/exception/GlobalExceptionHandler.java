package com.example.demo.core.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({DataIntegrityViolationException.class})
    ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException e) {
        return ResponseEntity.internalServerError()
                .body(new ErrorResponse("Action suspended due to the violation of data integrity."));
    }

    private record ErrorResponse(String message) {
    }
}

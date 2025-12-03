package com.example.demo.core.exception;

import com.example.demo.controller.response.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        String message = ex.getBindingResult().getFieldErrors().stream().
                map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(" "));

        return new ResponseEntity<>(new ErrorResponse(message), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({DuplicateKeyException.class})
    ResponseEntity<ErrorResponse> handleDuplicateKeyException(DuplicateKeyException e) {
        return ResponseEntity.internalServerError()
                .body(new ErrorResponse("Action suspended due to the duplication of unique key."));
    }

    @ExceptionHandler({ConstraintViolationException.class})
    ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException e) {
        return ResponseEntity.internalServerError()
                .body(new ErrorResponse("Action suspended due to the violation of a constraint."));
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    ResponseEntity<ErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException e) {
        return ResponseEntity.internalServerError()
                .body(new ErrorResponse("Action suspended due to the violation of data integrity."));
    }

    @ExceptionHandler({EntityNotExistException.class})
    ResponseEntity<ErrorResponse> handleEntityNotExistException(EntityNotExistException e) {
        return ResponseEntity.internalServerError()
                .body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler({SameLocationException.class})
    ResponseEntity<ErrorResponse> handleSameLocationException(SameLocationException e) {
        return ResponseEntity.internalServerError()
                .body(new ErrorResponse(e.getMessage()));
    }
}

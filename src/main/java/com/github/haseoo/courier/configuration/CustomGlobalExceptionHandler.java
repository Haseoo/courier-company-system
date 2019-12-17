package com.github.haseoo.courier.configuration;

import com.github.haseoo.courier.exceptions.BusinessLogicException;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BusinessLogicException.class)
    public ResponseEntity<ErrorResponse> customHandleNotFound(Exception ex) {
        ErrorResponse errors = new ErrorResponse(LocalDateTime.now(), ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);

    }

    @Value
    private static class ErrorResponse {
        private LocalDateTime timestamp;
        private String message;
    }
}
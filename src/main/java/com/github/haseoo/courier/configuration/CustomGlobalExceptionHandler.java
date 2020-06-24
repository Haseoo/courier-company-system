package com.github.haseoo.courier.configuration;

import com.github.haseoo.courier.exceptions.AuthException;
import com.github.haseoo.courier.exceptions.BusinessLogicException;
import com.github.haseoo.courier.exceptions.ResourceNotFoundException;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class CustomGlobalExceptionHandler {

    @ExceptionHandler(BusinessLogicException.class)
    public ResponseEntity<ErrorResponse> businessLogicExceptionHandler(BusinessLogicException ex) {
        ErrorResponse errors = new ErrorResponse(LocalDateTime.now(), ex.getMessage());
        log.error(ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex) {
        ErrorResponse errors = new ErrorResponse(LocalDateTime.now(), ex.getMessage());
        log.error(ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ErrorResponse> authExceptionHandler(AuthException ex) {
        ErrorResponse errors = new ErrorResponse(LocalDateTime.now(), ex.getMessage());
        log.error(ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> badCredentialsExceptionHandler(BadCredentialsException ex) {
        ErrorResponse errors = new ErrorResponse(LocalDateTime.now(), ex.getMessage());
        log.error(ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> radiationException(MethodArgumentNotValidException ex) {
        String errorMessage = getFieldErrorMessages(ex);
        return new ResponseEntity<>(new ErrorResponse(LocalDateTime.now(),
                errorMessage),
                HttpStatus.BAD_REQUEST);
    }

    private String getFieldErrorMessages(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(". ", "", "."));
    }

    @Value
    private static class ErrorResponse {
        private LocalDateTime timestamp;
        private String message;
    }
}

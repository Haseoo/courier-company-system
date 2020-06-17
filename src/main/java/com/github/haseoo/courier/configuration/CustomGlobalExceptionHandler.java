package com.github.haseoo.courier.configuration;

import com.github.haseoo.courier.exceptions.AuthException;
import com.github.haseoo.courier.exceptions.BusinessLogicException;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import java.time.LocalDateTime;

@ControllerAdvice
@Slf4j
public class CustomGlobalExceptionHandler extends DefaultHandlerExceptionResolver {

    @ExceptionHandler(BusinessLogicException.class)
    public ResponseEntity<ErrorResponse> businessLogicExceptionHandler(BusinessLogicException ex) {
        ErrorResponse errors = new ErrorResponse(LocalDateTime.now(), ex.getMessage());
        log.error(ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
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

    @Value
    private static class ErrorResponse {
        private LocalDateTime timestamp;
        private String message;
    }
}

package com.cryptovaultdoo.cryptovault.api.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.Timestamp;
import java.time.Instant;

import jakarta.validation.ValidationException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<CustomException> handleRuntimeException(RuntimeException exception) {
        CustomException customException = new CustomException(
                500,
                exception.getMessage(),
                Timestamp.from(Instant.now())
        );

        log.error("Internal server exception occurred {}", exception.getMessage());
        return new ResponseEntity<>(customException, HttpStatusCode.valueOf(500));
    }

    @ExceptionHandler(ValidationException.class)
    protected ResponseEntity<CustomException> handleServiceException(ValidationException exception) {
        CustomException customException = new CustomException(
                400,
                exception.getMessage(),
                Timestamp.from(Instant.now())
        );

        log.error("Validation exception occurred {}", exception.getMessage());
        return new ResponseEntity<>(customException, HttpStatusCode.valueOf(400));
    }


}

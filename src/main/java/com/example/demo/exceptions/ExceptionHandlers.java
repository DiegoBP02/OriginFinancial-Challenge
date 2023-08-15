package com.example.demo.exceptions;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionHandlers {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardError> exception(Exception e, HttpServletRequest request) {
        String error = "Server error";
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        StandardError err = new StandardError(Instant.now(), status.value(), error,
                e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> methodArgumentNotValidException
            (MethodArgumentNotValidException e, HttpServletRequest request) {
        String error = "Invalid arguments";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        final List<String> errors = new ArrayList<>();
        for (final FieldError err : e.getBindingResult().getFieldErrors()) {
            errors.add(err.getField() + ": " + err.getDefaultMessage());
        }

        StandardError err = new StandardError(Instant.now(), status.value(), error,
                errors.toString(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<StandardError> handleInvalidEnumValue(
            HttpMessageNotReadableException e, HttpServletRequest request) {
        String error = "JSON parse error";

        if (e.getCause() instanceof InvalidFormatException invalidFormatException) {
            if (Enum.class.isAssignableFrom(invalidFormatException.getTargetType())) {
                Class<? extends Enum> enumClass = (Class<? extends Enum>)
                        invalidFormatException.getTargetType();
                String invalidValue = invalidFormatException.getValue().toString();
                String validValues = Arrays.stream(enumClass.getEnumConstants())
                        .map(Enum::name)
                        .collect(Collectors.joining(", "));

                String message = String.format(
                        "%s is not one of the values accepted for Enum Class %s, valid values are: %s",
                        invalidValue, enumClass.getSimpleName(), validValues);
                HttpStatus status = HttpStatus.BAD_REQUEST;
                StandardError err = new StandardError(Instant.now(), status.value(), error,
                        message, request.getRequestURI());
                return ResponseEntity.status(status).body(err);
            }
        }

        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(Instant.now(), status.value(), error,
                e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

}

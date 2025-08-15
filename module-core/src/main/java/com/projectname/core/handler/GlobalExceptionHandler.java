package com.projectname.core.handler;

import com.projectname.core.exception.ErrorCode;
import com.projectname.core.response.ApiErrorResponse;
import com.projectname.core.response.ApiErrorResponse.ValidationError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("handleMethodArgumentNotValidException", e);

        final List<ValidationError> errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new ValidationError(error.getField(), error.getDefaultMessage()))
                .toList();

        final ApiErrorResponse response = ApiErrorResponse.of(
                ErrorCode.INVALID_INPUT_VALUE.getStatus(),
                ErrorCode.INVALID_INPUT_VALUE.getErrorCode(),
                ErrorCode.INVALID_INPUT_VALUE.getMessage(),
                errors
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}

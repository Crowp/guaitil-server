package com.guaitilsoft.exceptions.controllers;

import com.guaitilsoft.exceptions.ApiRequestException;
import com.guaitilsoft.exceptions.models.ApiException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        // 1. Create payload containing exception details
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(
                e.getMessage(),
                e,
                badRequest,
                ZonedDateTime.now(ZoneId.of("America/Costa_Rica"))
        );
        // 2. return response entity
        return ResponseEntity.status(badRequest).body(apiException);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e,
                                                                         HttpHeaders headers, HttpStatus status, WebRequest request) {
        // 1. Create payload containing exception details
        HttpStatus methodNotAllowed = HttpStatus.METHOD_NOT_ALLOWED;
        ApiException apiException = new ApiException(
                e.getMessage(),
                e,
                methodNotAllowed,
                ZonedDateTime.now(ZoneId.of("America/Costa_Rica"))
        );
        // 2. return response entity
        return ResponseEntity.status(methodNotAllowed).body(apiException);

    }

    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<ApiException> handleConstraintViolationException(ConstraintViolationException e,
                                                                           WebRequest request) {
        // 1. Create payload containing exception details
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(
                e.getMessage(),
                e,
                badRequest,
                ZonedDateTime.now(ZoneId.of("America/Costa_Rica"))
        );
        // 2. return response entity
        return ResponseEntity.status(badRequest).body(apiException);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public final ResponseEntity<ApiException> handleEntityNotFoundException(EntityNotFoundException e,
                                                                           WebRequest request) {
        // 1. Create payload containing exception details
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(
                e.getMessage(),
                e,
                badRequest,
                ZonedDateTime.now(ZoneId.of("America/Costa_Rica"))
        );
        // 2. return response entity
        return ResponseEntity.status(badRequest).body(apiException);
    }

    @ExceptionHandler(ApiRequestException.class)
    public ResponseEntity<ApiException> handleApiRequestException(ApiRequestException e) {
        // 1. Create payload containing exception details
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(
                e.getMessage(),
                e,
                badRequest,
                ZonedDateTime.now(ZoneId.of("America/Costa_Rica"))
        );
        // 2. return response entity
        return ResponseEntity.status(badRequest).body(apiException);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ApiException> handleMaxSizeException(MaxUploadSizeExceededException e) {
        // 1. Create payload containing exception details
        HttpStatus expectationFailed = HttpStatus.EXPECTATION_FAILED;
        ApiException apiException = new ApiException(
                "File too large!",
                e,
                expectationFailed,
                ZonedDateTime.now(ZoneId.of("America/Costa_Rica"))
        );
        // 2. return response entity
        return ResponseEntity.status(expectationFailed).body(apiException);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiException> handleDataIntegrityViolationException(HttpServletRequest req, DataIntegrityViolationException e) {
        // 1. Create payload containing exception details
        HttpStatus conflictStatus = HttpStatus.CONFLICT;
        ApiException apiException = new ApiException(
                e.getMessage(),
                e,
                conflictStatus,
                ZonedDateTime.now(ZoneId.of("America/Costa_Rica"))
        );
        // 2. return response entity
        return ResponseEntity.status(conflictStatus).body(apiException);
    }
}

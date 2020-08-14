package com.guaitilsoft.exceptions.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class ApiException {
    private final String message;
    private final Throwable throwable;
    private final HttpStatus httpStatus;
    private final ZonedDateTime timestamp;
}

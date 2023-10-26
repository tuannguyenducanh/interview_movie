package com.bank.interview.movie.controller;

import com.bank.interview.movie.api.ApiError;
import com.bank.interview.movie.api.ErrorStatusCode;
import com.bank.interview.movie.exception.ConstraintValidationException;
import com.bank.interview.movie.exception.DataNotFoundException;
import com.bank.interview.movie.exception.detail.ExceptionDetailFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {

    private final ExceptionDetailFactory exceptionDetailFactory;

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ApiError> handleEntityNotFoundException(DataNotFoundException ex, HttpServletRequest httpServletRequest) {
        return getApiErrorResponseEntity(buildErrorResponse(ex, ErrorStatusCode.REQUEST_DATA_NOT_FOUND),
                httpServletRequest, ErrorStatusCode.REQUEST_DATA_NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintValidationException.class})
    public ResponseEntity<ApiError> handleMethodArgumentNotValidException(Exception ex, HttpServletRequest httpServletRequest) {
        return getApiErrorResponseEntity(buildErrorResponse(ex, ErrorStatusCode.INVALID_REQUEST),
                httpServletRequest, ErrorStatusCode.INVALID_REQUEST, ex.getMessage());
    }

    private ResponseEntity<ApiError> getApiErrorResponseEntity(ApiError apiError, HttpServletRequest httpServletRequest, ErrorStatusCode invalidRequest, String ex1) {
        log.error(String.format("MovieApp Request: %s - ErrorCode: %s - Message: %s",
                httpServletRequest.getServletPath(), invalidRequest.getErrorId(), ex1));
        return new ResponseEntity<>(apiError, invalidRequest.getHttpStatus());
    }

    private ApiError buildErrorResponse(Exception ex, ErrorStatusCode errorStatusCode) {
        return ApiError.builder()
                .errorId(errorStatusCode.getErrorId())
                .message(errorStatusCode.getMessage())
                .httpStatus(errorStatusCode.getHttpStatus().value())
                .details(exceptionDetailFactory.buildDetails(ex))
                .build();
    }

}

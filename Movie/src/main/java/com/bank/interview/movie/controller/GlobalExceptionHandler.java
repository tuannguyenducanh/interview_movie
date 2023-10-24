package com.bank.interview.movie.controller;

import com.bank.interview.movie.api.ApiError;
import com.bank.interview.movie.api.ErrorStatusCode;
import com.bank.interview.movie.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ApiError> handleEntityNotFoundException(DataNotFoundException dataNotFoundException, HttpServletRequest httpServletRequest) {
        ApiError apiError= buildErrorResponse(ErrorStatusCode.REQUEST_DATA_NOT_FOUND);
        log.error(String.format("MovieApp Request: %s - ErrorCode: %s - Message: %s", httpServletRequest.getServletPath(), ErrorStatusCode.REQUEST_DATA_NOT_FOUND.getErrorId(), dataNotFoundException.getMessage()));
        return new ResponseEntity<>(buildErrorResponse(ErrorStatusCode.REQUEST_DATA_NOT_FOUND), apiError.getHttpStatus());
    }

    private ApiError buildErrorResponse(ErrorStatusCode errorStatusCode) {
        return ApiError.builder()
                .errorId(errorStatusCode.getErrorId())
                .message(errorStatusCode.getMessage())
                .httpStatus(errorStatusCode.getHttpStatus())
                .build();
    }

}

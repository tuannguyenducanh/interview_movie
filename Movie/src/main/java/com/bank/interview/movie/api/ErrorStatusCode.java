package com.bank.interview.movie.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatusCode {

    REQUEST_DATA_NOT_FOUND("API-001", "Request Data Not Found", HttpStatus.NOT_FOUND);

    private String errorId;
    private String message;
    private HttpStatus httpStatus;
}

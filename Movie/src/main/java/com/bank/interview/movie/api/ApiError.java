package com.bank.interview.movie.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiError {

    private String errorId;
    private String message;
    private HttpStatus httpStatus;
}

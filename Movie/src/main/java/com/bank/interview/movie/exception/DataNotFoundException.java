package com.bank.interview.movie.exception;

import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
public class DataNotFoundException extends BaseException {

    private final String field;
    private final String location;

    public DataNotFoundException(String message, String location, String field) {
        super(message);
        this.field = field;
        this.location = location;
    }

}

package com.bank.interview.movie.exception;

import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
public class DataNotFoundException extends BaseException {

    private final String field;
    private final Object value;

    public DataNotFoundException(String message, Object value, String field) {
        super(message);
        this.field = field;
        this.value = value;
    }

    public String getField() {
        return field;
    }

    public Object getValue() {
        return value;
    }
}

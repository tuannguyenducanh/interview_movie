package com.bank.interview.movie.exception;

import java.math.BigDecimal;

public class ConstraintValidationException extends BaseException {

    private BigDecimal value;
    private String field;

    public ConstraintValidationException(String field, BigDecimal value, String message) {
        super(message);
        this.field = field;
        this.value = value;
    }

    public String getField() {
        return field;
    }

    public BigDecimal getValue() {
        return value;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}

package com.bank.interview.movie.exception.detail;

import com.bank.interview.movie.api.ApiErrorDetail;
import com.bank.interview.movie.exception.ConstraintValidationException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class ConstraintValidationExceptionDetail implements ExceptionDetail<ConstraintValidationException> {
    @Override
    public Class<ConstraintValidationException> getClazz() {
        return ConstraintValidationException.class;
    }

    @Override
    public List<ApiErrorDetail> buiApiErrorDetails(ConstraintValidationException e) {
        return Collections.singletonList(
                ApiErrorDetail.builder()
                        .field(e.getField())
                        .value(e.getValue())
                        .issue(e.getMessage())
                        .build());
    }
}

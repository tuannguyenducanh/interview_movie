package com.bank.interview.movie.exception.detail;

import com.bank.interview.movie.api.ApiErrorDetail;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MethodArgumentNotValidExceptionDetail implements  ExceptionDetail<MethodArgumentNotValidException>{
    @Override
    public Class<MethodArgumentNotValidException> getClazz() {
        return MethodArgumentNotValidException.class;
    }

    @Override
    public List<ApiErrorDetail> buiApiErrorDetails(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getFieldErrors().stream()
                .map(err -> {
                    if (err instanceof FieldError) {
                        FieldError fieldError = (FieldError) err;
                        return ApiErrorDetail.builder()
                                .field(fieldError.getField())
                                .value(String.format("%s", fieldError.getRejectedValue()))
                                .issue(fieldError.getDefaultMessage())
                                .build();
                    } else {
                        return ApiErrorDetail.builder()
                                .field(err.getObjectName())
                                .value(null)
                                .issue(err.getDefaultMessage())
                                .build();
                    }
                }).collect(Collectors.toList());
    }
}

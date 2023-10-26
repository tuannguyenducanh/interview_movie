package com.bank.interview.movie.exception.detail;

import com.bank.interview.movie.api.ApiErrorDetail;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@AllArgsConstructor
public class ExceptionDetailFactory {

    private final List<ExceptionDetail> exceptionDetails;

    public List<ApiErrorDetail> buildDetails(Exception ex) {
        return exceptionDetails.stream().filter(exceptionDetail -> ex.getClass() == exceptionDetail.getClazz())
                .findFirst().map(exceptionDetail -> exceptionDetail.buiApiErrorDetails(ex))
                .orElseGet(() -> Collections.singletonList(ApiErrorDetail.builder().build()));
    }

}

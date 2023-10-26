package com.bank.interview.movie.exception.detail;

import com.bank.interview.movie.api.ApiErrorDetail;
import com.bank.interview.movie.exception.DataNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class DataNotFoundExceptionDetail implements ExceptionDetail<DataNotFoundException> {

    @Override
    public Class<DataNotFoundException> getClazz() {
        return DataNotFoundException.class;
    }

    @Override
    public List<ApiErrorDetail> buiApiErrorDetails(DataNotFoundException dataNotFoundException) {
        return Collections.singletonList(ApiErrorDetail.builder().build());
    }
}

package com.bank.interview.movie.exception.detail;

import com.bank.interview.movie.api.ApiErrorDetail;

import java.util.List;

public interface ExceptionDetail<T>{

    Class<T> getClazz();
    List<ApiErrorDetail> buiApiErrorDetails(T t);
}

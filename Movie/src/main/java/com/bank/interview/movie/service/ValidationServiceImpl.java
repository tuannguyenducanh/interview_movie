package com.bank.interview.movie.service;

import com.bank.interview.movie.exception.ConstraintValidationException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ValidationServiceImpl implements ValidationService{

    public static final String RATING_FIELD = "rating";

    @Override
    public void validateRating(BigDecimal rating) {
        if (rating == null) {
            throw new ConstraintValidationException(RATING_FIELD, null, "Rating is empty");
        }
        if (BigDecimal.valueOf(0.5).compareTo(rating) > 0 || rating.compareTo(BigDecimal.valueOf(5)) > 0) {
            throw new ConstraintValidationException(RATING_FIELD, rating, "Rating must be between 0.5 and 5");
        }
    }

}

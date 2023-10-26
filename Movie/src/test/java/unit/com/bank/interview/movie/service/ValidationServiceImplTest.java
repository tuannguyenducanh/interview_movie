package unit.com.bank.interview.movie.service;

import com.bank.interview.movie.exception.ConstraintValidationException;
import com.bank.interview.movie.service.ValidationService;
import com.bank.interview.movie.service.ValidationServiceImpl;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidationServiceImplTest {

    private ValidationService validationService = new ValidationServiceImpl();

    @Test
    public void validateRating_WhenRatingIsMoreThan5_ThrowConstraintValidationException() {
        ConstraintValidationException exception = assertThrows(
                ConstraintValidationException.class,
                () ->  validationService.validateRating(BigDecimal.valueOf(5.1))
        );
        assertTrue(exception.getMessage().equals("Rating must be between 0.5 and 5"));
    }

    @Test
    public void validateRating_WhenRatingIsLessThanHalfOne_ThrowConstraintValidationException() {
        ConstraintValidationException exception = assertThrows(
                ConstraintValidationException.class,
                () ->  validationService.validateRating(BigDecimal.valueOf(0.1))
        );
        assertTrue(exception.getMessage().equals("Rating must be between 0.5 and 5"));
    }

}

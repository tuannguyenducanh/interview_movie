package unit.com.bank.interview.movie.exception.detail;

import com.bank.interview.movie.api.ApiErrorDetail;
import com.bank.interview.movie.exception.DataNotFoundException;
import com.bank.interview.movie.exception.detail.ConstraintValidationExceptionDetail;
import com.bank.interview.movie.exception.detail.DataNotFoundExceptionDetail;
import com.bank.interview.movie.exception.detail.ExceptionDetail;
import com.bank.interview.movie.exception.detail.ExceptionDetailFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class ExceptionDetailFactoryTest {

    @InjectMocks
    private ExceptionDetailFactory exceptionDetailFactory;

    @Mock
    private DataNotFoundExceptionDetail dataNotFoundExceptionDetail;

    @Mock
    private ConstraintValidationExceptionDetail constraintValidationExceptionDetail;

    @BeforeEach
    public void setup() {
        List<ExceptionDetail> exceptionDetails = Arrays.asList(dataNotFoundExceptionDetail, constraintValidationExceptionDetail);
        exceptionDetailFactory = new ExceptionDetailFactory(exceptionDetails);
    }

    @Test
    public void buildDetails_WhenExceptionNotHandle_returnDefaultDetail() {
        NullPointerException exception = new NullPointerException();
        List<ApiErrorDetail> details = exceptionDetailFactory.buildDetails(exception);
        ApiErrorDetail detail = details.get(0);
        Assertions.assertNull(detail.getField());
        Assertions.assertNull(detail.getValue());
        Assertions.assertNull(detail.getIssue());
    }

    @Test
    public void buildDetails_WhenThrowDataNotFoundException_returnExpectedDetail() {
        Mockito.when(dataNotFoundExceptionDetail.getClazz()).thenReturn(DataNotFoundException.class);
        ApiErrorDetail expectedDetail = ApiErrorDetail.builder().field("movie_id").value(1).build();
        Mockito.when(dataNotFoundExceptionDetail.buiApiErrorDetails(any()))
                .thenReturn(Arrays.asList(expectedDetail));

        DataNotFoundException exception = new DataNotFoundException("test message", 1, "movie_id");
        List<ApiErrorDetail> details = exceptionDetailFactory.buildDetails(exception);

        ApiErrorDetail detail = details.get(0);
        Assertions.assertEquals("movie_id", detail.getField());
        Assertions.assertEquals(1, detail.getValue());
        Assertions.assertNull(detail.getIssue());
    }
}

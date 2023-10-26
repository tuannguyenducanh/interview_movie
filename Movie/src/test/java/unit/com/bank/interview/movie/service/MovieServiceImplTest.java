package unit.com.bank.interview.movie.service;

import com.bank.interview.movie.api.MovieRequest;
import com.bank.interview.movie.api.MovieResponse;
import com.bank.interview.movie.repository.MovieRepository;
import com.bank.interview.movie.repository.entity.Movie;
import com.bank.interview.movie.service.MovieServiceImpl;
import com.bank.interview.movie.service.dto.MovieRequestDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class MovieServiceImplTest {

    public static final String TITLE = "Sunrise";
    public static final String CATEGORY = "Romantic";
    private static BigDecimal RATING = BigDecimal.valueOf(4.9);
    private static Long MOVIE_ID = 1L;;
    @InjectMocks
    private MovieServiceImpl movieService;

    @Mock
    private MovieRepository movieRepository;

    @Test
    public void getMovie_WhenMovieNotFound_ThrowEntityNotFoundException() {
        Mockito.when(movieRepository.findById(any())).thenReturn(Optional.empty());
        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () ->  movieService.getMovie(MOVIE_ID)
        );
        assertTrue(exception.getMessage().equals("Cannot find movie with id 1"));
    }

    @Test
    public void getMovie_WhenMovieIsFound_ReturnMovieResponse() {
        Mockito.when(movieRepository.findById(any()))
                .thenReturn(Optional.of(
                        buildMovie()));
        MovieResponse movieResponse = movieService.getMovie(MOVIE_ID);
        assertEquals(MOVIE_ID, movieResponse.getId());
        assertEquals(TITLE, movieResponse.getTitle());
        assertEquals(CATEGORY, movieResponse.getCategory());
        assertEquals(BigDecimal.valueOf(4.9), movieResponse.getRating());
    }

    @Test
    public void createMovie_WhenMovieIsPersist_ReturnMovieResponse() {
        Mockito.when(movieRepository.save(any()))
                .thenReturn(buildMovie());
        MovieResponse movieResponse = movieService.createMovie(buildMovieRequestDto());
        assertEquals(MOVIE_ID, movieResponse.getId());
        assertEquals(TITLE, movieResponse.getTitle());
        assertEquals(CATEGORY, movieResponse.getCategory());
        assertEquals(BigDecimal.valueOf(4.9), movieResponse.getRating());
    }

    @Test
    public void update_WhenMovieIsNotFound_ThrowEntityNotFoundException() {
        Mockito.when(movieRepository.findById(any())).thenReturn(Optional.empty());
        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () ->  movieService.update(MOVIE_ID, buildMovieRequestDto())
        );
        assertTrue(exception.getMessage().equals("Cannot find movie with id 1"));
    }

    @Test
    public void update_WhenMovieExist_ShouldUpdateAndReturnMovieResponse() {
        Mockito.when(movieRepository.findById(any()))
                .thenReturn(Optional.of(
                        buildMovie()));
        Mockito.when(movieRepository.save(any()))
                .thenReturn(Movie.builder()
                        .id(1L)
                        .title("Tom")
                        .category("Cartoon")
                        .rating(BigDecimal.ONE)
                        .build());
        MovieResponse movieResponse = movieService.update(MOVIE_ID, buildMovieRequestDto());
        assertEquals(MOVIE_ID, movieResponse.getId());
        assertEquals("Tom", movieResponse.getTitle());
        assertEquals("Cartoon", movieResponse.getCategory());
        assertEquals(BigDecimal.ONE, movieResponse.getRating());
    }

    @Test
    public void delete_WhenMovieNotFound_ThrowEntityNotFoundException() {
        Mockito.when(movieRepository.findById(any())).thenReturn(Optional.empty());
        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () ->  movieService.delete(MOVIE_ID)
        );
        assertTrue(exception.getMessage().equals("Cannot find movie with id 1"));
    }

    private static MovieRequestDto buildMovieRequestDtoUpdate() {
        return MovieRequestDto.builder().title("Tom").category("Cartoon").rating(BigDecimal.ONE).build();
    }

    private static MovieRequestDto buildMovieRequestDto() {
        return MovieRequestDto.builder().title(TITLE).category(CATEGORY).rating(RATING).build();
    }
    private static Movie buildMovie() {
        return Movie.builder().id(MOVIE_ID).title(TITLE).category(CATEGORY).rating(RATING).build();
    }
}

package integration.com.bank.interview.movie.service;

import com.bank.interview.movie.repository.MovieRepository;
import com.bank.interview.movie.repository.entity.Movie;
import integration.com.bank.interview.movie.ComponentTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@ComponentTest
public class MovieServiceImplIT {

    @Autowired
    private MovieRepository movieRepository;

    @Test
    public void per() {
        Optional<Movie> movieOptional = movieRepository.findById(3L);
        Movie movie = movieOptional.get();
        Assertions.assertNotNull(movie);
    }
}

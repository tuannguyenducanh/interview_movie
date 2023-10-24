package com.bank.interview.movie.controller;

import com.bank.interview.movie.api.MovieRequest;
import com.bank.interview.movie.api.MovieResponse;
import com.bank.interview.movie.constant.ErrorLocation;
import com.bank.interview.movie.exception.DataNotFoundException;
import com.bank.interview.movie.service.MovieService;
import com.bank.interview.movie.service.dto.MovieRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import java.util.concurrent.Callable;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/movie")
@Slf4j
public class MovieController {

    public static final String MOVIE_ID = "movie_id";
    private final MovieService movieService;

    @PostMapping
    public Callable<ResponseEntity<MovieResponse>> createMovie(@RequestBody MovieRequest movieRequest) {
        log.debug("message=\"Movie request: {}\"", movieRequest);
        return ()  -> {
            MovieRequestDto movieRequestDto = mapRequestMovieToDto(movieRequest);
            MovieResponse movieResponse = movieService.createMovie(movieRequestDto);
            return new ResponseEntity<>(movieResponse, HttpStatus.CREATED);
        };
    }

    @GetMapping("/{movie_id}")
    public Callable<ResponseEntity<MovieResponse>> getMovie(@PathVariable(MOVIE_ID) Long movieId) {
        return () -> {
            try {
                MovieResponse movieResponse = movieService.getMovie(movieId);
                return new ResponseEntity<>(movieResponse, HttpStatus.OK);
            } catch (EntityNotFoundException ex) {
                throw new DataNotFoundException(ex.getMessage(), ErrorLocation.PATH, MOVIE_ID);
            }
        };
    }
    @PutMapping("/{movie_id}")
    public Callable<ResponseEntity<MovieResponse>> updateMovie(@PathVariable(MOVIE_ID) Long movieId, @RequestBody MovieRequest movieRequest) {
        return () -> {
            log.debug("message=\"Movie id: {}, Movie request: {}\"", movieId, movieRequest);
            try {
                MovieRequestDto movieRequestDto = mapRequestMovieToDto(movieRequest);
                MovieResponse movieResponse = movieService.update(movieId, movieRequestDto);
                return new ResponseEntity<>(movieResponse, HttpStatus.OK);
            } catch (EntityNotFoundException ex) {
                throw new DataNotFoundException(ex.getMessage(), ErrorLocation.PATH, MOVIE_ID);
            }
        };
    }

    private static MovieRequestDto mapRequestMovieToDto(MovieRequest movieRequest) {
        return MovieRequestDto.builder()
                .title(movieRequest.getTitle())
                .category(movieRequest.getCategory())
                .rating(movieRequest.getRating())
                .build();
    }

    @DeleteMapping("/{movie_id}")
    public Callable<ResponseEntity<MovieResponse>> deleteMovie(@PathVariable(MOVIE_ID) Long movieId) {
        return () -> {
            try {
                log.debug("message=\"Delete movie id: {}\"", movieId);
                movieService.delete(movieId);
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (EntityNotFoundException ex) {
                throw new DataNotFoundException(ex.getMessage(), ErrorLocation.PATH, MOVIE_ID);
            }
        };
    }
}

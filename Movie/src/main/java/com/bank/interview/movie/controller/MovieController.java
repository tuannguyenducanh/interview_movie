package com.bank.interview.movie.controller;

import com.bank.interview.movie.model.MovieRequest;
import com.bank.interview.movie.model.MovieResponse;
import com.bank.interview.movie.service.MovieService;
import com.bank.interview.movie.service.dto.MovieRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/movie")
@Slf4j
public class MovieController {

    private final MovieService movieService;

    @PostMapping
    public Callable<ResponseEntity<MovieResponse>> createMovie(@RequestBody MovieRequest movieRequest) {
        log.debug("message=\"Movie request: {}\"", movieRequest);
        return ()  -> {
            MovieRequestDto movieRequestDto = MovieRequestDto.builder()
                    .title(movieRequest.getTitle())
                    .category(movieRequest.getCategory())
                    .rating(movieRequest.getRating())
                    .build();
            MovieResponse movieResponse = movieService.createMovie(movieRequestDto);
            return new ResponseEntity<>(movieResponse, HttpStatus.CREATED);
        };
    }

    @GetMapping("/{movie_id}")
    public Callable<ResponseEntity<MovieResponse>> getMovie(@PathVariable("movie_id") Long movieId) {

        return () -> {
            MovieResponse movieResponse = movieService.getMovie(movieId);
            return new ResponseEntity<>(movieResponse, HttpStatus.OK);
        };
    }
}

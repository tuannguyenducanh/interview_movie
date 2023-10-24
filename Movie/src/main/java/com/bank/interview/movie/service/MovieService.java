package com.bank.interview.movie.service;

import com.bank.interview.movie.api.MovieResponse;
import com.bank.interview.movie.service.dto.MovieRequestDto;

public interface MovieService {

    MovieResponse createMovie(MovieRequestDto movieRequestDto);

    MovieResponse getMovie(Long movieId);

    MovieResponse update(Long movieId, MovieRequestDto movieRequestDto);

    void delete(Long movieId);
}

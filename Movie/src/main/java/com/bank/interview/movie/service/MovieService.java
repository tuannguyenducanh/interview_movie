package com.bank.interview.movie.service;

import com.bank.interview.movie.model.MovieResponse;
import com.bank.interview.movie.service.dto.MovieRequestDto;

public interface MovieService {

    MovieResponse createMovie(MovieRequestDto movieRequestDto);

    MovieResponse getMovie(Long movieId);
}
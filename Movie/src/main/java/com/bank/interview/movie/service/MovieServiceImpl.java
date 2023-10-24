package com.bank.interview.movie.service;

import com.bank.interview.movie.model.MovieResponse;
import com.bank.interview.movie.repository.MovieRepository;
import com.bank.interview.movie.repository.entity.Movie;
import com.bank.interview.movie.service.dto.MovieRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@RequiredArgsConstructor
@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    @Override
    public MovieResponse createMovie(MovieRequestDto movieRequestDto) {
        Movie movie = Movie.builder()
                .title(movieRequestDto.getTitle())
                .category(movieRequestDto.getCategory())
                .rating(movieRequestDto.getRating())
                .build();
        movieRepository.save(movie);
        return MovieResponse.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .category(movie.getCategory())
                .rating(movie.getRating())
                .build();
    }

    @Override
    public MovieResponse getMovie(Long movieId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find movie with id " + movieId));
        return MovieResponse.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .category(movie.getCategory())
                .rating(movie.getRating())
                .build();
    }
}

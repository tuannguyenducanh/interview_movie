package com.bank.interview.movie.service;

import com.bank.interview.movie.api.MoviePage;
import com.bank.interview.movie.api.MovieResponse;
import com.bank.interview.movie.repository.MovieRepository;
import com.bank.interview.movie.repository.entity.Movie;
import com.bank.interview.movie.service.dto.MovieRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

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
        Movie persistedMovie = movieRepository.save(movie);
        return mapResponse(persistedMovie);
    }

    @Override
    public MovieResponse getMovie(Long movieId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find movie with id " + movieId));
        return mapResponse(movie);
    }

    @Override
    @Transactional
    public MovieResponse update(Long movieId, MovieRequestDto movieRequestDto) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find movie with id " + movieId));
        Movie updatedMovie = Movie.builder()
                .id(movie.getId())
                .title(movieRequestDto.getTitle())
                .category(movieRequestDto.getCategory())
                .rating(movieRequestDto.getRating())
                .build();
        Movie persistedMovie = movieRepository.save(updatedMovie);
        return mapResponse(persistedMovie);
    }

    @Override
    public void delete(Long movieId) {
        movieRepository.findById(movieId)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find movie with id " + movieId));
        movieRepository.deleteById(movieId);
    }

    @Override
    public MoviePage<MovieResponse> findAll(Pageable pageable) {
        Page<Movie> page = movieRepository.findAll(pageable);
        List<MovieResponse> movies = page.getContent().stream().map(this::mapResponse).collect(Collectors.toList());
        return new MoviePage<>(movies, page.getTotalElements(), pageable);
    }

    private MovieResponse mapResponse(Movie movie) {
        return MovieResponse.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .category(movie.getCategory())
                .rating(movie.getRating())
                .build();
    }
}

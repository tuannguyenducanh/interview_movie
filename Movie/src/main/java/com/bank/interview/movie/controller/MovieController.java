package com.bank.interview.movie.controller;

import com.bank.interview.movie.api.MoviePage;
import com.bank.interview.movie.api.MovieRequest;
import com.bank.interview.movie.api.MovieResponse;
import com.bank.interview.movie.constant.ErrorLocation;
import com.bank.interview.movie.exception.DataNotFoundException;
import com.bank.interview.movie.service.MovieService;
import com.bank.interview.movie.service.ValidationService;
import com.bank.interview.movie.service.dto.MovieRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
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
import javax.validation.Valid;
import java.util.concurrent.Callable;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/movie")
@Slf4j
public class MovieController {

    public static final String MOVIE_ID = "movie_id";
    private final MovieService movieService;
    private final ValidationService validationService;

    @Operation(summary = "Create movie", description = "Returns a movie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created")
    })
    @PostMapping
    public ResponseEntity<MovieResponse> createMovie(@Valid @RequestBody MovieRequest movieRequest) {
        log.debug("message=\"Movie request: {}\"", movieRequest);
        validationService.validateRating(movieRequest.getRating());
        MovieRequestDto movieRequestDto = mapRequestMovieToDto(movieRequest);
        MovieResponse movieResponse = movieService.createMovie(movieRequestDto);
        return new ResponseEntity<>(movieResponse, HttpStatus.CREATED);
    }

    @Operation(summary = "Get movie", description = "Returns a movie by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    @GetMapping("/{movie_id}")
    public ResponseEntity<MovieResponse> getMovie(@PathVariable(MOVIE_ID)
                                                      @Parameter(name = "id", description = "Movie id", example = "1") Long movieId) {
        try {
            MovieResponse movieResponse = movieService.getMovie(movieId);
            return new ResponseEntity<>(movieResponse, HttpStatus.OK);
        } catch (EntityNotFoundException ex) {
            throw new DataNotFoundException(ex.getMessage(), ErrorLocation.PATH, MOVIE_ID);
        }
    }

    @Operation(summary = "Get list of movie", description = "Get list of movie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return list of movie")
    })
    @GetMapping
    public ResponseEntity<MoviePage<MovieResponse>> getMovieList(
            @SortDefault(sort = "id") @PageableDefault(size=10) final Pageable pageable) {
        MoviePage<MovieResponse> movieResponses = movieService.findAll(pageable);
        return new ResponseEntity<>(movieResponses, HttpStatus.OK);
    }

    @Operation(summary = "Update movie", description = "Return existing movie after updating")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated"),
            @ApiResponse(responseCode = "404", description = "Not found - The movie was not found")
    })
    @PutMapping("/{movie_id}")
    public ResponseEntity<MovieResponse> updateMovie(
            @PathVariable(MOVIE_ID) Long movieId, @Valid @RequestBody MovieRequest movieRequest) {
        log.debug("message=\"Movie id: {}, Movie request: {}\"", movieId, movieRequest);
        validationService.validateRating(movieRequest.getRating());
        try {
            MovieRequestDto movieRequestDto = mapRequestMovieToDto(movieRequest);
            MovieResponse movieResponse = movieService.update(movieId, movieRequestDto);
            return new ResponseEntity<>(movieResponse, HttpStatus.OK);
        } catch (EntityNotFoundException ex) {
            throw new DataNotFoundException(ex.getMessage(), ErrorLocation.PATH, MOVIE_ID);
        }
    }

    @Operation(summary = "Delete movie", description = "Delete existing movie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Not found - The movie was not found")
    })
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

    private static MovieRequestDto mapRequestMovieToDto(MovieRequest movieRequest) {
        return MovieRequestDto.builder()
                .title(movieRequest.getTitle())
                .category(movieRequest.getCategory())
                .rating(movieRequest.getRating())
                .build();
    }

}

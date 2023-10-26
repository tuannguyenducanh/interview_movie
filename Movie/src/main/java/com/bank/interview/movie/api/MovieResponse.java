package com.bank.interview.movie.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieResponse {

    @Schema(name = "Movie id", example = "1", required = true)
    private Long id;

    @Schema(name = "Movie title", example = "Sunrise", required = true)
    private String title;

    @Schema(name = "Movie category", example = "Romantic", required = true)
    private String category;

    @Schema(name = "Rating movie", example = "3.4", required = true)
    private BigDecimal rating;

}

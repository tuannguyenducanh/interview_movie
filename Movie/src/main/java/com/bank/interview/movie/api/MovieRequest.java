package com.bank.interview.movie.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieRequest {

    @Schema(name = "Movie title", example = "Sunrise", required = true)
    @Size(min = 1, max = 50, message = "Field length must between 1 and 50")
    private String title;

    @Schema(name = "Movie category", example = "Romantic", required = true)
    @Size(min = 1, max = 50, message = "Field length must between 1 and 50")
    private String category;

    @Schema(name = "Rating movie", example = "3.4", required = true)
    private BigDecimal rating;

}

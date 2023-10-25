package com.bank.interview.movie.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties({
        "pageable",
        "number",
        "numberOfElements",
        "first",
        "last",
        "empty"
})
public class MoviePage<T> extends PageImpl<T> {

    public MoviePage(final List<T> content, final long totalElements, final Pageable pageable) {
        super(content, pageable, totalElements);
    }

    @JsonProperty("sort")
    public List<String> getSortList() {
        return getSort().stream()
                .map(order -> order.getProperty() + "," + order.getDirection().name())
                .collect(Collectors.toList());
    }

}

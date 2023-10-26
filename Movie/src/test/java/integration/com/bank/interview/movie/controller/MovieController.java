package integration.com.bank.interview.movie.controller;

import com.bank.interview.movie.repository.MovieRepository;
import com.bank.interview.movie.repository.entity.Movie;
import integration.com.bank.interview.movie.ComponentTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@ComponentTest
public class MovieController {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MovieRepository movieRepository;

    @Test
    @Sql(scripts = {"/db/cleanup.sql", "/db/insert_movie.sql"})
    public void getMovie_WhenIdExistInDb_ShouldReturn200() throws Exception {
        mockMvc.perform(get("/v1/movie/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Tom and Jerry")))
                .andExpect(jsonPath("$.category", is("Cartoon")))
                .andExpect(jsonPath("$.rating", is(3.0)));
    }

    @Test
    @Sql(scripts = {"/db/cleanup.sql"})
    public void getMovie_WhenIdNotExistInDb_ShouldReturn404() throws Exception {
        mockMvc.perform(get("/v1/movie/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(jsonPath("$.errorId", is("API-001")))
                .andExpect(jsonPath("$.message", is("Request Data Not Found")))
                .andExpect(jsonPath("$.httpStatus", is(404)));
    }
    @Test
    @Sql(scripts = {"/db/cleanup.sql"})
    public void createMovie_WhenBodyIsValid_ShouldReturn201() throws Exception {
        mockMvc.perform(post("/v1/movie").contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"title\": \"Tomb Raider0\",\n" +
                                "    \"category\": \"Action\",\n" +
                                "    \"rating\": 5\n" +
                                "}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Tomb Raider0")))
                .andExpect(jsonPath("$.category", is("Action")))
                .andExpect(jsonPath("$.rating", is(5.0)));
    }

}

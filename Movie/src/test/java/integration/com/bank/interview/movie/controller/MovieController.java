package integration.com.bank.interview.movie.controller;

import com.bank.interview.movie.repository.MovieRepository;
import integration.com.bank.interview.movie.ComponentTest;
import integration.com.bank.interview.movie.util.FileUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.file.Files;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
                .andExpect(content().json(FileUtil.readJsonFile(("/data/get_movie_response_200.json")), true));
    }

    @Test
    @Sql(scripts = {"/db/cleanup.sql"})
    public void getMovie_WhenIdNotExistInDb_ShouldReturn404() throws Exception {
        mockMvc.perform(get("/v1/movie/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(content().json(FileUtil.readJsonFile("/data/get_movie_response_404.json"), true));
    }

    @Test
    @Sql(scripts = {"/db/cleanup.sql"})
    public void createMovie_WhenBodyIsValid_ShouldReturn201() throws Exception {
        mockMvc.perform(post("/v1/movie").contentType(MediaType.APPLICATION_JSON)
                        .content(FileUtil.readJsonFile("/data/create_movie_request_201.json")))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(content().json(Files.readString(new ClassPathResource("/data/create_movie_response_201.json").getFile().toPath()), true));
    }

    @Test
    public void createMovie_WhenTitleIsEmpty_ShouldReturn400() throws Exception {
        mockMvc.perform(post("/v1/movie").contentType(MediaType.APPLICATION_JSON)
                        .content(FileUtil.readJsonFile("/data/create_movie_title_empty_request.json")))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(content().json(Files.readString(new ClassPathResource("/data/create_movie_title_empty_response_400.json").getFile().toPath()), true));
    }

    @Test
    public void createMovie_WhenCategoryIsEmpty_ShouldReturn400() throws Exception {
        mockMvc.perform(post("/v1/movie").contentType(MediaType.APPLICATION_JSON)
                        .content(FileUtil.readJsonFile("/data/create_movie_category_empty_request.json")))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(content().json(Files.readString(new ClassPathResource("/data/create_movie_category_empty_response_400.json").getFile().toPath()), true));
    }

    @Test
    public void createMovie_WhenRatingOver5_ShouldReturn400() throws Exception {
        mockMvc.perform(post("/v1/movie").contentType(MediaType.APPLICATION_JSON)
                        .content(FileUtil.readJsonFile("/data/create_movie_rating_over5_request.json")))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(content().json(Files.readString(new ClassPathResource("/data/create_movie_rating_over5_response_400.json").getFile().toPath()), true));
    }

    @Test
    public void createMovie_WhenRatingUnderHalfOfOne_ShouldReturn400() throws Exception {
        mockMvc.perform(post("/v1/movie").contentType(MediaType.APPLICATION_JSON)
                        .content(FileUtil.readJsonFile("/data/create_movie_rating_under_half_of_one_request.json")))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(content().json(Files.readString(new ClassPathResource("/data/create_movie_rating_under_half_of_one_response_400.json").getFile().toPath()), true));
    }

    @Test
    @Sql(scripts = {"/db/cleanup.sql", "/db/insert_movie.sql"})
    public void updateMovie_WhenDataIsValid_ShouldReturn200() throws Exception {
        mockMvc.perform(put("/v1/movie/1").contentType(MediaType.APPLICATION_JSON)
                        .content(FileUtil.readJsonFile("/data/update_movie_successful_request.json")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().json(Files.readString(new ClassPathResource("/data/update_movie_successful_response.json").getFile().toPath()), true));
    }

    @Test
    @Sql(scripts = {"/db/cleanup.sql"})
    public void updateMovie_WhenMovieIdNotExist_ShouldReturn404() throws Exception {
        mockMvc.perform(put("/v1/movie/1").contentType(MediaType.APPLICATION_JSON)
                        .content(FileUtil.readJsonFile("/data/update_movie_successful_request.json")))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(content().json(Files.readString(new ClassPathResource("/data/update_movie_not_found_movie_id_response.json").getFile().toPath()), true));
    }

    @Test
    @Sql(scripts = {"/db/cleanup.sql", "/db/insert_movie.sql"})
    public void deleteMovie_WhenMovieIdExist_ShouldReturn200() throws Exception {
        mockMvc.perform(delete("/v1/movie/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Sql(scripts = {"/db/cleanup.sql"})
    public void deleteMovie_WhenMovieIdExist_ShouldReturn404() throws Exception {
        mockMvc.perform(delete("/v1/movie/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(content().json(Files.readString(new ClassPathResource("/data/delete_movie_response_404.json").getFile().toPath()), true));;
    }

    @Test
    @Sql(scripts = {"/db/cleanup.sql", "/db/insert_movie_test_getList.sql"})
    public void getMovieList_WhenHave11MoviesInDb_ReturnPageZero() throws Exception {
        mockMvc.perform(get("/v1/movie").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().json(Files.readString(new ClassPathResource("/data/get_list_move_page_0_response.json").getFile().toPath()), true));;
    }

    @Test
    @Sql(scripts = {"/db/cleanup.sql", "/db/insert_movie_test_getList.sql"})
    public void getMovieList_WhenHave11MoviesInDbAndParamPage1_ReturnPageOne() throws Exception {
        mockMvc.perform(get("/v1/movie").contentType(MediaType.APPLICATION_JSON).param("page", String.valueOf(1)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().json(Files.readString(new ClassPathResource("/data/get_list_move_page_1_response.json").getFile().toPath()), true));;
    }
}
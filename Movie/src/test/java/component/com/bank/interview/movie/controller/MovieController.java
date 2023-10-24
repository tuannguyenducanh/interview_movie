package component.com.bank.interview.movie.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@Sql(scripts = {"/db/cleanup.sql"})
@SpringBootTest(classes = {MovieController.class})
@AutoConfigureMockMvc
public class MovieController {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void adsad() throws Exception {
        mockMvc.perform(post("/").contentType(MediaType.APPLICATION_JSON).content(""))
                .andExpect(content().json(""));
    }
}

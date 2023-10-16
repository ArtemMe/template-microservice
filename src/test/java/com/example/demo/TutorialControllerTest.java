package com.example.demo;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

public class TutorialControllerTest extends DemoApplicationTests {
    private final TutorialRepository tutorialRepository;
    private final AuthorRepository authorRepository;
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    public TutorialControllerTest(
            @Autowired TutorialRepository tutorialRepository,
            @Autowired AuthorRepository authorRepository,
            @Autowired MockMvc mockMvc,
            @Autowired ObjectMapper objectMapper
    ) {
        this.tutorialRepository = tutorialRepository;
        this.authorRepository = authorRepository;
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @BeforeEach
    public void beforeEach() {
        authorRepository.deleteAll();
    }

    @Test
    public void createTutorial() throws Exception {
        this.mockMvc
                .perform(
                        post("/api/tutorials")
                                .content(objectMapper.writeValueAsString(new Tutorial("title1", "des", false)))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void updateTutorial() throws Exception {
        var t = tutorialRepository.save(new Tutorial("title1", "des", true));

        this.mockMvc
                .perform(
                        put("/api/tutorials/" + t.getId())
                                .content(objectMapper.writeValueAsString(new Tutorial("title2", "des", false)))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("title2")));
    }

    @Test
    public void getAllTutorials() throws Exception {
        tutorialRepository.save(new Tutorial("title", "des", true));
        tutorialRepository.save(new Tutorial("title2", "des2", true));
        this.mockMvc
                .perform(get("/api/tutorials"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("title")))
                .andExpect(content().string(containsString("title2")));
    }

    @Test
    public void checkNplus1Problem() throws Exception {
        var a = authorRepository.save(
                new Author("a1",
                        List.of(
                                new Tutorial("title1", "des", true),
                                new Tutorial("title2", "des", true),
                                new Tutorial("title3", "des", true),
                                new Tutorial("title4", "des", true)
                        )
                )
        );
        var a3 = authorRepository.save(
                new Author("a2",
                        List.of(
                                new Tutorial("title5", "des", true),
                                new Tutorial("title6", "des", true),
                                new Tutorial("title7", "des", true),
                                new Tutorial("title8", "des", true)
                        )
                )
        );

        var a2 = authorRepository.findAll();
    }

    @Test
    public void fixedNplus1Problem() throws Exception {
        var a = authorRepository.save(
                new Author("a1",
                        List.of(
                                new Tutorial("title1", "des", true),
                                new Tutorial("title2", "des", true),
                                new Tutorial("title3", "des", true),
                                new Tutorial("title4", "des", true)
                        )
                )
        );
        var a3 = authorRepository.save(
                new Author("a2",
                        List.of(
                                new Tutorial("title5", "des", true),
                                new Tutorial("title6", "des", true),
                                new Tutorial("title7", "des", true),
                                new Tutorial("title8", "des", true)
                        )
                )
        );

        var a2 = authorRepository.findAllAuthors();

    }

}

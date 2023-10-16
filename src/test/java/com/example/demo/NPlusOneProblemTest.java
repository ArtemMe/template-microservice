package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class NPlusOneProblemTest extends DemoApplicationTests {
    private final TutorialRepository tutorialRepository;
    private final AuthorRepository authorRepository;

    public NPlusOneProblemTest(
            @Autowired TutorialRepository tutorialRepository,
            @Autowired AuthorRepository authorRepository
    ) {
        this.tutorialRepository = tutorialRepository;
        this.authorRepository = authorRepository;
    }

    @BeforeEach
    public void beforeEach() {
        authorRepository.deleteAll();
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

package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api")
public class TutorialController {
    private final TutorialRepository tutorialRepository;

    public TutorialController(TutorialRepository tutorialRepository) {
        this.tutorialRepository = tutorialRepository;
    }

    @GetMapping("/tutorials")
    public List<Tutorial> getAllTutorials() {
        return StreamSupport.stream(tutorialRepository.findAll().spliterator(),false)
                .toList();
    }

    @GetMapping("/tutorials/{id}")
    public Tutorial getTutorialById(@PathVariable String id) {
        return tutorialRepository.findById(Long.getLong(id)).get();
    }

    @PostMapping("/tutorials")
    public ResponseEntity getTutorialById(@RequestBody Tutorial tutorial) {
        tutorialRepository.save(tutorial);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/tutorials/{id}")
    public ResponseEntity<Tutorial> updateTutorial(@PathVariable("id") long id, @RequestBody Tutorial tutorial) {
        Optional<Tutorial> tutorialData = tutorialRepository.findById(id);

        if (tutorialData.isPresent()) {
            Tutorial _tutorial = tutorialData.get();
            _tutorial.setTitle(tutorial.getTitle());
            _tutorial.setDescription(tutorial.getDescription());
            _tutorial.setPublished(tutorial.isPublished());
            return new ResponseEntity<>(tutorialRepository.save(_tutorial), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

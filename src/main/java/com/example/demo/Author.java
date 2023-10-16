package com.example.demo;

import javax.persistence.*;
import java.util.List;

@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Column(name = "tutorial_id")
    private List<Tutorial> tutorials;

    public Author() {
    }

    public Author(String name, List<Tutorial> tutorials) {
        this.name = name;
        this.tutorials = tutorials;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Tutorial> getTutorial() {
        return tutorials;
    }

    public void setTutorial(List<Tutorial> tutorials) {
        this.tutorials = tutorials;
    }
}

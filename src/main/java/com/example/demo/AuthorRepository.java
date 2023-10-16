package com.example.demo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {
    @Query("SELECT a FROM Author a JOIN FETCH a.tutorials")
    List<Author> findAllAuthors();
}

package com.github.gajicoding.todo_api_project.api.v2.repository;

import com.github.gajicoding.todo_api_project.api.v2.data.entity.Author;

import java.util.Optional;

public interface AuthorRepository {
    boolean existsByEmail(String email);

    boolean existsById(Long id);

    long addAuthor(Author reqAuthor);

    Optional<Author> findAuthorById(Long id);
}

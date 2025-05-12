package com.github.gajicoding.todo_api_project.api.v2.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Author {
    private Long id;
    private String name;
    private String email;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public Author(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Author(Long id) {
        this.id = id;
    }

    public Author(Long id, String name, String email, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}

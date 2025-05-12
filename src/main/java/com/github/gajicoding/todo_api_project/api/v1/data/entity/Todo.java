package com.github.gajicoding.todo_api_project.api.v1.data.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public class Todo {
    @Setter
    private Long id;
    private final String title;
    private final String contents;
    private final String author;
    @Setter
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public Todo(String title, String contents, String author, String password) {
        this.title = title;
        this.contents = contents;
        this.author = author;
        this.password = password;
    }

    public Todo(long id, String title, String contents, String author, String password, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.author = author;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}

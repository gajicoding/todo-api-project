package com.github.gajicoding.todo_api_project.data.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public class Todo {
    @Setter
    private Long id;
    private String title;
    private String contents;
    private String author;
    @Setter
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public Todo(String title, String contents, String author) {
        this.title = title;
        this.contents = contents;
        this.author = author;
    }

    public Todo(long id, String title, String contents, String author, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.author = author;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}

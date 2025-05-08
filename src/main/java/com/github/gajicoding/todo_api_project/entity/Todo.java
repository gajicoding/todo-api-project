package com.github.gajicoding.todo_api_project.entity;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Todo {
    private Long id;
    private String title;
    private String contents;
    private String author;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Todo(String title, String contents, String author, String password) {
        this.title = title;
        this.contents = contents;
        this.author = author;
        this.password = password;
    }
}

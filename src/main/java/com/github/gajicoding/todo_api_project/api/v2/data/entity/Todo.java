package com.github.gajicoding.todo_api_project.api.v2.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Todo {
    private Long id;
    private String title;
    private String contents;
    private Author author;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public Todo(String title, String contents, Author author) {
        this.title = title;
        this.contents = contents;
        this.author = author;
    }
}

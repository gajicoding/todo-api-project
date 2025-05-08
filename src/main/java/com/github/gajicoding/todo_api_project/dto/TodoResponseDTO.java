package com.github.gajicoding.todo_api_project.dto;

import com.github.gajicoding.todo_api_project.entity.Todo;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TodoResponseDTO {
    private Long id;
    private String title;
    private String contents;
    private String author;

    public TodoResponseDTO(Todo todo) {
        this.id = todo.getId();
        this.title = todo.getTitle();
        this.contents = todo.getContents();
        this.author = todo.getAuthor();
    }
}

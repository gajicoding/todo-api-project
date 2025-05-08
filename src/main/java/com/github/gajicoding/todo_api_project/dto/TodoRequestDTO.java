package com.github.gajicoding.todo_api_project.dto;

import com.github.gajicoding.todo_api_project.entity.Todo;
import lombok.Getter;

@Getter
public class TodoRequestDTO {
    private String title;
    private String contents;
    private String author;
    private String password;

    public Todo toEntity() {
        return Todo.builder()
                .title(title)
                .contents(contents)
                .author(author)
                .build();
    }
}

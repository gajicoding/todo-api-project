package com.github.gajicoding.todo_api_project.data.dto;

import com.github.gajicoding.todo_api_project.data.entity.Todo;
import lombok.Getter;
import lombok.Setter;

@Getter
public class TodoRequestDTO {
    private String title;
    private String contents;
    private String author;
    @Setter
    private String password;

    public Todo toEntity() {
        return Todo.builder()
                .title(title)
                .contents(contents)
                .author(author)
                .build();
    }
}

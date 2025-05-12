package com.github.gajicoding.todo_api_project.api.v2.data.dto;

import com.github.gajicoding.todo_api_project.api.v2.data.entity.Author;
import com.github.gajicoding.todo_api_project.api.v2.data.entity.Todo;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TodoRequestDTO {
    private String title;
    private String contents;
    private Long authorId;
    private String password;

    public Todo toEntity() {
        return Todo.builder()
                .title(title)
                .contents(contents)
                .author(new Author(authorId))
                .build();
    }
}

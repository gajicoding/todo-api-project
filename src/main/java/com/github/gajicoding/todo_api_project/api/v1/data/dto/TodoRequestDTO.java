package com.github.gajicoding.todo_api_project.api.v1.data.dto;

import com.github.gajicoding.todo_api_project.api.v1.data.entity.Todo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
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
                .password(password)
                .build();
    }
}

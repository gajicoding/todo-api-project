package com.github.gajicoding.todo_api_project.api.v2.data.dto;

import com.github.gajicoding.todo_api_project.api.v2.data.entity.Todo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class TodoResponseDTO {
    private Long id;
    private String title;
    private String contents;
    private AuthorResponseDTO author;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public TodoResponseDTO(Todo todo){
        this.id = todo.getId();
        this.title = todo.getTitle();
        this.contents = todo.getContents();
        this.author = new AuthorResponseDTO(todo.getAuthor());
        this.createdAt = author.getCreatedAt();
        this.updatedAt = author.getUpdatedAt();
    }
}

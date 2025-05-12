package com.github.gajicoding.todo_api_project.api.v2.data.dto;

import com.github.gajicoding.todo_api_project.api.v2.data.entity.Author;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class AuthorRequestDTO {
    private String name;
    private String email;
    @Setter
    private String password;

    public Author toEntity() {
        return Author.builder()
                .name(name)
                .email(email)
                .password(password)
                .build();
    }
}

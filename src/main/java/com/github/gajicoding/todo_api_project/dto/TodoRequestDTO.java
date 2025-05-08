package com.github.gajicoding.todo_api_project.dto;

import lombok.Getter;

@Getter
public class TodoRequestDTO {
    private String title;
    private String contents;
    private String author;
    private String password;
}

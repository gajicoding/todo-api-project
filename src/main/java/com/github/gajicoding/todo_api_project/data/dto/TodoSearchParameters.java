package com.github.gajicoding.todo_api_project.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class TodoSearchParameters {
    private String author;
    private LocalDate updatedAt;

    public TodoSearchParameters(String author, String updatedAtString) {
        this.author = author;
        this.updatedAt = updatedAtString == null ? null : LocalDate.parse(updatedAtString);
    }
}

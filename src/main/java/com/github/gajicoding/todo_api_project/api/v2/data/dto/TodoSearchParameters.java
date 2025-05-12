package com.github.gajicoding.todo_api_project.api.v2.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class TodoSearchParameters {
    private Long authorId;
    private LocalDate updatedAt;
    private int page;
    private int size;

    public TodoSearchParameters(Long authorId, String updatedAtString, int page, int size) {
        this.authorId = authorId;
        this.updatedAt = updatedAtString == null ? null : LocalDate.parse(updatedAtString);
        this.page = page;
        this.size = size;
    }
}

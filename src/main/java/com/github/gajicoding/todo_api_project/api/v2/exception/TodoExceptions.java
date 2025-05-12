package com.github.gajicoding.todo_api_project.api.v2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class TodoExceptions {
    private TodoExceptions() {}

    public static ResponseStatusException notChanged() {
        return new ResponseStatusException(HttpStatus.CONFLICT, "변경된 내용이 없습니다.");
    }

    public static ResponseStatusException notFound() {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 일정이 존재하지 않습니다.");
    }
}

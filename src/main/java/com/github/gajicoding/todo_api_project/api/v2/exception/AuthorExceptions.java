package com.github.gajicoding.todo_api_project.api.v2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AuthorExceptions {
    private AuthorExceptions() {}

    public static ResponseStatusException notFound() {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 작성자가 존재하지 않습니다.");
    }

    public static ResponseStatusException invalidPassword() {
        return new ResponseStatusException(HttpStatus.FORBIDDEN, "비밀번호가 올바르지 않습니다.");
    }

    public static ResponseStatusException emailAlreadyExists() {
        return new ResponseStatusException(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다.");
    }
}

package com.github.gajicoding.todo_api_project.api.v2.controller;

import com.github.gajicoding.todo_api_project.api.v2.data.dto.AuthorRequestDTO;
import com.github.gajicoding.todo_api_project.api.v2.data.dto.AuthorResponseDTO;
import com.github.gajicoding.todo_api_project.api.v2.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/authors")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    public ResponseEntity<AuthorResponseDTO> save(@RequestBody AuthorRequestDTO req) {
        return new ResponseEntity<>(authorService.addAuthor(req), HttpStatus.CREATED);
    }
}

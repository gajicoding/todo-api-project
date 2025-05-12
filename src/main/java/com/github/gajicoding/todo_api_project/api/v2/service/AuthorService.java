package com.github.gajicoding.todo_api_project.api.v2.service;

import com.github.gajicoding.todo_api_project.api.v2.data.dto.AuthorRequestDTO;
import com.github.gajicoding.todo_api_project.api.v2.data.dto.AuthorResponseDTO;

public interface AuthorService {
    AuthorResponseDTO addAuthor(AuthorRequestDTO req);

}

package com.github.gajicoding.todo_api_project.api.v2.service;

import com.github.gajicoding.todo_api_project.api.v2.data.dto.AuthorRequestDTO;
import com.github.gajicoding.todo_api_project.api.v2.data.dto.AuthorResponseDTO;
import com.github.gajicoding.todo_api_project.api.v2.data.entity.Author;
import com.github.gajicoding.todo_api_project.api.v2.repository.AuthorRepository;
import com.github.gajicoding.todo_api_project.common.security.PasswordEncryptor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final PasswordEncryptor passwordEncryptor;

    public AuthorServiceImpl(AuthorRepository authorRepository, PasswordEncryptor passwordEncryptor) {
        this.authorRepository = authorRepository;
        this.passwordEncryptor = passwordEncryptor;
    }

    @Override
    @Transactional
    public AuthorResponseDTO addAuthor(AuthorRequestDTO req) {
        if(authorRepository.existsByEmail(req.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다.");
        }

        req.setPassword(passwordEncryptor.encode(req.getPassword()));
        Author reqAuthor = req.toEntity();

        long id = authorRepository.addAuthor(reqAuthor);
        Author resAuthor = findAuthorById(id);

        return new AuthorResponseDTO(resAuthor);
    }

    private Author findAuthorById(Long id){
        return authorRepository.findAuthorById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 작성자가 존재하지 않습니다."));
    }
}

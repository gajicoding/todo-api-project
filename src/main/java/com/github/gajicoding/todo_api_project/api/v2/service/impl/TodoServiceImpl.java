package com.github.gajicoding.todo_api_project.api.v2.service.impl;

import com.github.gajicoding.todo_api_project.api.v2.data.dto.TodoRequestDTO;
import com.github.gajicoding.todo_api_project.api.v2.data.dto.TodoResponseDTO;
import com.github.gajicoding.todo_api_project.api.v2.data.dto.TodoSearchParameters;
import com.github.gajicoding.todo_api_project.api.v2.data.entity.Author;
import com.github.gajicoding.todo_api_project.api.v2.data.entity.Todo;
import com.github.gajicoding.todo_api_project.api.v2.exception.AuthorExceptions;
import com.github.gajicoding.todo_api_project.api.v2.exception.TodoExceptions;
import com.github.gajicoding.todo_api_project.api.v2.repository.AuthorRepository;
import com.github.gajicoding.todo_api_project.api.v2.repository.TodoRepository;
import com.github.gajicoding.todo_api_project.api.v2.service.TodoService;
import com.github.gajicoding.todo_api_project.common.dto.PageResponseDTO;
import com.github.gajicoding.todo_api_project.common.security.PasswordEncryptor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("todoServiceV2")
public class TodoServiceImpl implements TodoService {
    private final TodoRepository todoRepository;
    private final AuthorRepository authorRepository;
    private final PasswordEncryptor passwordEncryptor;

    public TodoServiceImpl(TodoRepository todoRepository, AuthorRepository authorRepository, PasswordEncryptor passwordEncryptor) {
        this.todoRepository = todoRepository;
        this.authorRepository = authorRepository;
        this.passwordEncryptor = passwordEncryptor;
    }

    @Override
    @Transactional
    public TodoResponseDTO saveTodo(TodoRequestDTO req) {
        Todo reqTodo = req.toEntity();

        if(!authorRepository.existsById(req.getAuthorId())){
            throw AuthorExceptions.notFound();
        }

        long id = todoRepository.saveTodo(reqTodo);

        Todo resTodo = getTodoById(id);
        return new TodoResponseDTO(resTodo);
    }

    @Override
    public PageResponseDTO<TodoResponseDTO> findTodosBySearchParams(TodoSearchParameters searchParams) {
        long totalElements = todoRepository.countTodosBySearchParams(searchParams);

        List<Todo> resTodos = todoRepository.findTodosBySearchParamsWithPagination(searchParams);

        List<TodoResponseDTO> resTodosDTO = resTodos.stream()
                .map(TodoResponseDTO::new)
                .toList();

        return new PageResponseDTO<>(resTodosDTO, searchParams.getPage(), searchParams.getSize(), totalElements);
    }

    @Override
    public TodoResponseDTO findTodoById(Long id) {
        Todo resTodo = getTodoById(id);
        return new TodoResponseDTO(resTodo);
    }

    @Override
    public TodoResponseDTO updateTodo(Long id, TodoRequestDTO req) {
        getTodoById(id);
        passwordCheck(req.getAuthorId(), req);

        Todo reqTodo = req.toEntity();

        int updatedRow = todoRepository.updateTodo(id, reqTodo);
        if (updatedRow == 0) {
            throw TodoExceptions.notChanged();
        }

        Todo resTodo = todoRepository.findTodoById(id).orElseThrow(TodoExceptions::notFound);
        return new TodoResponseDTO(resTodo);
    }

    @Override
    public void deleteTodo(Long id, TodoRequestDTO req) {
        getTodoById(id);
        passwordCheck(req.getAuthorId(), req);

        int deletedRow = todoRepository.deleteTodo(id);
        if (deletedRow == 0) {
            throw TodoExceptions.notFound();
        }
    }

    private Todo getTodoById(Long id){
        return todoRepository.findTodoById(id)
                .orElseThrow(TodoExceptions::notFound);
    }

    private void passwordCheck(Long id, TodoRequestDTO req){
        Author author = authorRepository.findAuthorById(id).orElseThrow(AuthorExceptions::notFound);

        if(!passwordMatches(req.getPassword(), author.getPassword())){
            throw AuthorExceptions.invalidPassword();
        }
    }

    private boolean passwordMatches(String password, String encodedPassword) {
        return password != null && passwordEncryptor.matches(password, encodedPassword);
    }
}

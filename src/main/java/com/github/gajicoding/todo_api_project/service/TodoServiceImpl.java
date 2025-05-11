package com.github.gajicoding.todo_api_project.service;

import com.github.gajicoding.todo_api_project.common.security.PasswordEncryptor;
import com.github.gajicoding.todo_api_project.data.dto.TodoRequestDTO;
import com.github.gajicoding.todo_api_project.data.dto.TodoResponseDTO;
import com.github.gajicoding.todo_api_project.data.dto.TodoSearchParameters;
import com.github.gajicoding.todo_api_project.data.entity.Todo;
import com.github.gajicoding.todo_api_project.repository.TodoRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TodoServiceImpl implements TodoService {
    private final TodoRepository todoRepository;
    private final PasswordEncryptor passwordEncryptor;

    public TodoServiceImpl(TodoRepository todoRepository, PasswordEncryptor passwordEncryptor) {
        this.todoRepository = todoRepository;
        this.passwordEncryptor = passwordEncryptor;
    }

    @Override
    @Transactional
    public TodoResponseDTO saveTodo(TodoRequestDTO req) {
        req.setPassword(passwordEncryptor.encode(req.getPassword()));
        Todo todo = req.toEntity();

        long id = todoRepository.saveTodo(todo);

        Todo res = todoRepository.findTodoByIdOrElseThrow(id);
        return new TodoResponseDTO(res);
    }

    @Override
    public List<TodoResponseDTO> findTodosBySearchParams(TodoSearchParameters searchParams) {
        List<Todo> todoList = todoRepository.findTodosBySearchParams(searchParams);

        return todoList.stream()
                .map(TodoResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public TodoResponseDTO findTodoById(Long id) {
        Todo todo = todoRepository.findTodoByIdOrElseThrow(id);
        return new TodoResponseDTO(todo);
    }
}

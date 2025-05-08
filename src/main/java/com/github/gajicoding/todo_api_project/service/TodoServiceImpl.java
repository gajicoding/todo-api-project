package com.github.gajicoding.todo_api_project.service;

import com.github.gajicoding.todo_api_project.dto.TodoRequestDTO;
import com.github.gajicoding.todo_api_project.dto.TodoResponseDTO;
import com.github.gajicoding.todo_api_project.dto.TodoSearchParameters;
import com.github.gajicoding.todo_api_project.entity.Todo;
import com.github.gajicoding.todo_api_project.repository.TodoRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TodoServiceImpl implements TodoService {
    private final TodoRepository todoRepository;

    public TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    @Transactional
    public TodoResponseDTO saveTodo(TodoRequestDTO req) {
        Todo todo = req.toEntity();

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(req.getPassword());
        todo.setPassword(encodedPassword);

        long id = todoRepository.saveTodo(todo);

        return new TodoResponseDTO(todoRepository.findTodoByIdOrElseThrow(id));
    }

    @Override
    public List<TodoResponseDTO> findAllMemos(TodoSearchParameters searchParams) {
        List<Todo> todoList = todoRepository.findAllTodos();

        if (searchParams.getAuthor() != null) {
            todoList = todoList.stream()
                    .filter(todo -> todo.getAuthor().contains(searchParams.getAuthor()))
                    .collect(Collectors.toList());
        }

        if (searchParams.getUpdatedAt() != null) {
            todoList = todoList.stream()
                    .filter(todo -> todo.getUpdatedAt().toLocalDate().equals(searchParams.getUpdatedAt()))
                    .collect(Collectors.toList());
        }

        return todoList.stream()
                .map(TodoResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public TodoResponseDTO findTodoById(Long id) {
        Todo optionalTodo = todoRepository.findTodoByIdOrElseThrow(id);
        return new TodoResponseDTO(optionalTodo);
    }
}

package com.github.gajicoding.todo_api_project.api.v1.service;

import com.github.gajicoding.todo_api_project.api.v1.data.dto.TodoRequestDTO;
import com.github.gajicoding.todo_api_project.api.v1.data.dto.TodoResponseDTO;
import com.github.gajicoding.todo_api_project.api.v1.data.dto.TodoSearchParameters;

import java.util.List;

public interface TodoService {
    TodoResponseDTO saveTodo(TodoRequestDTO req);

    List<TodoResponseDTO> findTodosBySearchParams(TodoSearchParameters todoSearchParameters);

    TodoResponseDTO findTodoById(Long id);

    TodoResponseDTO updateTodo(Long id, TodoRequestDTO req);

    void deleteTodo(Long id, TodoRequestDTO req);
}

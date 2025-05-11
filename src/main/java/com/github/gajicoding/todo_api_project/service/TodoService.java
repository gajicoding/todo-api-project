package com.github.gajicoding.todo_api_project.service;

import com.github.gajicoding.todo_api_project.data.dto.TodoRequestDTO;
import com.github.gajicoding.todo_api_project.data.dto.TodoResponseDTO;
import com.github.gajicoding.todo_api_project.data.dto.TodoSearchParameters;

import java.util.List;

public interface TodoService {
    TodoResponseDTO saveTodo(TodoRequestDTO req);

    List<TodoResponseDTO> findTodosBySearchParams(TodoSearchParameters todoSearchParameters);

    TodoResponseDTO findTodoById(Long id);

    TodoResponseDTO updateTodo(Long id, TodoRequestDTO req);

    void deleteTodo(Long id, TodoRequestDTO req);
}

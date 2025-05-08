package com.github.gajicoding.todo_api_project.service;

import com.github.gajicoding.todo_api_project.dto.TodoRequestDTO;
import com.github.gajicoding.todo_api_project.dto.TodoResponseDTO;
import com.github.gajicoding.todo_api_project.dto.TodoSearchParameters;

import java.util.List;

public interface TodoService {
    TodoResponseDTO saveTodo(TodoRequestDTO req);

    List<TodoResponseDTO> findAllMemos(TodoSearchParameters todoSearchParameters);

    TodoResponseDTO findTodoById(Long id);
}

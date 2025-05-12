package com.github.gajicoding.todo_api_project.api.v2.service;

import com.github.gajicoding.todo_api_project.api.v2.data.dto.TodoRequestDTO;
import com.github.gajicoding.todo_api_project.api.v2.data.dto.TodoResponseDTO;
import com.github.gajicoding.todo_api_project.api.v2.data.dto.TodoSearchParameters;
import com.github.gajicoding.todo_api_project.common.dto.PageResponseDTO;

public interface TodoService {
    TodoResponseDTO saveTodo(TodoRequestDTO req);

    PageResponseDTO<TodoResponseDTO> findTodosBySearchParams(TodoSearchParameters searchParams);

    TodoResponseDTO findTodoById(Long id);

    TodoResponseDTO updateTodo(Long id, TodoRequestDTO req);

    void deleteTodo(Long id, TodoRequestDTO req);
}

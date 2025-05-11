package com.github.gajicoding.todo_api_project.repository;

import com.github.gajicoding.todo_api_project.data.dto.TodoResponseDTO;
import com.github.gajicoding.todo_api_project.data.dto.TodoSearchParameters;
import com.github.gajicoding.todo_api_project.data.entity.Todo;

import java.util.List;

public interface TodoRepository {
    long saveTodo(Todo todo);

    Todo findTodoByIdOrElseThrow(long id);

    List<Todo> findAllTodos();

    List<Todo> findTodosBySearchParams(TodoSearchParameters searchParams);
}

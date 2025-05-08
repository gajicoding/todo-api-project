package com.github.gajicoding.todo_api_project.repository;

import com.github.gajicoding.todo_api_project.entity.Todo;

import java.util.List;

public interface TodoRepository {
    long saveTodo(Todo todo);

    Todo findTodoByIdOrElseThrow(long id);

    List<Todo> findAllTodos();
}

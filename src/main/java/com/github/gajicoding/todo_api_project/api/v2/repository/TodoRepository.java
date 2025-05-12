package com.github.gajicoding.todo_api_project.api.v2.repository;

import com.github.gajicoding.todo_api_project.api.v2.data.dto.TodoSearchParameters;
import com.github.gajicoding.todo_api_project.api.v2.data.entity.Todo;

import java.util.List;
import java.util.Optional;

public interface TodoRepository {
    long saveTodo(Todo reqTodo);

    Optional<Todo> findTodoById(long id);

    Long countTodosBySearchParams(TodoSearchParameters searchParams);

    List<Todo> findTodosBySearchParamsWithPagination(TodoSearchParameters searchParams);

    int updateTodo(Long id, Todo reqTodo);

    int deleteTodo(Long id);

}

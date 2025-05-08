package com.github.gajicoding.todo_api_project.controller;

import com.github.gajicoding.todo_api_project.dto.TodoRequestDTO;
import com.github.gajicoding.todo_api_project.dto.TodoResponseDTO;
import com.github.gajicoding.todo_api_project.dto.TodoSearchParameters;
import com.github.gajicoding.todo_api_project.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping
    public ResponseEntity<TodoResponseDTO> saveTodo(@RequestBody TodoRequestDTO req) {
        return new ResponseEntity<>(todoService.saveTodo(req), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TodoResponseDTO>> findAllTodos(
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String updatedAt
    ) {
        TodoSearchParameters searchParams = new TodoSearchParameters(author, updatedAt);
        return new ResponseEntity<>(todoService.findAllMemos(searchParams), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoResponseDTO> findTodoById(@PathVariable Long id) {
        return new ResponseEntity<>(todoService.findTodoById(id), HttpStatus.OK);
    }
}

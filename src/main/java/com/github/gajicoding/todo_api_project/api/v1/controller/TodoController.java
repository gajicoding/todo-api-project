package com.github.gajicoding.todo_api_project.api.v1.controller;

import com.github.gajicoding.todo_api_project.api.v1.data.dto.TodoRequestDTO;
import com.github.gajicoding.todo_api_project.api.v1.data.dto.TodoResponseDTO;
import com.github.gajicoding.todo_api_project.api.v1.data.dto.TodoSearchParameters;
import com.github.gajicoding.todo_api_project.api.v1.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/todos")
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
    public ResponseEntity<List<TodoResponseDTO>> findTodosBySearchParams(
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String updatedAt
    ) {
        TodoSearchParameters searchParams = new TodoSearchParameters(author, updatedAt);
        return new ResponseEntity<>(todoService.findTodosBySearchParams(searchParams), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoResponseDTO> findTodoById(@PathVariable Long id) {
        return new ResponseEntity<>(todoService.findTodoById(id), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TodoResponseDTO> updateTodo(@PathVariable Long id, @RequestBody TodoRequestDTO req) {
        return new ResponseEntity<>(todoService.updateTodo(id, req), HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id, @RequestBody TodoRequestDTO req) {
        todoService.deleteTodo(id, req);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

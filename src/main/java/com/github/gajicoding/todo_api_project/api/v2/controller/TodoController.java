package com.github.gajicoding.todo_api_project.api.v2.controller;

import com.github.gajicoding.todo_api_project.api.v2.data.dto.TodoSearchParameters;
import com.github.gajicoding.todo_api_project.api.v2.data.dto.TodoRequestDTO;
import com.github.gajicoding.todo_api_project.api.v2.data.dto.TodoResponseDTO;
import com.github.gajicoding.todo_api_project.api.v2.service.TodoService;
import com.github.gajicoding.todo_api_project.api.v2.validation.CreateGroup;
import com.github.gajicoding.todo_api_project.api.v2.validation.DeleteGroup;
import com.github.gajicoding.todo_api_project.api.v2.validation.UpdateGroup;
import com.github.gajicoding.todo_api_project.common.dto.PageResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;


@RestController("todoControllerV2")
@RequestMapping("/api/v2/todos")
public class TodoController {
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping
    public ResponseEntity<TodoResponseDTO> saveTodo(@RequestBody @Validated(CreateGroup.class) TodoRequestDTO req){
        System.out.println(req);
        return new ResponseEntity<>(todoService.saveTodo(req), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PageResponseDTO<TodoResponseDTO>> findTodosBySearchParams(
            @RequestParam(required = false) Long authorId,
            @RequestParam(required = false) String updatedAt,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        TodoSearchParameters searchParams = new TodoSearchParameters(authorId, updatedAt, page, size);
        return new ResponseEntity<>(todoService.findTodosBySearchParams(searchParams), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoResponseDTO> findTodoById(@PathVariable Long id) {
        return new ResponseEntity<>(todoService.findTodoById(id), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TodoResponseDTO> updateTodo(@PathVariable Long id, @RequestBody @Validated(UpdateGroup.class) TodoRequestDTO req) {
        return new ResponseEntity<>(todoService.updateTodo(id, req), HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Object> deleteTodo(@PathVariable Long id, @RequestBody @Validated(DeleteGroup.class) TodoRequestDTO req) {
        todoService.deleteTodo(id, req);
        return new ResponseEntity<>(Collections.singletonMap("message", "일정이 성공적으로 삭제되었습니다."), HttpStatus.OK);
    }

}

package com.github.gajicoding.todo_api_project.api.v1.service;

import com.github.gajicoding.todo_api_project.common.security.PasswordEncryptor;
import com.github.gajicoding.todo_api_project.api.v1.data.dto.TodoRequestDTO;
import com.github.gajicoding.todo_api_project.api.v1.data.dto.TodoResponseDTO;
import com.github.gajicoding.todo_api_project.api.v1.data.dto.TodoSearchParameters;
import com.github.gajicoding.todo_api_project.api.v1.data.entity.Todo;
import com.github.gajicoding.todo_api_project.api.v1.repository.TodoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TodoServiceImpl implements TodoService {
    private final TodoRepository todoRepository;
    private final PasswordEncryptor passwordEncryptor;

    public TodoServiceImpl(TodoRepository todoRepository, PasswordEncryptor passwordEncryptor) {
        this.todoRepository = todoRepository;
        this.passwordEncryptor = passwordEncryptor;
    }

    @Override
    @Transactional
    public TodoResponseDTO saveTodo(TodoRequestDTO req) {
        req.setPassword(passwordEncryptor.encode(req.getPassword()));
        Todo reqTodo = req.toEntity();

        long id = todoRepository.saveTodo(reqTodo);

        Todo resTodo = todoRepository.findTodoById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 일정이 존재하지 않습니다."));
        return new TodoResponseDTO(resTodo);
    }

    @Override
    public List<TodoResponseDTO> findTodosBySearchParams(TodoSearchParameters searchParams) {
        List<Todo> resTodos = todoRepository.findTodosBySearchParams(searchParams);

        return resTodos.stream()
                .map(TodoResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public TodoResponseDTO findTodoById(Long id) {
        Todo resTodo = todoRepository.findTodoById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 일정이 존재하지 않습니다."));
        return new TodoResponseDTO(resTodo);
    }

    @Override
    public TodoResponseDTO updateTodo(Long id, TodoRequestDTO req) {
        passwordCheck(id, req);

        Todo reqTodo = req.toEntity();

        int updatedRow = todoRepository.updateTodo(id, reqTodo);
        if (updatedRow == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "변경된 내용이 없습니다.");
        }

       Todo resTodo = todoRepository.findTodoById(id)
               .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 일정이 존재하지 않습니다."));
       return new TodoResponseDTO(resTodo);
    }

    @Override
    public void deleteTodo(Long id, TodoRequestDTO req) {
        passwordCheck(id, req);

        int deletedRow = todoRepository.deleteTodo(id);
        if (deletedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 일정이 존재하지 않습니다.");
        }

    }

    private void passwordCheck(Long id, TodoRequestDTO req){
        Todo originalTodo = todoRepository.findTodoById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 일정이 존재하지 않습니다."));

        if(!passwordMatches(req.getPassword(), originalTodo.getPassword())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "비밀번호가 올바르지 않습니다.");
        }
    }

    private boolean passwordMatches(String password, String encodedPassword) {
        return password != null && passwordEncryptor.matches(password, encodedPassword);
    }
}

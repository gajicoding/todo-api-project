package com.github.gajicoding.todo_api_project.repository;

import com.github.gajicoding.todo_api_project.common.security.PasswordEncryptor;
import com.github.gajicoding.todo_api_project.entity.Todo;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TodoRepositoryImpl implements TodoRepository {
    private final JdbcTemplate jdbcTemplate;

    private PasswordEncryptor passwordEncryptor;

    public TodoRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.passwordEncryptor = new PasswordEncryptor();
    }

    @Override
    public long saveTodo(Todo todo) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("todos")
                .usingGeneratedKeyColumns("id")
                .usingColumns("title", "contents", "author", "password");

        String encodedPassword = passwordEncryptor.encode(todo.getPassword());

        Map<String, Object> parameters = new HashMap<>() {{
            put("title", todo.getTitle());
            put("contents", todo.getContents());
            put("author", todo.getAuthor());
            put("password", encodedPassword);
        }};

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return key.longValue();
    }

    @Override
    public Todo findTodoByIdOrElseThrow(long id) {
        List<Todo> result = jdbcTemplate.query("SELECT * FROM todos WHERE id = ?", todoRowMapper(), id);

        System.out.println(result);
        return result.stream().findFirst().orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 일정이 존재하지 않습니다."));
    }

    @Override
    public List<Todo> findAllTodos() {
        return jdbcTemplate.query("SELECT * FROM todos ORDER BY updated_at DESC", todoRowMapper());
    }

    private RowMapper<Todo> todoRowMapper() {
        return (rs, rowNum) -> new Todo(
                rs.getLong("id"),
                rs.getString("title"),
                rs.getString("contents"),
                rs.getString("author"),
                rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null,
                rs.getTimestamp("updated_at") != null ? rs.getTimestamp("updated_at").toLocalDateTime() : null
        );
    }
}

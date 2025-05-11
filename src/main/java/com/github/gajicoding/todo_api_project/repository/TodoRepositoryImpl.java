package com.github.gajicoding.todo_api_project.repository;

import com.github.gajicoding.todo_api_project.common.security.PasswordEncryptor;
import com.github.gajicoding.todo_api_project.data.dto.TodoResponseDTO;
import com.github.gajicoding.todo_api_project.data.dto.TodoSearchParameters;
import com.github.gajicoding.todo_api_project.data.entity.Todo;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TodoRepositoryImpl implements TodoRepository {
    private final JdbcTemplate jdbcTemplate;

    public TodoRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public long saveTodo(Todo todo) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("todos")
                .usingGeneratedKeyColumns("id")
                .usingColumns("title", "contents", "author", "password");

        Map<String, Object> parameters = new HashMap<>() {{
            put("title", todo.getTitle());
            put("contents", todo.getContents());
            put("author", todo.getAuthor());
            put("password", todo.getAuthor());
        }};

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return key.longValue();
    }

    @Override
    public Todo findTodoByIdOrElseThrow(long id) {
        return jdbcTemplate.query("SELECT * FROM todos WHERE id = ?", todoRowMapper(), id).stream().findAny().orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 일정이 존재하지 않습니다."));
    }

    @Override
    public List<Todo> findAllTodos() {
        return jdbcTemplate.query("SELECT * FROM todos ORDER BY updated_at DESC", todoRowMapper());
    }

    @Override
    public List<Todo> findTodosBySearchParams(TodoSearchParameters searchParams) {
        StringBuilder sql = new StringBuilder("SELECT * FROM todos WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (searchParams.getAuthor() != null) {
            sql.append(" AND author LIKE ?");
            params.add("%" + searchParams.getAuthor() + "%");
        }

        if (searchParams.getUpdatedAt() != null) {
            sql.append(" AND DATE(updated_at) = ?");
            params.add(searchParams.getUpdatedAt());
        }

        sql.append(" ORDER BY updated_at DESC");

        return jdbcTemplate.query(sql.toString(), todoRowMapper(), params.toArray());
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

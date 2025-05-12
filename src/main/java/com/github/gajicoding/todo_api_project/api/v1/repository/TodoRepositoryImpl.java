package com.github.gajicoding.todo_api_project.api.v1.repository;

import com.github.gajicoding.todo_api_project.api.v1.data.dto.TodoSearchParameters;
import com.github.gajicoding.todo_api_project.api.v1.data.entity.Todo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class TodoRepositoryImpl implements TodoRepository {
    private final JdbcTemplate jdbcTemplate;

    public TodoRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public long saveTodo(Todo reqTodo) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("todos")
                .usingGeneratedKeyColumns("id")
                .usingColumns("title", "contents", "author", "password");

        Map<String, Object> parameters = new HashMap<>() {{
            put("title", reqTodo.getTitle());
            put("contents", reqTodo.getContents());
            put("author", reqTodo.getAuthor());
            put("password", reqTodo.getPassword());
        }};

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return key.longValue();
    }

    @Override
    public Optional<Todo> findTodoById(long id) {
        return jdbcTemplate.query("SELECT * FROM todos WHERE id = ?", todoRowMapper(), id).stream().findAny();
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

    @Override
    public int updateTodo(Long id, Todo reqTodo) {
        StringBuilder sql = new StringBuilder("UPDATE todos SET");
        List<Object> params = new ArrayList<>();

        if (reqTodo.getTitle() != null) {
            sql.append(" title = ?, ");
            params.add(reqTodo.getTitle());
        }

        if (reqTodo.getContents() != null) {
            sql.append(" contents = ?, ");
            params.add(reqTodo.getContents());
        }

        if (reqTodo.getAuthor() != null) {
            sql.append(" author = ?, ");
            params.add(reqTodo.getAuthor());
        }

        if (params.isEmpty()) {
            return 0;
        }

        sql.setLength(sql.length() - 2);
        sql.append(" WHERE id = ?");
        params.add(id);

        return jdbcTemplate.update(sql.toString(), params.toArray());
    }

    @Override
    public int deleteTodo(Long id) {
        return jdbcTemplate.update("DELETE FROM todos WHERE id = ?", id);
    }

    private RowMapper<Todo> todoRowMapper() {
        return (rs, rowNum) -> new Todo(
                rs.getLong("id"),
                rs.getString("title"),
                rs.getString("contents"),
                rs.getString("author"),
                rs.getString("password"),
                rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null,
                rs.getTimestamp("updated_at") != null ? rs.getTimestamp("updated_at").toLocalDateTime() : null
        );
    }
}

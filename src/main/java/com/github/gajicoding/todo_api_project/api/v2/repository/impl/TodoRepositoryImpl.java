package com.github.gajicoding.todo_api_project.api.v2.repository.impl;


import com.github.gajicoding.todo_api_project.api.v2.data.dto.TodoSearchParameters;
import com.github.gajicoding.todo_api_project.api.v2.data.entity.Author;
import com.github.gajicoding.todo_api_project.api.v2.data.entity.Todo;
import com.github.gajicoding.todo_api_project.api.v2.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository("todoRepositoryV2")
public class TodoRepositoryImpl implements TodoRepository {
    private final JdbcTemplate jdbcTemplate;

    public TodoRepositoryImpl(@Qualifier("todoV2DataSource") DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public long saveTodo(Todo reqTodo) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("todos")
                .usingGeneratedKeyColumns("id")
                .usingColumns("title", "contents", "author_id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("title", reqTodo.getTitle());
        parameters.put("contents", reqTodo.getContents());
        parameters.put("author_id", reqTodo.getAuthor().getId());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return key.longValue();
    }

    @Override
    public Optional<Todo> findTodoById(long id) {
        return jdbcTemplate.query("SELECT * FROM todo_with_author WHERE todo_id = ?", todoRowMapper(), id).stream().findAny();
    }

    @Override
    public Long countTodosBySearchParams(TodoSearchParameters searchParams) {
        String sql = createSql("SELECT COUNT(*)", searchParams, false);
        Object[] params = createParams(searchParams, false);

        return jdbcTemplate.queryForObject(sql, Long.class, params);
    }

    @Override
    public List<Todo> findTodosBySearchParamsWithPagination(TodoSearchParameters searchParams) {
        String sql = createSql("SELECT *", searchParams, true);
        Object[] params = createParams(searchParams, true);

        return jdbcTemplate.query(sql, todoRowMapper(), params);
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

    private String createSql(String baseSql, TodoSearchParameters searchParams, boolean withPagination) {
        StringBuilder sql = new StringBuilder(baseSql + " FROM todo_with_author WHERE 1=1");

        if (searchParams.getAuthorId() != null) {
            sql.append(" AND author_id = ?");
        }

        if (searchParams.getUpdatedAt() != null) {
            sql.append(" AND DATE(updated_at) = ?");
        }

        if (withPagination) {
            sql.append(" ORDER BY updated_at DESC LIMIT ? OFFSET ?");
        }

        return sql.toString();
    }

    private Object[] createParams(TodoSearchParameters searchParams, boolean withPagination) {
        List<Object> params = new ArrayList<>();

        if (searchParams.getAuthorId() != null) {
            params.add(searchParams.getAuthorId());
        }

        if (searchParams.getUpdatedAt() != null) {
            params.add(searchParams.getUpdatedAt());
        }

        if (withPagination) {
            params.add(searchParams.getSize());
            params.add((searchParams.getPage() - 1) * searchParams.getSize());
        }

        return params.toArray();
    }

    private RowMapper<Todo> todoRowMapper() {
        return (rs, rowNum) -> {
            Author author = new Author(
                    rs.getLong("author_id"),
                    rs.getString("author_name"),
                    rs.getString("author_email"),
                    rs.getTimestamp("author_created_at") != null ? rs.getTimestamp("author_created_at").toLocalDateTime() : null,
                    rs.getTimestamp("author_updated_at") != null ? rs.getTimestamp("author_updated_at").toLocalDateTime() : null
            );

            return new Todo(
                    rs.getLong("todo_id"),
                    rs.getString("title"),
                    rs.getString("contents"),
                    author,
                    rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null,
                    rs.getTimestamp("updated_at") != null ? rs.getTimestamp("updated_at").toLocalDateTime() : null
            );
        };
    }
}

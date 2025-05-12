package com.github.gajicoding.todo_api_project.api.v2.repository.impl;

import com.github.gajicoding.todo_api_project.api.v2.data.entity.Author;
import com.github.gajicoding.todo_api_project.api.v2.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Repository
public class AuthorRepositoryImpl implements AuthorRepository {
    private final JdbcTemplate jdbcTemplate;

    public AuthorRepositoryImpl(@Qualifier("todoV2DataSource") DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public boolean existsByEmail(String email) {
        return Objects.requireNonNullElse(jdbcTemplate.queryForObject("SELECT COUNT(*) FROM authors WHERE email = ?", Long.class, email), 0L) > 0;
    }

    @Override
    public boolean existsById(Long id) {
        return Objects.requireNonNullElse(jdbcTemplate.queryForObject("SELECT COUNT(*) FROM authors WHERE id = ?", Long.class, id), 0L) > 0;
    }

    @Override
    public long addAuthor(Author reqAuthor) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("authors")
                .usingGeneratedKeyColumns("id")
                .usingColumns("name", "email", "password");

        Map<String, Object> parameters = new HashMap<>() {{
            put("name", reqAuthor.getName());
            put("email", reqAuthor.getEmail());
            put("password", reqAuthor.getPassword());
        }};

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return key.longValue();
    }

    @Override
    public Optional<Author> findAuthorById(Long id) {
        return jdbcTemplate.query("SELECT * FROM authors WHERE id = ?", authorRowMapper(), id).stream().findAny();
    }

    private RowMapper<Author> authorRowMapper() {
        return (rs, rowNum) -> new Author(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null,
                rs.getTimestamp("updated_at") != null ? rs.getTimestamp("updated_at").toLocalDateTime() : null
        );
    }
}

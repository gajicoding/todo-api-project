package com.github.gajicoding.todo_api_project.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "spring.datasource.todo-v2")
public class TodoV2DataSourceProperties {
    private String jdbcUrl;
    private String username;
    private String password;
    private String driverClassName;
}
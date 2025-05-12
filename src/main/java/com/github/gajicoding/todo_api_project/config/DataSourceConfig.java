package com.github.gajicoding.todo_api_project.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties({TodoDataSourceProperties.class, TodoV2DataSourceProperties.class})
public class DataSourceConfig {
    @Primary
    @Bean(name = "todoDataSource")
    public DataSource todoDataSource(TodoDataSourceProperties props) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(props.getJdbcUrl());
        dataSource.setUsername(props.getUsername());
        dataSource.setPassword(props.getPassword());
        dataSource.setDriverClassName(props.getDriverClassName());
        return dataSource;
    }

    @Bean(name = "todoV2DataSource")
    public DataSource todoV2DataSource(TodoV2DataSourceProperties props) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(props.getJdbcUrl());
        dataSource.setUsername(props.getUsername());
        dataSource.setPassword(props.getPassword());
        dataSource.setDriverClassName(props.getDriverClassName());
        return dataSource;
    }
}

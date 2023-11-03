package com.spg.applicationTask.api.config;

import com.spg.applicationTask.engine.IoC.annotation.ComponentFactory;
import com.spg.applicationTask.engine.IoC.annotation.Configuration;
import com.spg.applicationTask.engine.IoC.annotation.Value;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

@Configuration
public class BDConfiguration {

    @Value("db.url")
    private String jdbcUrl;
    @Value("db.username")
    private String user;
    @Value("db.password")
    private String password;
    @Value("db.sqlDriver")
    private String driverClass;

    @ComponentFactory
    public DataSource getDataSource() {
        final HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setMaximumPoolSize(Runtime.getRuntime().availableProcessors() * 2 + 1);
        dataSource.addDataSourceProperty("user", user);
        dataSource.addDataSourceProperty("password", password);
        return dataSource;
    }
}

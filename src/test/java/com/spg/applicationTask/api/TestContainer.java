package com.spg.applicationTask.api;

import com.spg.applicationTask.Main;
import com.spg.applicationTask.engine.IoC.Application;
import com.spg.applicationTask.engine.IoC.ApplicationContext;
import com.spg.applicationTask.engine.web.ServerConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import static com.spg.applicationTask.api.TestContainer.InitDB.createDb;
import static com.spg.applicationTask.api.TestContainer.InitDB.destroyDb;
import static com.spg.applicationTask.api.TestContainer.InitDB.insertDb;
import static com.spg.applicationTask.engine.web.ServerConfig.Property.HOST;
import static com.spg.applicationTask.engine.web.ServerConfig.Property.PORT;

@Testcontainers
public class TestContainer extends PostgreSQLContainer<TestContainer> {

    private static TestContainer container;
    public static ApplicationContext context;

    private TestContainer() {
        super("postgres:latest");
    }

    @Override
    public void start() {
        super.start();
        context = Application.run(Main.class);
        HikariDataSource dataSource = (HikariDataSource) context.getObject(DataSource.class);
        ServerConfig serverConfig = context.getObject(ServerConfig.class);
        setServerSettings(serverConfig);
        setDataBaseSettings(dataSource);
        initDB(dataSource);
    }

    public static TestContainer getContainer() {
        if (container == null) {
            container = new TestContainer();
        }
        return container;
    }

    private void initDB(DataSource dataSource) {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            statement.execute(destroyDb);
            statement.execute(createDb);
            statement.executeUpdate(insertDb);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setDataBaseSettings(HikariDataSource dataSource) {
        dataSource.setJdbcUrl(container.getJdbcUrl());
        dataSource.addDataSourceProperty("user", container.getUsername());
        dataSource.addDataSourceProperty("password", container.getPassword());
    }

    private void setServerSettings(ServerConfig serverConfig) {
        serverConfig.setProperties(Map.of(HOST, "localhost", PORT, "8000"));
    }

    static class InitDB {
        public static final String destroyDb =
                "DROP TABLE IF EXISTS PROJECT_TASKS;" +
                        "DROP TABLE IF EXISTS TASKS;" +
                        "DROP TABLE IF EXISTS USERS;" +
                        "DROP TABLE IF EXISTS PROJECTS;";

        public static final String createDb = """
                CREATE TABLE IF NOT EXISTS PROJECTS
                (ID SERIAL NOT NULL PRIMARY KEY,
                NAME VARCHAR(255) NOT NULL UNIQUE,
                DESCRIPTION TEXT NOT NULL);

                CREATE TABLE IF NOT EXISTS USERS
                (ID SERIAL NOT NULL PRIMARY KEY,
                FIRSTNAME  VARCHAR(255) NOT NULL,
                LASTNAME   VARCHAR(255) NOT NULL,
                EMAIL      VARCHAR(255) UNIQUE,
                PROJECT_ID INTEGER,
                FOREIGN KEY (PROJECT_ID) REFERENCES PROJECTS (ID) ON DELETE SET NULL);

                CREATE TABLE IF NOT EXISTS TASKS
                (ID SERIAL NOT NULL PRIMARY KEY,
                TITLE VARCHAR(255) NOT NULL,
                DESCRIPTION  TEXT         NOT NULL,
                CREATED_DATE TIMESTAMPTZ  NOT NULL DEFAULT current_timestamp ,
                DUE_DATE TIMESTAMPTZ NOT NULL,
                STATUS VARCHAR NOT NULL,
                ASSIGNEE_ID INTEGER REFERENCES USERS (ID) ON DELETE SET NULL,
                PROJECT_ID INTEGER REFERENCES PROJECTS (ID) ON DELETE CASCADE);""";

        public static final String insertDb = """
            INSERT INTO PROJECTS (NAME, DESCRIPTION) VALUES
                    ('PROJECT1', 'PROJECT1'),
                    ('PROJECT2', 'PROJECT2'),
                    ('PROJECT3', 'PROJECT3'),
                    ('PROJECT4', 'PROJECT4');

                    INSERT INTO USERS (FIRSTNAME, LASTNAME, EMAIL, PROJECT_ID)VALUES
                    ('USER1', 'USER1', 'USER1@gmail.com', 1),
                    ('USER2', 'USER2', 'USER2@gmail.com', null),
                    ('USER3', 'USER3', 'USER3@gmail.com', 2),
                    ('USER4', 'USER4', 'USER4@gmail.com', null),
                    ('USER5', 'USER5', 'USER5@gmail.com', 3),
                    ('USER6', 'USER6', 'USER6@gmail.com', 4);

                    INSERT INTO TASKS (title, DESCRIPTION, CREATED_DATE,
                    DUE_DATE, STATUS, ASSIGNEE_ID, PROJECT_ID) VALUES
                    ('TASK1', 'create 1', '2023-10-19 13:52:29.697271 +00:00', '2023-10-19 13:52:29.697271 +00:00', 'IN PROGRESS', 1, null),
                    ('TASK2', 'create 2', '2023-10-19 13:52:29.697271 +00:00', '2023-10-19 13:52:29.697271 +00:00', 'IN PROGRESS', 2, 1),
                    ('TASK3', 'create 3', '2023-10-19 13:52:29.697271 +00:00', '2023-10-19 13:52:29.697271 +00:00', 'IN PROGRESS', 3, 1),
                    ('TASK4', 'create 4', '2023-10-19 13:52:29.697271 +00:00', '2023-10-19 13:52:29.697271 +00:00', 'IN PROGRESS', null,2);""";

    }
}
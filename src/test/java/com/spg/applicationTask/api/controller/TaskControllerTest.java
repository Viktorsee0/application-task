package com.spg.applicationTask.api.controller;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.spg.applicationTask.api.TestContainer;
import com.spg.applicationTask.api.dto.AssigneeDTO;
import com.spg.applicationTask.api.dto.TaskDTO;
import com.spg.applicationTask.api.mapper.TaskMapper;
import com.spg.applicationTask.engine.web.Server;
import com.spg.applicationTask.engine.web.WebClient;
import com.spg.applicationTask.engine.web.WebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import java.time.LocalDateTime;
import java.util.List;

import static com.spg.applicationTask.api.TestContainer.context;
import static com.spg.applicationTask.api.TestContainer.getContainer;
import static com.spg.applicationTask.api.utils.JsonUtils.toJson;
import static com.spg.applicationTask.engine.web.HttpMethod.DELETE;
import static com.spg.applicationTask.engine.web.HttpMethod.POST;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Task Controller Test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TaskControllerTest {

    @Container
    private static final PostgreSQLContainer<TestContainer> postgreSQLContainer = getContainer();

    private WebServer server;
    private WebClient client;
    private TaskController controller;
    private TaskMapper mapper;
    private static List<TaskDTO> savedTasks;

    @BeforeAll
    static void load() {
        savedTasks = List.of(
                new TaskDTO.Builder()
                        .title("TASK1")
                        .description("create 1")
                        .dueDate(LocalDateTime.of(2023, 10, 19, 16, 52, 29))
                        .status("IN PROGRESS")
                        .assignee(new AssigneeDTO.Builder()
                                .id(1)
                                .firstName("USERNEW")
                                .lastName("USERNEW")
                                .email("NEW@gmail.com")
                                .build())
                        .build(),
                new TaskDTO.Builder()
                        .title("TASK2")
                        .description("create 2")
                        .dueDate(LocalDateTime.of(2023, 10, 19, 16, 52, 29))
                        .status("IN PROGRESS")
                        .assignee(new AssigneeDTO.Builder()
                                .id(2)
                                .firstName("USER2")
                                .lastName("USER2")
                                .email("USER1000@gmail.com")
                                .build())
                        .build());
    }

    @BeforeEach
    void beforeEach() {
        postgreSQLContainer.start();
        server = context.getObject(Server.class);
        controller = context.getObject(TaskController.class);
        mapper = context.getObject(TaskMapper.class);
        server.addControllers(controller);
        server.start();
    }


    @AfterEach
    void afterEach() {
        server.stop();
        postgreSQLContainer.stop();
    }

    @Test
    void save() {
        client = new WebClient<JsonArray>("http://localhost:8000/task", POST, toJson(savedTasks));
        assertThat(client.getCode()).isEqualTo(200);
    }

    @Test
    void delete() {
        client = new WebClient<JsonArray>("http://localhost:8000/task?id=1", DELETE);
        assertThat(client.getCode()).isEqualTo(204);
    }

    @Test
    void deleteIfThrow() {
        client = new WebClient<JsonArray>("http://localhost:8000/task?id=1000", DELETE);
        assertThat(client.getCode()).isEqualTo(400);
    }
}

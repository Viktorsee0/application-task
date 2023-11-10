package com.spg.applicationTask.api.controller;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.spg.applicationTask.api.TestContainer;
import com.spg.applicationTask.api.dto.UserDTO;
import com.spg.applicationTask.api.mapper.UserMapper;
import com.spg.applicationTask.api.model.Project;
import com.spg.applicationTask.api.model.Task;
import com.spg.applicationTask.api.model.User;
import com.spg.applicationTask.engine.web.Server;
import com.spg.applicationTask.engine.web.WebClient;
import com.spg.applicationTask.engine.web.WebServer;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import java.time.LocalDateTime;
import java.util.List;

import static com.spg.applicationTask.api.TestContainer.context;
import static com.spg.applicationTask.api.TestContainer.getContainer;
import static com.spg.applicationTask.engine.extension.JsonUtils.toJson;
import static com.spg.applicationTask.engine.web.HttpMethod.*;
import static com.spg.applicationTask.engine.web.HttpMethod.DELETE;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("User Controller Test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserControllerTest {

    @Container
    private static final PostgreSQLContainer<TestContainer> postgreSQLContainer = getContainer();

    private WebServer server;
    private WebClient client;
    private UserController controller;
    private UserMapper mapper;
    private static List<UserDTO> userDTOS;
    private static User expectedUser;

    @BeforeAll
    static void load() {
        expectedUser = new User.Builder()
                .id(1)
                .firstName("USER1")
                .lastName("USER1")
                .email("USER1@gmail.com")
                .project(new Project.Builder()
                        .id(1)
                        .name("PROJECT1")
                        .description("PROJECT1")
                        .tasks(List.of(
                                new Task.Builder()
                                        .id(2)
                                        .title("TASK2")
                                        .description("create 2")
                                        .dueDate(LocalDateTime.of(2023, 10, 19, 16, 52, 29))
                                        .status("IN PROGRESS")
                                        .assignee(new User.Builder()
                                                .id(2)
                                                .firstName("USER2")
                                                .lastName("USER2")
                                                .email("USER2@gmail.com")
                                                .build())
                                        .build(),
                                new Task.Builder()
                                        .id(3)
                                        .title("TASK3")
                                        .description("create 3")
                                        .dueDate(LocalDateTime.of(2023, 10, 19, 16, 52, 29))
                                        .status("IN PROGRESS")
                                        .assignee(new User.Builder()
                                                .id(3)
                                                .firstName("USER3")
                                                .lastName("USER3")
                                                .email("USER3@gmail.com")
                                                .build())
                                        .build()
                        ))
                        .build())
                .build();
        userDTOS = List.of(new UserDTO.Builder()
                        .firstName("NEW USER 1")
                        .lastName("NEW USER 1")
                        .email("NEW1@gmail.com")
                        .build(),
                new UserDTO.Builder()
                        .firstName("NEW USER 2")
                        .lastName("NEW USER 2")
                        .email("NEW2@gmail.com")
                        .build()
        );
    }

    @BeforeEach
    void beforeEach() {
        postgreSQLContainer.start();
        server = context.getObject(Server.class);
        controller = context.getObject(UserController.class);
        mapper = context.getObject(UserMapper.class);
        server.addControllers(controller);
        server.start();
    }

    @AfterEach
    void afterEach() {
        server.stop();
        postgreSQLContainer.stop();
    }

    @Test
    void getAllUsers() {
        client = new WebClient<JsonArray>("http://localhost:8000/user", GET);
        List<UserDTO> response = ((JsonArray) client.getContent()).stream()
                .map(json -> new UserDTO.Builder((JsonObject) json).build())
                .toList();
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(response.size()).isEqualTo(6);
        softAssertions.assertThat(client.getCode()).isEqualTo(200);
        softAssertions.assertAll();
    }

    @Test
    void getUser() {
        client = new WebClient<JsonArray>("http://localhost:8000/user?id=1", GET);
        UserDTO response = new UserDTO.Builder(((JsonObject) client.getContent())).build();
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(mapper.toUser(response)).isEqualTo(expectedUser);
        softAssertions.assertThat(client.getCode()).isEqualTo(200);
        softAssertions.assertAll();
    }

    @Test
    void getProjectIfThrow() {
        client = new WebClient<JsonArray>("http://localhost:8000/user?id=100000000", GET);
        assertThat(client.getCode()).isEqualTo(404);
    }

    @Test
    void save() {
        client = new WebClient<JsonArray>("http://localhost:8000/user", POST, toJson(userDTOS));
        assertThat(client.getCode()).isEqualTo(200);
    }

    @Test
    void delete() {
        client = new WebClient<JsonArray>("http://localhost:8000/user?id=1", DELETE);
        assertThat(client.getCode()).isEqualTo(204);
    }

    @Test
    void deleteIfThrow() {
        client = new WebClient<JsonArray>("http://localhost:8000/user?id=1000", DELETE);
        assertThat(client.getCode()).isEqualTo(400);
    }
}

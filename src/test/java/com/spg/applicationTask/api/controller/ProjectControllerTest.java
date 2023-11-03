package com.spg.applicationTask.api.controller;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.spg.applicationTask.api.TestContainer;
import com.spg.applicationTask.api.dto.AssigneeDTO;
import com.spg.applicationTask.api.dto.ProjectDTO;
import com.spg.applicationTask.api.dto.TaskDTO;
import com.spg.applicationTask.api.mapper.ProjectMapper;
import com.spg.applicationTask.api.model.Project;
import com.spg.applicationTask.api.model.Task;
import com.spg.applicationTask.api.model.User;
import com.spg.applicationTask.engine.web.Server;
import com.spg.applicationTask.engine.web.WebClient;
import com.spg.applicationTask.engine.web.WebServer;
import org.assertj.core.api.SoftAssertions;
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
import static com.spg.applicationTask.engine.extension.JsonUtils.toJson;
import static com.spg.applicationTask.engine.web.HttpMethod.DELETE;
import static com.spg.applicationTask.engine.web.HttpMethod.GET;
import static com.spg.applicationTask.engine.web.HttpMethod.POST;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Project Controller Test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProjectControllerTest {

    @Container
    private static final PostgreSQLContainer<TestContainer> postgreSQLContainer = getContainer();

    private WebServer server;
    private WebClient client;
    private ProjectController controller;
    private ProjectMapper mapper;
    private static List<ProjectDTO> savedProjects;
    private static Project expectedProject;

    @BeforeAll
    static void load(){
        expectedProject = new Project.Builder()
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
                .build();
        savedProjects = List.of(new ProjectDTO.Builder()
                .name("PROJECT1111")
                .description("PROJECT1")
                .tasks(List.of(
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
                                .build()
                ))
                .build());
    }

    @BeforeEach
    void beforeEach() {
        postgreSQLContainer.start();
        server = context.getObject(Server.class);
        controller = context.getObject(ProjectController.class);
        mapper = context.getObject(ProjectMapper.class);
        server.addControllers(controller);
        server.start();
    }

    @AfterEach
    void afterEach() {
        server.stop();
        postgreSQLContainer.stop();
    }

    @Test
    void getAllProject() {
        client = new WebClient<JsonArray>("http://localhost:8000/project", GET);
        List<ProjectDTO> response = ((JsonArray) client.getResponse()).stream()
                .map(json -> new ProjectDTO.Builder((JsonObject) json).build())
                .toList();

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(response.size()).isEqualTo(4);
        softAssertions.assertThat(client.getCode()).isEqualTo(200);
        softAssertions.assertAll();
//        keytool -import -alias server-cert -keystore "C:\Program Files\Java\jdk-17\jre\lib\security\cacerts" -file
    }

    @Test
    void getProject() {
        client = new WebClient<JsonArray>("http://localhost:8000/project?id=1", GET);
        ProjectDTO response = new ProjectDTO.Builder(((JsonObject) client.getResponse())).build();

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(mapper.toProject(response)).isEqualTo(expectedProject);
        softAssertions.assertThat(client.getCode()).isEqualTo(200);
        softAssertions.assertAll();
    }

    @Test
    void getProjectIfThrow() {
        client = new WebClient<JsonArray>("http://localhost:8000/project?id=100000000", GET);
        assertThat(client.getCode()).isEqualTo(404);
    }

    @Test
    void save() {
        client = new WebClient<JsonArray>("http://localhost:8000/project", POST, toJson(savedProjects));
        assertThat(client.getCode()).isEqualTo(200);
    }

    @Test
    void delete() {
        client = new WebClient<JsonArray>("http://localhost:8000/project?id=1", DELETE);
        assertThat(client.getCode()).isEqualTo(204);
    }

    @Test
    void deleteIfThrow() {
        client = new WebClient<JsonArray>("http://localhost:8000/project?id=1000", DELETE);
        assertThat(client.getCode()).isEqualTo(400);
    }
}

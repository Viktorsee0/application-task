package com.spg.applicationTask.api.repository;

import com.spg.applicationTask.api.TestContainer;
import com.spg.applicationTask.api.model.Project;
import com.spg.applicationTask.api.model.Task;
import com.spg.applicationTask.api.model.User;
import com.spg.applicationTask.engine.IoC.Application;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProjectRepositoryTest {
    @Container
    private static final PostgreSQLContainer<TestContainer> postgreSQLContainer = TestContainer.getContainer();
    private static ProjectRepository repository;
    private static Project expected;
    private static List<Project> insertedProjects;
    private static List<Project> updatedProjects;

    @BeforeAll
    void load() {
        postgreSQLContainer.start();
        repository = Application.getContext().getObject(ProjectRepository.class);
        loadData();
    }

    @AfterAll
    void stop() {
        postgreSQLContainer.stop();
    }

    @Test
    @DisplayName("Search project by id")
    void searchId() {
        Project project = repository.getProject(1).get();
        assertThat(project).isEqualTo(expected);
    }

    @Test
    @DisplayName("Search for a project by id, if the project with such id does not exist")
    void searchIdIfDoseNotExist() {
        Optional<Project> project = repository.getProject(10000);
        assertThat(project).isEqualTo(Optional.empty());
    }

    @Test
    @DisplayName("Insert project")
    void create() {
        List<Project> ids = repository.saveAndFlush(insertedProjects);
        assertThat(ids.size()).isEqualTo(insertedProjects.size());
    }

    @Test
    @DisplayName("Update project")
    void update() {
        List<Project> result = repository.saveAndFlush(updatedProjects);
        assertThat(result).isEqualTo(updatedProjects);
    }

    @Test
    @DisplayName("Delete project")
    void delete() {
        boolean result = repository.delete(4);
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Delete if project with such an id does not exist")
    void deleteIfIdDoesNotExist() {
        boolean result = repository.delete(1000000);
        assertThat(result).isFalse();
    }

    static void loadData(){
        updatedProjects = List.of(new Project.Builder()
                        .id(3)
                        .name("NEW NAME 3")
                        .description("TEST UPDATE PROJECT")
                        .build(),
                new Project.Builder()
                        .id(2)
                        .name("NEW NAME 2")
                        .description("TEST UPDATE PROJECT")
                        .build()
        );
        insertedProjects = List.of(new Project.Builder()
                        .name("TEST1")
                        .description("TEST CREATE PROJECT")
                        .build(),
                new Project.Builder()
                        .name("TEST2")
                        .description("TEST CREATE PROJECT")
                        .build()
        );
        expected = new Project.Builder()
                .id(1)
                .name("PROJECT1")
                .description("PROJECT1")
                .tasks(List.of(
                        new Task.Builder()
                                .id(2)
                                .title("TASK2")
                                .description("create 2")
                                .createdDate(LocalDateTime.of(2023, 10, 19, 16, 52, 29, 697271000))
                                .dueDate(LocalDateTime.of(2023, 10, 19, 16, 52, 29, 697271000))
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
                                .createdDate(LocalDateTime.of(2023, 10, 19, 16, 52, 29, 697271000))
                                .dueDate(LocalDateTime.of(2023, 10, 19, 16, 52, 29, 697271000))
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
    }
}

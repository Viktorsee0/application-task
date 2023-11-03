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
public class UserRepositoryTest {

    @Container
    private static final PostgreSQLContainer<TestContainer> postgreSQLContainer = TestContainer.getContainer();
    private static UserRepository repository;
    private static User expected;
    private static List<User> expectedUsers;
    private static List<User> updatedUsers;

    @BeforeAll
    void load() {
        postgreSQLContainer.start();
        repository = Application.getContext().getObject(UserRepository.class);
        loadData();
    }

    @AfterAll
    void stop() {
        postgreSQLContainer.stop();
    }

    @Test
    @DisplayName("Search user by id")
    void searchId() {
        User user = repository.getById(1).get();
        assertThat(user).isEqualTo(expected);
    }

    @Test
    @DisplayName("Search for a user by id, if the user with such id does not exist")
    void searchIdIfDoseNotExist() {
        Optional<User> project = repository.getById(10000);
        assertThat(project).isEqualTo(Optional.empty());
    }

    @Test
    @DisplayName("Insert user")
    void create() {
        List<User> result = repository.saveAndFlush(expectedUsers);
        assertThat(result.size()).isEqualTo(expectedUsers.size());
    }

    @Test
    @DisplayName("Update user")
    void update() {
        List<User> result = repository.saveAndFlush(updatedUsers);
        assertThat(result).isEqualTo(updatedUsers);
    }

    @Test
    @DisplayName("Delete project")
    void delete() {
        boolean result = repository.delete(6);
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Delete if project with such an id does not exist")
    void deleteIfIdDoesNotExist() {
        boolean result = repository.delete(1000000);
        assertThat(result).isFalse();
    }

    static void loadData(){
        updatedUsers = List.of(new User.Builder()
                        .id(4)
                        .firstName("UPDATED USER 4")
                        .lastName("UPDATED USER 4")
                        .email("UPDATED4@gmail.com")
                        .build(),
                new User.Builder()
                        .id(5)
                        .firstName("UPDATED USER 5")
                        .lastName("UPDATED USER 5")
                        .email("UPDATED5@gmail.com")
                        .build()
        );
        expectedUsers = List.of(new User.Builder()
                        .firstName("NEW USER 1")
                        .lastName("NEW USER 1")
                        .email("NEW1@gmail.com")
                        .build(),
                new User.Builder()
                        .firstName("NEW USER 2")
                        .lastName("NEW USER 2")
                        .email("NEW2@gmail.com")
                        .build()
        );
        expected = new User.Builder()
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
                        .build())
                .build();
    }
}

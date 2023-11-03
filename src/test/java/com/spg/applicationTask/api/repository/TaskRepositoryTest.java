package com.spg.applicationTask.api.repository;

import com.spg.applicationTask.api.TestContainer;
import com.spg.applicationTask.api.model.Task;
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

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TaskRepositoryTest {

    @Container
    private static final PostgreSQLContainer<TestContainer> postgreSQLContainer = TestContainer.getContainer();
    private static TaskRepository repository;
    private static  List<Task> updatedTasks;

    @BeforeAll
    void load() {
        postgreSQLContainer.start();
        repository = Application.getContext().getObject(TaskRepository.class);
        updatedTasks = List.of(new Task.Builder()
                .id(4)
                .title("NEW TASK4 TITLE")
                .description("NEW DESCR")
                .dueDate(LocalDateTime.now().plusDays(20))
                .createdDate(LocalDateTime.of(2023, 10, 19, 16, 52, 29, 697271000))
                .status("IN PROGRESS")
                .build()
        );
    }

    @AfterAll
    void stop() {
        postgreSQLContainer.stop();
    }

    @Test
    @DisplayName("Update task")
    void update() {
        List<Task> result = repository.saveAndFlush(updatedTasks);
        assertThat(result).isEqualTo(updatedTasks);
    }

    @Test
    @DisplayName("Delete task")
    void delete() {
        boolean result = repository.delete(1);;
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Delete if task with such an id does not exist")
    void deleteIfIdDoesNotExist() {
        boolean result = repository.delete(1000000);
        assertThat(result).isFalse();
    }
}

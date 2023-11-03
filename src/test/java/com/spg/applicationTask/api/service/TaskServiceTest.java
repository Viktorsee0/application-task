package com.spg.applicationTask.api.service;

import com.spg.applicationTask.Main;
import com.spg.applicationTask.api.mapper.TaskMapper;
import com.spg.applicationTask.api.dto.TaskDTO;
import com.spg.applicationTask.api.model.Task;
import com.spg.applicationTask.api.repository.TaskRepository;
import com.spg.applicationTask.engine.IoC.Application;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class TaskServiceTest {
    private static TaskRepository repository;
    private static ArgumentCaptor<Integer> integerArgumentCaptor;
    private static ArgumentCaptor<List<Task>> tasksArgumentCaptor;
    private static TaskService service;
    private static TaskMapper mapper;
    private static LocalDateTime dateTime;
    private static List<Task> repoRes;
    private static List<TaskDTO> taskDTOS;
    private static List<Task> repoArg;

    @BeforeAll
    static void load() {
        Application.run(Main.class);
        repository = Mockito.mock(TaskRepository.class);
        service = Application.getContext().getObject(TaskService.class);
        mapper = Application.getContext().getObject(TaskMapper.class);
        service.setTaskRepository(repository);
        integerArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        tasksArgumentCaptor = ArgumentCaptor.forClass(List.class);
        loadData();
    }

    @Test
    @DisplayName("Delete user by id if user does not exist")
    void deleteIfThrow() {
        Mockito.doReturn(false).when(repository).delete(1);
        assertThatThrownBy(() -> service.delete(1))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("Delete user by id")
    void delete() {
        Mockito.doReturn(true).when(repository).delete(1);
        service.delete(1);
        verify(repository, times(1)).delete(integerArgumentCaptor.capture());
        assertThat(integerArgumentCaptor.getValue()).isEqualTo(1);
    }

    @Test
    @DisplayName("Save user")
    void save() {
        doReturn(repoRes).when(repository).saveAndFlush(repoArg);
        List<TaskDTO> result = service.save(taskDTOS);
        verify(repository, times(1)).saveAndFlush(tasksArgumentCaptor.capture());
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(result.size()).isEqualTo(taskDTOS.size());
        softAssertions.assertThat(repoArg).isEqualTo(tasksArgumentCaptor.getValue());
        softAssertions.assertAll();
    }

    static void loadData() {
        dateTime = LocalDateTime.now().plusDays(20);
        taskDTOS = List.of(new TaskDTO.Builder()
                .title("NEW TASK4 TITLE")
                .description("NEW DESCR")
                .dueDate(dateTime)
                .status("IN PROGRESS")
                .build());
        repoArg = List.of(new Task.Builder()
                .title("NEW TASK4 TITLE")
                .description("NEW DESCR")
                .dueDate(dateTime)
                .status("IN PROGRESS")
                .build());
        repoRes = List.of(new Task.Builder()
                .id(1)
                .title("NEW TASK4 TITLE")
                .description("NEW DESCR")
                .dueDate(dateTime)
                .status("IN PROGRESS")
                .build());
    }
}

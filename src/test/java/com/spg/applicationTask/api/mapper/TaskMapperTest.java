package com.spg.applicationTask.api.mapper;

import com.spg.applicationTask.Main;
import com.spg.applicationTask.api.dto.TaskDTO;
import com.spg.applicationTask.api.model.Task;
import com.spg.applicationTask.engine.IoC.Application;
import com.spg.applicationTask.engine.IoC.ApplicationContext;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

@DisplayName("Task Mapper Test")
class TaskMapperTest {

    private static TaskMapper mapper;
    private static Task task;
    private static TaskDTO taskDTO;;

    @BeforeAll
    static void load(){
        task = new Task.Builder()
                .id(1)
                .title("task")
                .description("task")
                .dueDate(LocalDateTime.now())
                .status("IN PROGRESS")
                .build();
        taskDTO = new TaskDTO.Builder()
                .id(1)
                .title("TaskDTO")
                .description("TaskDTO")
                .dueDate(LocalDateTime.now())
                .status("IN PROGRESS")
                .build();
        ApplicationContext context = Application.run(Main.class);
        mapper = context.getObject(TaskMapper.class);
    }

    @Test
    void toTasks() {
        List<Task> result = mapper.toTask(List.of(taskDTO));
        softAssert(result, List.of(taskDTO));
    }

    @Test
    void toTask() {
        Task result = mapper.toTask(taskDTO);
        softAssert(result, taskDTO);
    }

    @Test
    void toProjectDto() {
        TaskDTO result = mapper.toTaskDto(task);
        softAssert(task, result);
    }

    @Test
    void toProjectDtos() {
        List<TaskDTO> result = mapper.toTaskDto(List.of(task));
        softAssert(List.of(task), result);
    }

    private void softAssert(Task task, TaskDTO taskDTO){
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(task.getId()).isEqualTo(taskDTO.getId());
        softAssertions.assertThat(task.getTitle()).isEqualTo(taskDTO.getTitle());
        softAssertions.assertThat(task.getDescription()).isEqualTo(taskDTO.getDescription());
        softAssertions.assertThat(task.getDueDate()).isEqualTo(taskDTO.getDueDate());
        softAssertions.assertThat(task.getStatus()).isEqualTo(taskDTO.getStatus());
        softAssertions.assertAll();
    }

    private void softAssert(List<Task> tasks, List<TaskDTO> taskDTOs){
        SoftAssertions softAssertions = new SoftAssertions();
        for (int i = 0; i < tasks.size(); i++) {
            softAssertions.assertThat(tasks.get(i).getId()).isEqualTo(taskDTOs.get(i).getId());
            softAssertions.assertThat(tasks.get(i).getTitle()).isEqualTo(taskDTOs.get(i).getTitle());
            softAssertions.assertThat(tasks.get(i).getDescription()).isEqualTo(taskDTOs.get(i).getDescription());
            softAssertions.assertThat(tasks.get(i).getDueDate()).isEqualTo(taskDTOs.get(i).getDueDate());
            softAssertions.assertThat(tasks.get(i).getStatus()).isEqualTo(taskDTOs.get(i).getStatus());
        }
        softAssertions.assertAll();
    }
}
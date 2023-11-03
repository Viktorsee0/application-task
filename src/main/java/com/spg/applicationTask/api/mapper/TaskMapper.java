package com.spg.applicationTask.api.mapper;

import com.spg.applicationTask.api.dto.TaskDTO;
import com.spg.applicationTask.api.model.Task;
import com.spg.applicationTask.engine.IoC.annotation.Component;
import com.spg.applicationTask.engine.IoC.annotation.Inject;

import java.util.List;

@Component
public class TaskMapper {

    @Inject
    private UserMapper mapper;

    public Task toTask(final TaskDTO dto) {
        return new Task.Builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .status(dto.getStatus())
                .dueDate(dto.getDueDate())
                .assignee(dto.getAssignee() != null ?
                        mapper.toUser(dto.getAssignee()) : null)
                .build();
    }

    public List<Task> toTask(final List<TaskDTO> dto) {
        return dto.stream().map(this::toTask).toList();
    }

    public TaskDTO toTaskDto(final Task task) {
        return new TaskDTO.Builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .dueDate(task.getDueDate())
                .assignee(task.getAssignee().isPresent() ?
                        mapper.toAssigneeDto(task.getAssignee().get()) : null)
                .status(task.getStatus())
                .build();
    }

    public List<TaskDTO> toTaskDto(final List<Task> tasks) {
        return tasks.stream().map(this::toTaskDto).toList();
    }
}

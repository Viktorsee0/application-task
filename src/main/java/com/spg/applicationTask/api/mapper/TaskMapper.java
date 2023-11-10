package com.spg.applicationTask.api.mapper;

import com.spg.applicationTask.api.dto.TaskDTO;
import com.spg.applicationTask.api.model.Task;
import com.spg.applicationTask.engine.IoC.annotation.Component;
import com.spg.applicationTask.engine.IoC.annotation.Inject;

import java.util.List;

/**
 * Provides method for mapping task to dto and dto to task
 */
@Component
public class TaskMapper {

    @Inject
    private UserMapper mapper;

    /**
     * Maps the task dto with the task
     *
     * @param dto a task dto
     * @return task
     */
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

    /**
     * Maps the list of task dto with the list of task
     *
     * @param dto a list of task dto
     * @return list of task
     */
    public List<Task> toTask(final List<TaskDTO> dto) {
        return dto.stream().map(this::toTask).toList();
    }

    /**
     * Maps the task with the task dto
     *
     * @param task a task
     * @return task dto
     */
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

    /**
     * Maps the list of task with the list of task dto
     *
     * @param tasks a list of task
     * @return list of task
     */
    public List<TaskDTO> toTaskDto(final List<Task> tasks) {
        return tasks.stream().map(this::toTaskDto).toList();
    }
}

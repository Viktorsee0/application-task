package com.spg.applicationTask.api.service.serviceImpl;

import com.spg.applicationTask.api.mapper.TaskMapper;
import com.spg.applicationTask.api.dto.TaskDTO;
import com.spg.applicationTask.api.model.Task;
import com.spg.applicationTask.api.repository.TaskRepository;
import com.spg.applicationTask.api.service.TaskService;
import com.spg.applicationTask.engine.IoC.annotation.Component;
import com.spg.applicationTask.engine.IoC.annotation.Inject;
import com.spg.applicationTask.api.utils.Validator;

import java.util.List;

import static com.spg.applicationTask.engine.Constants.Messages.EXISTING_TASK_ERROR;

@Component
public class TaskServiceImpl implements TaskService {

    @Inject
    private TaskRepository taskRepository;
    @Inject
    private TaskMapper mapper;

    @Override
    public void delete(int id) {
        Validator.of(taskRepository.delete(id))
                .validate(result->result.equals(true), EXISTING_TASK_ERROR)
                .get();
    }

    @Override
    public List<TaskDTO> save(List<TaskDTO> tasksDto) {
        List<Task> tasks1 = mapper.toTask(tasksDto);
        List<Task> tasks = taskRepository.saveAndFlush(tasks1);
        List<TaskDTO> taskDTOS = mapper.toTaskDto(tasks);
        return taskDTOS ;
    }

    @Override
    public void setTaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
}

package com.spg.applicationTask.api.service;

import com.spg.applicationTask.api.dto.TaskDTO;
import com.spg.applicationTask.api.repository.TaskRepository;

import java.util.List;

public interface TaskService {

    void delete(int id);
    List<TaskDTO> save(List<TaskDTO> task);
    void setTaskRepository(TaskRepository taskRepository);
}

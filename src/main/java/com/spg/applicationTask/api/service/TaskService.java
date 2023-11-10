package com.spg.applicationTask.api.service;

import com.spg.applicationTask.api.dto.TaskDTO;
import com.spg.applicationTask.api.repository.TaskRepository;

import java.util.List;

/**
 * Provides service methods to create, read, update and delete operations.
 */
public interface TaskService {

    /**
     * Removes task model by id.
     *
     * @param id an id of task model.
     */
    void delete(int id);

    /**
     * Updates/creates task models.
     *
     * @param tasks a list of taskDTO.
     * @return a list of updated task models as DTO.
     * @see TaskDTO
     */
    List<TaskDTO> save(List<TaskDTO> tasks);

    /**
     * Sets task repository.
     *
     * @param repository a taskRepository.
     * @see TaskRepository
     */
    void setTaskRepository(TaskRepository repository);
}

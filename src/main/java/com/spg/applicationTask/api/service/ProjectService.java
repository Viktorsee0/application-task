package com.spg.applicationTask.api.service;

import com.spg.applicationTask.api.dto.ProjectDTO;
import com.spg.applicationTask.api.repository.ProjectRepository;

import java.util.List;

/**
 * Provides service methods to create, read, update and delete operations.
 */
public interface ProjectService {

    /**
     * Returns all project models.
     *
     * @return a list of project models as DTO.
     * @see ProjectDTO
     */
    List<ProjectDTO> getProjects();

    /**
     * Returns project model by id.
     *
     * @param id an id of project model.
     * @return a project models as DTO.
     */
    ProjectDTO getProject(int id);

    /**
     * Updates/creates project models.
     *
     * @param projects a list of projectDTO.
     * @return a list of updated project models as DTO.
     * @see ProjectDTO
     */
    List<ProjectDTO> save(final List<ProjectDTO> projects);

    /**
     * Removes project model by id.
     *
     * @param id an id of project model.
     */
    void delete(final int id);

    /**
     * Sets project repository.
     *
     * @param repository a projectRepository.
     * @see ProjectRepository
     */
    void setProjectRepository(final ProjectRepository repository);
}

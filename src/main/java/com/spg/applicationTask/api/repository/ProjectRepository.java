package com.spg.applicationTask.api.repository;

import com.spg.applicationTask.api.model.Project;

import java.util.List;
import java.util.Optional;

/**
 * Provides repository methods to create, read, update and delete operations.
 */
public interface ProjectRepository {
    /**
     * Returns all project models.
     *
     * @return a list of project models.
     */
    List<Project> getProjects();

    /**
     * Returns project models by project id.
     *
     * @param id - id of project model.
     * @return a project model.
     */
    Optional<Project> getProject(int id);

    /**
     * Saves and flushes project models.
     *
     * @param projects a list of project models.
     */
    List<Project> saveAndFlush(List<Project> projects);

    /**
     * Deletes project models.
     *
     * @param id an id of project model.
     */
    boolean delete(final int id);
}

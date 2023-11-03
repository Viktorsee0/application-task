package com.spg.applicationTask.api.service;

import com.spg.applicationTask.api.dto.ProjectDTO;
import com.spg.applicationTask.api.repository.ProjectRepository;

import java.util.List;

public interface ProjectService {

    List<ProjectDTO> getProjects();
    ProjectDTO getProject(int id);
    List<ProjectDTO> save(final List<ProjectDTO> projects);
    void delete(final int id);
    void setProjectRepository(final ProjectRepository repository);
}

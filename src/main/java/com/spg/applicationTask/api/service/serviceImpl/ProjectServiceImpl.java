package com.spg.applicationTask.api.service.serviceImpl;

import com.spg.applicationTask.api.mapper.ProjectMapper;
import com.spg.applicationTask.api.dto.ProjectDTO;
import com.spg.applicationTask.engine.IoC.annotation.Inject;
import com.spg.applicationTask.engine.IoC.annotation.Component;
import com.spg.applicationTask.api.model.Project;
import com.spg.applicationTask.api.repository.ProjectRepository;
import com.spg.applicationTask.api.service.ProjectService;
import com.spg.applicationTask.engine.extension.Validator;
import com.spg.applicationTask.engine.web.exception.ElementDoesNotExistException;

import java.util.List;

import static com.spg.applicationTask.api.Constants.Messages.EXISTING_PROJECT_ERROR;

@Component
public class ProjectServiceImpl implements ProjectService {

    @Inject
    private ProjectRepository projectRepository;
    @Inject
    private ProjectMapper mapper;

    @Override
    public List<ProjectDTO> getProjects() {
        return mapper.toProjectDto(projectRepository.getProjects());
    }

    @Override
    public ProjectDTO getProject(final int id) {
        Project project = projectRepository.getProject(id)
                .orElseThrow(() ->
                        new ElementDoesNotExistException(404, EXISTING_PROJECT_ERROR));
        return mapper.toProjectDto(project);
    }

    @Override
    public List<ProjectDTO> save(final List<ProjectDTO> projects) {
        return mapper.toProjectDto(
                projectRepository.saveAndFlush(mapper.toProject(projects)
                )
        );
    }

    @Override
    public void delete(final int id) {
        Validator.of(projectRepository.delete(id))
                .validate(result -> result.equals(true), EXISTING_PROJECT_ERROR)
                .get();
    }

    @Override
    public void setProjectRepository(final ProjectRepository repository) {
        this.projectRepository = repository;
    }
}

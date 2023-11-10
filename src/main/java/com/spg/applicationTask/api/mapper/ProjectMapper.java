package com.spg.applicationTask.api.mapper;

import com.spg.applicationTask.api.dto.ProjectDTO;
import com.spg.applicationTask.api.model.Project;
import com.spg.applicationTask.engine.IoC.annotation.Component;
import com.spg.applicationTask.engine.IoC.annotation.Inject;

import java.util.List;

/**
 * Provides method for mapping project to dto and dto to project
 */
@Component
public class ProjectMapper {

    @Inject
    private TaskMapper mapper;

    /**
     * Maps the project dto with the project
     *
     * @param dto a project dto
     * @return project
     */
    public Project toProject(final ProjectDTO dto) {
        return new Project.Builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .tasks(mapper.toTask(dto.getTasks()))
                .build();
    }

    /**
     * Maps the list of project dto to the list of projects
     *
     * @param dto a list of project dto
     * @return list of project
     */
    public List<Project> toProject(final List<ProjectDTO> dto) {
        return dto.stream().map(this::toProject).toList();
    }

    /**
     * Maps the project with the project dto
     *
     * @param project a project
     * @return project dto
     */
    public ProjectDTO toProjectDto(final Project project) {
        return new ProjectDTO.Builder()
                .id(project.getId())
                .description(project.getDescription())
                .name(project.getName())
                .tasks(mapper.toTaskDto(project.getTasks()))
                .build();
    }

    /**
     * Maps the list of projects to the list of project dto
     *
     * @param projects a list of project
     * @return list of project dto
     */
    public List<ProjectDTO> toProjectDto(final List<Project> projects) {
        return projects.stream().map(this::toProjectDto).toList();
    }
}

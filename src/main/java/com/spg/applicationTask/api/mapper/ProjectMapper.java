package com.spg.applicationTask.api.mapper;

import com.spg.applicationTask.api.dto.ProjectDTO;
import com.spg.applicationTask.api.model.Project;
import com.spg.applicationTask.engine.IoC.annotation.Component;
import com.spg.applicationTask.engine.IoC.annotation.Inject;

import java.util.List;

@Component
public class ProjectMapper {

    @Inject
    private TaskMapper mapper;

    public Project toProject(final ProjectDTO dto) {
        return new Project.Builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .tasks(mapper.toTask(dto.getTasks()))
                .build();
    }

    public List<Project> toProject(final List<ProjectDTO> dto) {
        return dto.stream().map(this::toProject).toList();
    }

    public ProjectDTO toProjectDto(final Project project) {
        return new ProjectDTO.Builder()
                .id(project.getId())
                .description(project.getDescription())
                .name(project.getName())
                .tasks(mapper.toTaskDto(project.getTasks()))
                .build();
    }

    public List<ProjectDTO> toProjectDto(final List<Project> projects) {
        return projects.stream().map(this::toProjectDto).toList();
    }
}

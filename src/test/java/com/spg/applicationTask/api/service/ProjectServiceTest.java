package com.spg.applicationTask.api.service;

import com.spg.applicationTask.Main;
import com.spg.applicationTask.api.dto.ProjectDTO;
import com.spg.applicationTask.api.mapper.ProjectMapper;
import com.spg.applicationTask.api.model.Project;
import com.spg.applicationTask.api.repository.ProjectRepository;
import com.spg.applicationTask.engine.IoC.Application;
import com.spg.applicationTask.engine.web.exception.ElementDoesNotExistException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProjectServiceTest {

    private static ProjectRepository repository;
    private static ArgumentCaptor<Integer> integerArgumentCaptor;
    private static ProjectService service;
    private static ProjectMapper mapper;
    private static Optional<Project> optionalProject;
    private static List<ProjectDTO> projectDto;
    private static List<Project> repoArg;
    private static List<Project> repoResult;
    private static List<Project> projects;

    @BeforeAll
    static void load() {
        Application.run(Main.class);
        repository = mock(ProjectRepository.class);
        service = Application.getContext().getObject(ProjectService.class);
        mapper = Application.getContext().getObject(ProjectMapper.class);
        service.setProjectRepository(repository);
        integerArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        loadData();
    }

    @Test
    @DisplayName("Get project by id if project does not exist")
    void getProjectIfThrow() {
        doReturn(Optional.empty())
                .when(repository).getProject(anyInt());
        assertThatThrownBy(() -> service.getProject(1))
                .isInstanceOf(ElementDoesNotExistException.class);
    }

    @Test
    @DisplayName("Get project by id")
    void getProject() {
        when(repository.getProject(3)).thenReturn(optionalProject);
        ProjectDTO result = service.getProject(3);
        assertThat(mapper.toProject(result)).isEqualTo(optionalProject.get());
    }

    @Test
    @DisplayName("Delete project by id if project does not exist")
    void deleteIfThrow() {
        doReturn(false).when(repository).delete(1);
        assertThatThrownBy(() -> service.delete(1))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("Delete project by id")
    void delete() {
        integerArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        doReturn(true).when(repository).delete(anyInt());
        service.delete(1);
        verify(repository).delete(integerArgumentCaptor.capture());
        assertThat(integerArgumentCaptor.getValue())
                .isEqualTo(1);
    }

    @Test
    @DisplayName("Save project")
    void save() {
        doReturn(repoResult).when(repository).saveAndFlush(repoArg);
        List<ProjectDTO> result = service.save(projectDto);
        assertThat(mapper.toProject(result)).isEqualTo(repoResult);
    }

    @Test
    @DisplayName("Get all project")
    void getProjects() {
        doReturn(projects).when(repository).getProjects();
        List<ProjectDTO> result = service.getProjects();
        assertThat(mapper.toProject(result)).isEqualTo(projects);
    }

    static void loadData() {
        optionalProject = Optional.of(new Project.Builder()
                .id(3)
                .name("TEST1")
                .description("TEST CREATE PROJECT")
                .build());
        projectDto = List.of(new ProjectDTO.Builder()
                .name("TEST1")
                .description("TEST CREATE PROJECT")
                .build());
        repoArg = List.of(new Project.Builder()
                .name("TEST1")
                .description("TEST CREATE PROJECT")
                .build());
        repoResult = List.of(new Project.Builder()
                .id(1)
                .name("TEST1")
                .description("TEST CREATE PROJECT")
                .build());
        projects = List.of(new Project.Builder()
                .name("TEST1")
                .description("TEST CREATE PROJECT")
                .build());
    }
}

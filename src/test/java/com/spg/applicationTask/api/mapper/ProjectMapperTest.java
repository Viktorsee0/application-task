package com.spg.applicationTask.api.mapper;

import com.spg.applicationTask.Main;
import com.spg.applicationTask.api.dto.ProjectDTO;
import com.spg.applicationTask.api.model.Project;
import com.spg.applicationTask.engine.IoC.Application;
import com.spg.applicationTask.engine.IoC.ApplicationContext;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

@DisplayName("Project Mapper Test")
class ProjectMapperTest {

    private static ProjectMapper mapper;
    private static Project project;
    private static ProjectDTO projectDTO;;

    @BeforeAll
    static void load(){
        project = new Project.Builder()
                .id(1)
                .name("project")
                .description("project")
                .build();
        projectDTO = new ProjectDTO.Builder()
                .id(1)
                .name("projectDTO")
                .description("projectDTO")
                .build();
        ApplicationContext context = Application.run(Main.class);
        mapper = context.getObject(ProjectMapper.class);
    }

    @Test
    void toProjects() {
        List<Project> result = mapper.toProject(List.of(projectDTO));
        softAssert(result, List.of(projectDTO));
    }

    @Test
    void toProject() {
        Project result = mapper.toProject(projectDTO);
        softAssert(result, projectDTO);
    }

    @Test
    void toProjectDto() {
        ProjectDTO result = mapper.toProjectDto(project);
        softAssert(project, result);
    }

    @Test
    void toProjectDtos() {
        List<ProjectDTO> result = mapper.toProjectDto(List.of(project));
        softAssert(List.of(project), result);
    }

    private void softAssert(Project project, ProjectDTO projectDTO){
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(project.getId()).isEqualTo(projectDTO.getId());
        softAssertions.assertThat(project.getName()).isEqualTo(projectDTO.getName());
        softAssertions.assertThat(project.getDescription()).isEqualTo(projectDTO.getDescription());
        softAssertions.assertAll();
    }

    private void softAssert(List<Project> projects, List<ProjectDTO> projectDTOs){
        SoftAssertions softAssertions = new SoftAssertions();
        for (int i = 0; i < projects.size(); i++) {
            softAssertions.assertThat(projects.get(i).getId()).isEqualTo(projectDTOs.get(i).getId());
            softAssertions.assertThat(projects.get(i).getName()).isEqualTo(projectDTOs.get(i).getName());
            softAssertions.assertThat(projects.get(i).getDescription()).isEqualTo(projectDTOs.get(i).getDescription());
        }
        softAssertions.assertAll();
    }
}
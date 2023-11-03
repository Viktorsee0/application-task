package com.spg.applicationTask.api.repository.repositoryImpl;

import com.spg.applicationTask.api.model.Project;
import com.spg.applicationTask.api.model.Task;
import com.spg.applicationTask.api.model.User;
import com.spg.applicationTask.api.repository.ProjectRepository;
import com.spg.applicationTask.api.repository.TaskRepository;
import com.spg.applicationTask.api.utils.JDBCUtils;
import com.spg.applicationTask.api.utils.SQLUtils;
import com.spg.applicationTask.engine.IoC.annotation.Component;
import com.spg.applicationTask.engine.IoC.annotation.Inject;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.spg.applicationTask.engine.Constants.Messages.DELETE_PROJECT_ERROR;
import static com.spg.applicationTask.engine.Constants.Messages.RECEIVED_PROJECT_ERROR;
import static com.spg.applicationTask.engine.Constants.Messages.SAVE_PROJECTS_ERROR;

@Component
public class ProjectRepositoryImpl implements ProjectRepository {

    @Inject
    private TaskRepository taskRepository;
    @Inject
    private DataSource dataSource;

    @Override
    public List<Project> getProjects() {
        final String sql = SQLUtils.Select.project();
        Map<Integer, Project> projects = new HashMap<>();

        try (final Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Project currentProject;
                    int projectId = resultSet.getInt(1);
                    if (!projects.containsKey(projectId)) {
                        currentProject = new Project.Builder()
                                .id(projectId)
                                .name(resultSet.getString(2))
                                .description(resultSet.getString(3))
                                .build();
                        projects.put(projectId, currentProject);
                    }
                    int taskId = resultSet.getInt(4);
                    if (taskId > 0) {
                        Task task = setTask(resultSet, taskId);
                        projects.get(projectId).addTask(task);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(RECEIVED_PROJECT_ERROR, e);
        }

        return projects.values().stream().toList();
    }

    @Override
    public Optional<Project> getProject(int id) {
        final String sql = SQLUtils.Select.project();

        try (final Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql + " WHERE P.ID = ?")) {

            preparedStatement.setInt(1, id);
            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                Project project = null;
                while (resultSet.next()) {
                    if (project == null) {
                        project = new Project.Builder()
                                .id(resultSet.getInt(1))
                                .name(resultSet.getString(2))
                                .description(resultSet.getString(3))
                                .build();
                    }
                    int taskId = resultSet.getInt(4);
                    if (taskId > 0) {
                        Task task = setTask(resultSet, taskId);
                        project.addTask(task);
                    }
                }
                return Optional.ofNullable(project);
            }
        } catch (SQLException e) {
            throw new RuntimeException(RECEIVED_PROJECT_ERROR, e);
        }
    }

    @Override
    public List<Project> saveAndFlush(final List<Project> projects) {
        Connection connection = null;
        final List<Project> result = new ArrayList<>();
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            result.addAll(saveAndFlush(connection, projects));
        } catch (final SQLException e) {
            JDBCUtils.rollback(connection, e);
        } finally {
            JDBCUtils.close(connection);
        }
        return result;
    }

    @Override
    public boolean delete(final int id) {
        boolean result = false;
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            result = delete(connection, id);
        } catch (final SQLException e) {
            JDBCUtils.rollback(connection, e);
        } finally {
            JDBCUtils.close(connection);
        }
        return result;
    }

    private boolean delete(final Connection connection, final int id) throws SQLException {
        int result;
        try {
            final String sql = SQLUtils.Delete.projects();
            try (final PreparedStatement statement =
                         connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                result = statement.executeUpdate();
                connection.commit();
            }
        } catch (final SQLException e) {
            throw new SQLException(DELETE_PROJECT_ERROR, e);
        }
        return result == 1;
    }

    private List<Project> saveAndFlush(final Connection connection, final List<Project> projects) throws SQLException {
        final Collection<Project> toUpdate = new LinkedList<>();
        final Collection<Project> toInsert = new LinkedList<>();
        final List<Project> result = new ArrayList<>();
        for (final Project project : projects) {
            if (project.getId() > 0) {
                toUpdate.add(project);
            } else {
                toInsert.add(project);
            }
        }
        final Project[] savedProjects = new Project[toUpdate.size() + toInsert.size()];
        if (toUpdate.size() > 0) {
            result.addAll(update(connection, toUpdate.toArray(new Project[0])));
        }
        if (toInsert.size() > 0) {
            result.addAll(insert(connection, toInsert.toArray(new Project[0])));
        }
        if (savedProjects.length > 0) {
            connection.commit();
        }
        return result;
    }

    private List<Project> update(final Connection connection, final Project[] projects) throws SQLException {
        final List<Project> result = new ArrayList<>();
        if (projects.length > 0) {
            final String configsSql = SQLUtils.Update.project();
            result.addAll(execute(connection, projects, configsSql));
        }
        return result;
    }

    private List<Project> insert(final Connection connection, final Project[] projects) throws SQLException {
        final List<Project> result = new ArrayList<>();
        if (projects.length > 0) {
            final String configsSql = SQLUtils.Insert.project();
            result.addAll(execute(connection, projects, configsSql));
        }
        return result;
    }

    private List<Project> execute(final Connection connection, final Project[] projects, final String configsSql) throws SQLException {
        try (final PreparedStatement statement =
                     connection.prepareStatement(configsSql, Statement.RETURN_GENERATED_KEYS)) {
            for (Project project : projects) {
                statement.setString(1, project.getName());
                statement.setString(2, project.getDescription());
                if (project.getId() > 0) {
                    statement.setInt(3, project.getId());
                }
                statement.addBatch();
            }
            if (!(statement.executeBatch().length == projects.length)) {
                throw new SQLException(SAVE_PROJECTS_ERROR);
            }
            setProjectId(statement.getGeneratedKeys(), projects);
            saveTask(connection, getTasks(projects));
            return List.of(projects);
        }
    }

    private List<Task> saveTask(final Connection connection, final List<Task> tasks) throws SQLException {
        return taskRepository.saveAndFlush(connection, tasks);
    }

    private void setProjectId(final ResultSet generatedKeys, final Project[] projects) throws SQLException {
        int i = 0;
        while (generatedKeys.next()) {
            projects[i].setId(generatedKeys.getInt(1));
            i++;
        }
    }

    private Task setTask(final ResultSet resultSet, final int taskId) throws SQLException {
        return new Task.Builder()
                .id(taskId)
                .title(resultSet.getString(5))
                .description(resultSet.getString(6))
                .createdDate(resultSet.getTimestamp(7).toLocalDateTime())
                .dueDate(resultSet.getTimestamp(8).toLocalDateTime())
                .status(resultSet.getString(9))
                .assignee(resultSet.getInt(10) > 0 ? new User.Builder()
                        .id(resultSet.getInt(10))
                        .firstName(resultSet.getString(11))
                        .lastName(resultSet.getString(12))
                        .email(resultSet.getString(13))
                        .build() : null)
                .build();
    }

    private List<Task> getTasks(final Project[] projects) {
        Arrays.stream(projects)
                .forEach(p -> p.getTasks()
                        .forEach(t -> t.setProject(p)));
        return Arrays.stream(projects).flatMap(p -> p.getTasks().stream()).toList();
    }
}
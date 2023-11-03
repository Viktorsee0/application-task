package com.spg.applicationTask.api.repository.repositoryImpl;

import com.spg.applicationTask.api.model.Project;
import com.spg.applicationTask.api.model.Task;
import com.spg.applicationTask.api.model.User;
import com.spg.applicationTask.api.repository.UserRepository;
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
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.spg.applicationTask.engine.Constants.Messages.DELETE_USER_ERROR;
import static com.spg.applicationTask.engine.Constants.Messages.RECEIVED_USER_ERROR;
import static com.spg.applicationTask.engine.Constants.Messages.SAVE_USERS_ERROR;
import static com.spg.applicationTask.engine.Constants.Messages.UPDATE_USERS_ERROR;

@Component
public class UserRepositoryImpl implements UserRepository {

    @Inject
    private DataSource dataSource;

    @Override
    public Optional<User> getById(final int id) {
        final String sql = SQLUtils.Select.user();
        try (final Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql + " WHERE U.ID = ?")) {

            preparedStatement.setInt(1, id);
            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                User user = null;
                Project project = null;
                while (resultSet.next()) {
                    if (user == null) {
                        user = new User.Builder()
                                .id(resultSet.getInt(1))
                                .firstName(resultSet.getString(2))
                                .lastName(resultSet.getString(3))
                                .email(resultSet.getString(4))
                                .build();
                    }
                    if (project == null) {
                        if (resultSet.getInt(5) > 0) {
                            project = new Project.Builder()
                                    .id(resultSet.getInt(5))
                                    .name(resultSet.getString(6))
                                    .description(resultSet.getString(7))
                                    .build();
                            user.setProject(project);
                        }
                    }
                    if (resultSet.getInt(5) > 0) {
                        Task task = setupTask(resultSet);
                        project.addTask(task);
                    }
                }
                return Optional.ofNullable(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(RECEIVED_USER_ERROR, e);
        }
    }

    @Override
    public List<User> getUsers() {
        final String sql = SQLUtils.Select.user();
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                Map<Integer, Project> projects = new HashMap<>();
                Map<Integer, User> developers = new HashMap<>();
                while (resultSet.next()) {
                    User currentUser;
                    Project currentProject;
                    int userId = resultSet.getInt(1);
                    if (!developers.containsKey(userId)) {
                        currentUser = new User.Builder()
                                .id(userId)
                                .firstName(resultSet.getString(2))
                                .lastName(resultSet.getString(3))
                                .email(resultSet.getString(4))
                                .build();
                        developers.put(userId, currentUser);
                        int projectId = resultSet.getInt(5);
                        if (projectId > 0) {
                            currentProject = new Project.Builder()
                                    .id(projectId)
                                    .name(resultSet.getString(6))
                                    .description(resultSet.getString(7))
                                    .build();
                            projects.put(userId, currentProject);
                        }
                    }
                    if (resultSet.getInt(8) > 0) {
                        Task task = setupTask(resultSet);
                        projects.get(userId).addTask(task);
                    }
                }
                List<User> values = developers.values().stream().toList();
                values.forEach(u -> u.setProject(projects.get(u.getId())));
                return values;
            }
        } catch (SQLException e) {
            throw new RuntimeException(RECEIVED_USER_ERROR, e);
        }
    }

    @Override
    public List<User> saveAndFlush(final List<User> users) {
        Connection connection = null;
        List<User> result = new ArrayList<>();
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            result.addAll(saveAndFlush(connection, users));
        } catch (final SQLException e) {
            JDBCUtils.rollback(connection, e);
        } finally {
            JDBCUtils.close(connection);
        }
        return result;
    }

    @Override
    public boolean delete(final int id) {
        Connection connection = null;
        boolean result = false;
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

    private Task setupTask(ResultSet resultSet) throws SQLException {
        return new Task.Builder()
                .id(resultSet.getInt(8))
                .title(resultSet.getString(9))
                .description(resultSet.getString(10))
                .createdDate(resultSet.getTimestamp(11).toLocalDateTime())
                .dueDate(resultSet.getTimestamp(12).toLocalDateTime())
                .status(resultSet.getString(13))
                .assignee(resultSet.getInt(14) > 0 ? new User.Builder()
                        .id(resultSet.getInt(15))
                        .firstName(resultSet.getString(16))
                        .lastName(resultSet.getString(17))
                        .email(resultSet.getString(18))
                        .build() : null)
                .build();
    }

    private boolean delete(final Connection connection, final int id) throws SQLException {
        int result;
        try {
            final String sql = SQLUtils.Delete.users();
            try (final PreparedStatement statement =
                         connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                result = statement.executeUpdate();
                connection.commit();
            }
        } catch (final SQLException e) {
            throw new SQLException(DELETE_USER_ERROR, e);
        }
        return result == 1;
    }

    private List<User> saveAndFlush(final Connection connection, final List<User> users) throws SQLException {
        final Collection<User> toUpdate = new LinkedList<>();
        final Collection<User> toInsert = new LinkedList<>();
        List<User> result = new ArrayList<>();
        for (final User user : users) {
            if (user.getId() > 0) {
                toUpdate.add(user);
            } else {
                toInsert.add(user);
            }
        }
        final User[] savedUsers = new User[toUpdate.size() + toInsert.size()];
        if (toUpdate.size() > 0) {
            result.addAll(update(connection, toUpdate.toArray(new User[0])));
        }
        if (toInsert.size() > 0) {
            result.addAll(insert(connection, toInsert.toArray(new User[0])));
        }
        if (savedUsers.length > 0) {
            connection.commit();
        }
        return result;
    }

    private List<User> update(final Connection connection, final User[] users) throws SQLException {
        final String configsSql = SQLUtils.Update.user();
        try (final PreparedStatement statement =
                     connection.prepareStatement(configsSql, Statement.RETURN_GENERATED_KEYS)) {
            for (User user : users) {
                statement.setString(1, user.getFirstName());
                statement.setString(2, user.getLastName());
                statement.setString(3, user.getEmail());
                if (user.getProject().isPresent() && user.getProject().get().getId() > 0) {
                    statement.setInt(4, user.getProject().get().getId());
                } else {
                    statement.setNull(4, Types.INTEGER);
                }
                statement.setInt(5, user.getId());
                statement.addBatch();
            }
            if (!(statement.executeBatch().length == users.length)) {
                throw new SQLException(UPDATE_USERS_ERROR);
            }
            return List.of(users);
        }
    }

    private List<User> insert(final Connection connection, final User[] users) throws SQLException {
        final String configsSql = SQLUtils.Insert.user();
        try (final PreparedStatement statement =
                     connection.prepareStatement(configsSql, Statement.RETURN_GENERATED_KEYS)) {
            for (User user : users) {
                statement.setString(1, user.getFirstName());
                statement.setString(2, user.getLastName());
                statement.setString(3, user.getEmail());
                if (user.getProject().isPresent() && user.getProject().get().getId() > 0) {
                    statement.setInt(4, user.getProject().get().getId());
                } else {
                    statement.setNull(4, Types.INTEGER);
                }
                statement.addBatch();
            }
            if (!(statement.executeBatch().length == users.length)) {
                throw new SQLException(SAVE_USERS_ERROR);
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            for (User user : users) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));
                }
            }
            return List.of(users);
        }
    }
}
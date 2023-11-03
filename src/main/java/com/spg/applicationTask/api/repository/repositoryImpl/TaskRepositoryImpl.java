package com.spg.applicationTask.api.repository.repositoryImpl;

import com.spg.applicationTask.api.model.Task;
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
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static com.spg.applicationTask.engine.Constants.Messages.DELETE_TASK_ERROR;
import static com.spg.applicationTask.engine.Constants.Messages.SAVE_TASKS_ERROR;
import static com.spg.applicationTask.engine.Constants.Messages.UPDATE_TASKS_ERROR;

@Component
public class TaskRepositoryImpl implements TaskRepository {

    @Inject
    private DataSource dataSource;

    @Override
    public List<Task> saveAndFlush(final List<Task> tasks) {
        Connection connection = null;
        List<Task> result = new ArrayList<>();
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            result.addAll(saveAndUpdateAndCommit(connection, tasks.toArray(new Task[0])));
        } catch (final SQLException e) {
            JDBCUtils.rollback(connection, e);
        } finally {
            JDBCUtils.close(connection);
        }
        return result;
    }

    @Override
    public List<Task> saveAndFlush(Connection connection, final List<Task> tasks) throws SQLException {
        return saveAndUpdate(connection, tasks.toArray(new Task[0]));
    }

    @Override
    public boolean delete(int id) {
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
            final String sql = SQLUtils.Delete.tasks();
            try (final PreparedStatement statement =
                         connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                result = statement.executeUpdate();
                connection.commit();
            }
        } catch (final SQLException e) {
            throw new SQLException(DELETE_TASK_ERROR, e);
        }
        return result == 1;
    }

    private List<Task> saveAndUpdateAndCommit(final Connection connection, final Task[] tasks) throws SQLException {
        final Collection<Task> toUpdate = getToUpdate(tasks);
        final Collection<Task> toInsert = getToInsert(tasks);
        final Task[] savedTasks = new Task[toUpdate.size() + toInsert.size()];
        try {
            return execute(connection, toUpdate, toInsert);
        } finally {
            if (savedTasks.length > 0) {
                connection.commit();
            }
        }
    }

    private List<Task> saveAndUpdate(final Connection connection, final Task[] tasks) throws SQLException {
        final Collection<Task> toUpdate = getToUpdate(tasks);
        final Collection<Task> toInsert = getToInsert(tasks);
        return execute(connection, toUpdate, toInsert);
    }

    private List<Task> getToUpdate(final Task[] tasks) {
        return Arrays.stream(tasks).toList().stream().filter(t -> t.getId() > 0).toList();
    }

    private List<Task> getToInsert(final Task[] tasks) {
        return Arrays.stream(tasks).toList().stream().filter(t -> t.getId() == 0).toList();
    }

    private List<Task> update(final Connection connection, final Task[] tasks) throws SQLException {
        final String configsSql = SQLUtils.Update.task();
        return execute(connection, tasks, configsSql, UPDATE_TASKS_ERROR);
    }

    private List<Task> insert(final Connection connection, final Task[] tasks) throws SQLException {
        final String configsSql = SQLUtils.Insert.task();
        return execute(connection, tasks, configsSql, SAVE_TASKS_ERROR);
    }

    private List<Task> execute(Connection connection, Collection<Task> toUpdate,
                               Collection<Task> toInsert) throws SQLException {
        final List<Task> tasks = new ArrayList<>();
        if (toUpdate.size() > 0) {
            tasks.addAll(update(connection, toUpdate.toArray(new Task[0])));
        }
        if (toInsert.size() > 0) {
            tasks.addAll(insert(connection, toInsert.toArray(new Task[0])));
        }
        return tasks;
    }

    private List<Task> execute(Connection connection, final Task[] tasks, final String configsSql,
                               final String updateTasksError) throws SQLException {
        try (final PreparedStatement statement =
                     connection.prepareStatement(configsSql, Statement.RETURN_GENERATED_KEYS)) {
            for (Task task : tasks) {
                statement.setString(1, task.getTitle());
                statement.setString(2, task.getDescription());
                statement.setTimestamp(3, Timestamp.valueOf(task.getDueDate()));
                statement.setString(4, task.getStatus());
                if (task.getAssignee().isPresent() && task.getAssignee().get().getId() > 0) {
                    statement.setInt(5, task.getAssignee().get().getId());
                } else {
                    statement.setNull(5, Types.INTEGER);
                }
                if (task.getProject().isPresent() && task.getProject().get().getId() > 0) {
                    statement.setInt(6, task.getProject().get().getId());
                } else {
                    statement.setNull(6, Types.INTEGER);
                }
                if (task.getId() > 0) {
                    statement.setInt(7, task.getId());
                }
                statement.addBatch();
            }

            if (statement.executeBatch().length == tasks.length) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                for (Task task : tasks) {
                    if (generatedKeys.next()) {
                        task.setId(generatedKeys.getInt(1));
                    }
                }
                return List.of(tasks);
            } else {
                throw new SQLException(updateTasksError);
            }
        }
    }
}
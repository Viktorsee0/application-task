package com.spg.applicationTask.api.repository;

import com.spg.applicationTask.api.model.Task;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Provides repository methods to create, read, update and delete operations.
 */
public interface TaskRepository {

    /**
     * This method is used for updating or inserting list of tasks with commit
     * @param tasks tasks
     */
    List<Task> saveAndFlush(List<Task> tasks);

    /**
     * This method is used for updating or inserting list of tasks without commit
     *
     * @param connection - JDBC connection
     * @param tasks tasks
     * @return list of tasks witch was mapped by generated keys
     * @throws SQLException SQLException
     */
    List<Task> saveAndFlush(Connection connection, List<Task> tasks) throws SQLException;

    /**
     * Deletes task models.
     * @param id an id of task model.
     */
    boolean delete(final int id);
}

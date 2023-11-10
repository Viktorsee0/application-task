package com.spg.applicationTask.api.repository;

import com.spg.applicationTask.api.model.User;

import java.util.List;
import java.util.Optional;

/**
 * Provides repository methods to create, read, update and delete operations.
 */
public interface UserRepository {

    /**
     * Returns user models by user id.
     *
     * @param id - id of user model.
     * @return a user model.
     */
    Optional<User> getById(final int id);

    /**
     * Returns all user models.
     * @return a list of user models.
     */
    List<User> getUsers();

    /**
     * Saves and flushes user models.
     * @param users a list of user models.
     */
    List<User>  saveAndFlush(final List<User> users);

    /**
     * Deletes project models.
     * @param id an id of user model.
     */
    boolean delete(final int id);
}

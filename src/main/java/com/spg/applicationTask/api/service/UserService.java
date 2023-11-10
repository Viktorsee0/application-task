package com.spg.applicationTask.api.service;

import com.spg.applicationTask.api.dto.UserDTO;
import com.spg.applicationTask.api.repository.UserRepository;

import java.util.List;

/**
 * Provides service methods to create, read, update and delete operations.
 */
public interface UserService {

    /**
     * Returns all users models.
     *
     * @return a list of user models as DTO.
     * @see UserDTO
     */
    List<UserDTO> getUsers();

    /**
     * Returns user model by id.
     *
     * @return a user model as DTO.
     * @see UserDTO
     */
    UserDTO getUser(int id);

    /**
     * Updates/creates user models.
     *
     * @param users a list of userDTO.
     * @return a list of updated user models as DTO.
     * @see UserDTO
     */
    List<UserDTO> save(List<UserDTO> users);

    /**
     * Removes user model by id.
     *
     * @param id an id of user model.
     */
    void delete(int id);

    /**
     * Sets user repository.
     *
     * @param repository a userRepository.
     * @see UserRepository
     */
    void setUserRepository(UserRepository repository);
}

package com.spg.applicationTask.api.service;

import com.spg.applicationTask.api.dto.UserDTO;
import com.spg.applicationTask.api.repository.UserRepository;

import java.util.List;

public interface UserService {
    List<UserDTO> getUsers();

    UserDTO getUser(int id);

    List<UserDTO> save(List<UserDTO> users);

    void delete(int id);
    void setUserRepository(UserRepository userRepository);
}

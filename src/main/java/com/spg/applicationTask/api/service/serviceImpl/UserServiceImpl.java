package com.spg.applicationTask.api.service.serviceImpl;

import com.spg.applicationTask.api.dto.UserDTO;
import com.spg.applicationTask.api.mapper.UserMapper;
import com.spg.applicationTask.engine.IoC.annotation.Inject;
import com.spg.applicationTask.engine.IoC.annotation.Component;
import com.spg.applicationTask.api.model.User;
import com.spg.applicationTask.api.repository.UserRepository;
import com.spg.applicationTask.api.service.UserService;
import com.spg.applicationTask.engine.extension.Validator;
import com.spg.applicationTask.engine.web.exception.ElementDoseNotExistException;

import java.util.List;

import static com.spg.applicationTask.api.Constants.Messages.EXISTING_USER_ERROR;

@Component
public class UserServiceImpl implements UserService {

    @Inject
    private UserRepository userRepository;
    @Inject
    private UserMapper mapper;

    @Override
    public List<UserDTO> getUsers() {
        List<User> users = userRepository.getUsers();
        return mapper.toUserDto(users);
    }

    @Override
    public UserDTO getUser(final int id) {
        User user = userRepository.getById(id)
                .orElseThrow(() ->
                        new ElementDoseNotExistException(404, EXISTING_USER_ERROR));
        return mapper.toUserDto(user);
    }

    @Override
    public List<UserDTO> save(List<UserDTO> usersDto) {
        List<User> users = userRepository.saveAndFlush(mapper.toUser(usersDto));
        List<UserDTO> userDTOS = mapper.toUserDto(users);
        return userDTOS;
    }

    @Override
    public void delete(final int id) {
        Validator.of(userRepository.delete(id))
                .validate(result->result.equals(true), EXISTING_USER_ERROR)
                .get();
    }

    @Override
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}

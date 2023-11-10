package com.spg.applicationTask.api.mapper;

import com.spg.applicationTask.api.dto.AssigneeDTO;
import com.spg.applicationTask.api.dto.UserDTO;
import com.spg.applicationTask.api.model.User;
import com.spg.applicationTask.engine.IoC.annotation.Component;
import com.spg.applicationTask.engine.IoC.annotation.Inject;

import java.util.List;

/**
 * Provides method for mapping user to dto and dto to user
 */
@Component
public class UserMapper {

    @Inject
    private ProjectMapper mapper;

    /**
     * Maps the user dto with the user
     *
     * @param dto a user dto
     * @return user
     */
    public User toUser(final UserDTO dto) {
        return new User.Builder()
                .id(dto.getId())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .project(dto.getProject() != null ?
                        mapper.toProject(dto.getProject()) : null)
                .build();
    }

    /**
     * Maps the assignee dto with the user
     *
     * @param dto a assignee dto
     * @return user
     */
    public User toUser(final AssigneeDTO dto) {
        return new User.Builder()
                .id(dto.getId())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .build();
    }

    /**
     * Maps the list of user dto with the list of user
     *
     * @param dto a user dto
     * @return user
     */
    public List<User> toUser(final List<UserDTO> dto) {
        return dto.stream().map(this::toUser).toList();
    }

    /**
     * Maps the list of user with the list of user dto
     *
     * @param users a user
     * @return user dto
     */
    public List<UserDTO> toUserDto(final List<User> users) {
        return users.stream().map(this::toUserDto).toList();
    }

    /**
     * Maps the user with the user dto
     *
     * @param user a user
     * @return user dto
     */
    public UserDTO toUserDto(final User user) {
        return new UserDTO.Builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .project(user.getProject().isPresent() ?
                        mapper.toProjectDto(user.getProject().get()) : null)
                .build();
    }

    /**
     * Maps the user with the assignee dto
     *
     * @param user a user
     * @return assignee dto
     */
    public AssigneeDTO toAssigneeDto(final User user) {
        return new AssigneeDTO.Builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }
}

package com.spg.applicationTask.api.mapper;

import com.spg.applicationTask.api.dto.AssigneeDTO;
import com.spg.applicationTask.api.dto.UserDTO;
import com.spg.applicationTask.api.model.User;
import com.spg.applicationTask.engine.IoC.annotation.Component;
import com.spg.applicationTask.engine.IoC.annotation.Inject;

import java.util.List;

@Component
public class UserMapper {

    @Inject
    private ProjectMapper mapper;

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

    public User toUser(final AssigneeDTO dto) {
        return new User.Builder()
                .id(dto.getId())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .build();
    }

    public List<User> toUser(final List<UserDTO> dto) {
        return dto.stream().map(this::toUser).toList();
    }

    public List<UserDTO> toUserDto(final List<User> users) {
        return users.stream().map(this::toUserDto).toList();
    }

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

    public AssigneeDTO toAssigneeDto(final User user) {
        return new AssigneeDTO.Builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }
}

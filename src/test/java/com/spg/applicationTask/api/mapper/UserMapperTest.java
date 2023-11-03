package com.spg.applicationTask.api.mapper;

import com.spg.applicationTask.Main;
import com.spg.applicationTask.api.dto.AssigneeDTO;
import com.spg.applicationTask.api.dto.UserDTO;
import com.spg.applicationTask.api.model.User;
import com.spg.applicationTask.engine.IoC.Application;
import com.spg.applicationTask.engine.IoC.ApplicationContext;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

@DisplayName("User Mapper Test")
class UserMapperTest {

    private static UserMapper mapper;
    private static User user;
    private static UserDTO userDTO;
    private static AssigneeDTO assigneeDTO;

    @BeforeAll
    static void load() {
        user = new User.Builder()
                .id(1)
                .firstName("user")
                .lastName("user")
                .email("user@gmail.com")
                .build();
        userDTO = new UserDTO.Builder()
                .id(1)
                .firstName("user")
                .lastName("user")
                .email("user@gmail.com")
                .build();
        assigneeDTO = new AssigneeDTO.Builder()
                .id(1)
                .firstName("user")
                .lastName("user")
                .email("user@gmail.com")
                .build();

        ApplicationContext context = Application.run(Main.class);
        mapper = context.getObject(UserMapper.class);
    }

    @Test
    void toUser() {
        User result = mapper.toUser(userDTO);
        softAssert(result, userDTO);
    }

    @Test
    void toUsers() {
        List<User> result = mapper.toUser(List.of(userDTO));
        softAssert(result, List.of(userDTO));
    }

    @Test
    void toUserFromAssignee() {
        User result = mapper.toUser(assigneeDTO);
        softAssert(result, assigneeDTO);
    }

    @Test
    void toUserDto() {
        UserDTO result = mapper.toUserDto(user);
        softAssert(user, result);
    }

    @Test
    void toUserDtos() {
        List<UserDTO> result = mapper.toUserDto(List.of(user));
        softAssert(List.of(user), result);
    }

    @Test
    void toAssignee() {
        AssigneeDTO result = mapper.toAssigneeDto(user);
        softAssert(user, result);
    }

    private void softAssert(User user, UserDTO userDTO) {
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(user.getId()).isEqualTo(userDTO.getId());
        softAssertions.assertThat(user.getEmail()).isEqualTo(userDTO.getEmail());
        softAssertions.assertThat(user.getFirstName()).isEqualTo(userDTO.getFirstName());
        softAssertions.assertThat(user.getLastName()).isEqualTo(userDTO.getLastName());
        softAssertions.assertAll();
    }

    private void softAssert(User user, AssigneeDTO assigneeDTO) {
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(user.getId()).isEqualTo(assigneeDTO.getId());
        softAssertions.assertThat(user.getEmail()).isEqualTo(assigneeDTO.getEmail());
        softAssertions.assertThat(user.getFirstName()).isEqualTo(assigneeDTO.getFirstName());
        softAssertions.assertThat(user.getLastName()).isEqualTo(assigneeDTO.getLastName());
        softAssertions.assertAll();
    }

    private void softAssert(List<User> users, List<UserDTO> userDTOs) {
        SoftAssertions softAssertions = new SoftAssertions();
        for (int i = 0; i < users.size(); i++) {
            softAssertions.assertThat(userDTOs.get(i).getId()).isEqualTo(userDTOs.get(i).getId());
            softAssertions.assertThat(userDTOs.get(i).getEmail()).isEqualTo(userDTOs.get(i).getEmail());
            softAssertions.assertThat(userDTOs.get(i).getFirstName()).isEqualTo(userDTOs.get(i).getFirstName());
            softAssertions.assertThat(userDTOs.get(i).getLastName()).isEqualTo(userDTOs.get(i).getLastName());
        }
        softAssertions.assertAll();
    }
}
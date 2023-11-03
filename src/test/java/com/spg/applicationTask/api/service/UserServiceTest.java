package com.spg.applicationTask.api.service;

import com.spg.applicationTask.Main;
import com.spg.applicationTask.api.dto.UserDTO;
import com.spg.applicationTask.api.mapper.UserMapper;
import com.spg.applicationTask.api.model.User;
import com.spg.applicationTask.api.repository.UserRepository;
import com.spg.applicationTask.engine.IoC.Application;
import com.spg.applicationTask.engine.web.exception.ElementDoseNotExistException;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class UserServiceTest {

    private static UserRepository repository;
    private static ArgumentCaptor<Integer> integerArgumentCaptor;
    private static ArgumentCaptor<List<User>> userArgumentCaptor;
    private static UserService service;
    private static UserMapper mapper;
    private static List<UserDTO> userDTOS;
    private static List<User> repoRes;
    private static List<User> expectedUsers;

    @BeforeAll
    static void load() {
        Application.run(Main.class);
        repository = mock(UserRepository.class);
        service = Application.getContext().getObject(UserService.class);
        mapper = Application.getContext().getObject(UserMapper.class);
        service.setUserRepository(repository);
        integerArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        userArgumentCaptor = ArgumentCaptor.forClass(List.class);
        loadData();
    }

    @Test
    @DisplayName("Get user by id if project does not exist")
    void geUser() {
        doReturn(Optional.empty()).when(repository).getById(1);
        assertThatThrownBy(() -> service.getUser(1))
                .isInstanceOf(ElementDoseNotExistException.class);
    }

    @Test
    @DisplayName("Get user by id if  throw")
    void getUserIfThrow() {
        doReturn(Optional.empty()).when(repository).getById(anyInt());
        assertThatThrownBy(() -> service.getUser(1))
                .isInstanceOf(ElementDoseNotExistException.class);
    }

    @Test
    @DisplayName("Delete user by id if user does not exist")
    void deleteIfThrow() {
        doReturn(false).when(repository).delete(1);
        assertThatThrownBy(() -> service.delete(1))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("Delete user by id")
    void delete() {
        doReturn(true).when(repository).delete(1);
        service.delete(1);
        verify(repository, times(1))
                .delete(integerArgumentCaptor.capture());
        assertThat(integerArgumentCaptor.getValue())
                .isEqualTo(1);
    }

    @Test
    @DisplayName("Save user")
    void save() {
        doReturn(repoRes).when(repository).saveAndFlush(mapper.toUser(userDTOS));
        List<UserDTO> result = service.save(userDTOS);
        verify(repository, times(1)).saveAndFlush(userArgumentCaptor.capture());
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(result.size()).isEqualTo(repoRes.size());
        softAssertions.assertThat(userArgumentCaptor.getValue()).isEqualTo(mapper.toUser(userDTOS));
        softAssertions.assertAll();
    }

    @Test
    @DisplayName("Get all users")
    void getProjects() {
        doReturn(expectedUsers).when(repository).getUsers();
        List<UserDTO> result = service.getUsers();
        assertThat(mapper.toUser(result)).isEqualTo(expectedUsers);
    }

    static void loadData() {
        userDTOS = List.of(new UserDTO.Builder()
                .firstName("UPDATED USER 2")
                .lastName("UPDATED USER 2")
                .email("UPDATED2@gmail.com")
                .build());
        repoRes = List.of(new User.Builder()
                .id(1)
                .firstName("UPDATED USER 2")
                .lastName("UPDATED USER 2")
                .email("UPDATED2@gmail.com")
                .build());
        expectedUsers = List.of(new User.Builder()
                .id(1)
                .firstName("UPDATED USER 2")
                .lastName("UPDATED USER 2")
                .email("UPDATED2@gmail.com")
                .build());
    }
}
package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();

    UserDto getById(Long userId);

    UserDto saveUser(User user);

    UserDto updateUser(Long userId, UpdateUserRequest request);

    boolean delete(Long id);
}
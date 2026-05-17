package ru.practicum.shareit.user.mappers;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.user.NewUserRequest;
import ru.practicum.shareit.user.UpdateUserRequest;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {
    public static UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());

        return userDto;
    }

    public static User mapToUser(NewUserRequest request) {
        User user = new User();
        if (request.getName() == null || request.getName().isBlank()) {
            throw new NullPointerException("Не задано имя юзера");
        }
        user.setName(request.getName());
        user.setEmail(request.getEmail());

        return user;
    }

    public static User mapToUpUser(UpdateUserRequest request) {
        User user = new User();
        /*if (request.getName() == null || request.getName().isBlank()) {
            throw new NullPointerException("Не задано имя юзера");
        }*/
        user.setId(request.getId());
        user.setName(request.getName());
        user.setEmail(request.getEmail());

        return user;
    }
}
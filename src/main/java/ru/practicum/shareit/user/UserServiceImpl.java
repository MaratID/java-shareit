package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exeptions.ConflictExeption;
import ru.practicum.shareit.exeptions.NotFoundExeption;
import ru.practicum.shareit.exeptions.ValidationException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mappers.UserMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    @Override
    public List<UserDto> getAllUsers() {
        log.info("Получаем всех пользователей");

        return repository.findAll()
                .stream()
                .map(UserMapper::mapToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto saveUser(User user) {

        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new ValidationException("Имейл должен быть указан!");
        }
        // Проверка на существование пользователя
        Optional<User> existingUser = repository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new ConflictExeption("Пользователь с указанным email уже существует");
        }

        User savedUser = repository.save(user);
        return UserMapper.mapToUserDto(savedUser);
    }

    @Override
    public UserDto getById(Long userId) {
        Optional<User> existingUserOpt = repository.findById(userId);
        if (existingUserOpt.isEmpty()) {
            throw new NotFoundExeption("Пользователь с данным ID не найден");
        } else {
            return UserMapper.mapToUserDto(existingUserOpt.get());
        }
    }

    @Override
    public UserDto updateUser(Long userId, UpdateUserRequest request) {
        Optional<User> existingUserOpt = repository.findById(userId);
        if (existingUserOpt.isEmpty()) {
            throw new NotFoundExeption("Пользователь с данным ID не найден");
        }

        boolean existsWithSameEmail = repository.existsByEmailAndIdNot(request.getEmail(), userId);
        if (existsWithSameEmail) {
            throw new ConflictExeption("Пользователь с указанным email уже существует");
        }

        User updatedUser = repository.update(userId, UserMapper.mapToUpUser(request));
        return UserMapper.mapToUserDto(updatedUser);
    }

    @Override
    public boolean delete(Long id) {
        Optional<User> existingUserOpt = repository.findById(id);
        if (existingUserOpt.isEmpty()) {
            throw new NotFoundExeption("Пользователь с данным ID не найден");
        } else {
            User deletedUser = existingUserOpt.get();
            return repository.delete(deletedUser);
        }
    }
}
package ru.practicum.shareit.user;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<User> findAll();

    User save(User user);

    User update(Long userId, User newUser);

    Optional<User> findByEmail(String email);

    Optional<User> findById(Long userId);

    boolean existsByEmailAndIdNot(String email, Long id);

    boolean delete(User user);
}
package ru.practicum.shareit.user.storage;

import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.Optional;

public interface UserStorage {
    List<User> findAll();

    User create(User user);

    Optional<User> getById(Long id);

    User update(User user);

    void delete(Long id);
}

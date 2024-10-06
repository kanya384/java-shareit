package ru.practicum.shareit.user.storage;

import exception.DuplicateDataException;
import exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class UserInMemoryStorage implements UserStorage {
    private final Map<Long, User> users;
    private final Set<String> usedEmails;
    private Long nextId = 0L;

    @Override
    public List<User> findAll() {
        return users.values().stream().toList();
    }

    @Override
    public User create(User user) {
        if (usedEmails.contains(user.getEmail())) {
            throw new DuplicateDataException(String.format("Пользователь с email %s, уже существует", user.getEmail()));
        }
        user.setId(getNextId());
        users.put(user.getId(), user);
        usedEmails.add(user.getEmail());
        return user;
    }

    @Override
    public Optional<User> getById(Long id) {
        User user = users.get(id);
        if (user == null) {
            return Optional.empty();
        }

        return Optional.of(User.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build());
    }

    @Override
    public User update(User user) {
        Optional<User> oldUser = getById(user.getId());
        if (oldUser.isEmpty()) {
            throw new NotFoundException("Пользователь не найден");
        }

        if (!oldUser.get().getEmail().equals(user.getEmail())) {
            if (usedEmails.contains(user.getEmail())) {
                throw new DuplicateDataException(
                        String.format("Пользователь с email %s, уже существует", user.getEmail()));
            }

            usedEmails.remove(oldUser.get().getEmail());
            usedEmails.add(user.getEmail());
        }

        users.put(user.getId(), user);

        return user;
    }

    @Override
    public void delete(Long id) {
        if (!users.containsKey(id)) {
            throw new NotFoundException(String.format("Не найден пользователь с id = %d для удаления", id));
        }
        users.remove(id);
    }

    private Long getNextId() {
        return ++nextId;
    }
}

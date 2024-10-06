package ru.practicum.shareit.user.service;

import exception.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.dto.CreateUserRequest;
import ru.practicum.shareit.user.dto.UpdateUserRequest;
import ru.practicum.shareit.user.dto.UserMapper;
import ru.practicum.shareit.user.dto.UserResponse;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private UserStorage userStorage;

    @Override
    public UserResponse createUser(CreateUserRequest request) {
        User user = UserMapper.toUser(request);
        return UserMapper.toUserResponse(userStorage.create(user));
    }

    @Override
    public List<UserResponse> findAll() {
        return userStorage.findAll().stream().map(UserMapper::toUserResponse).toList();
    }

    @Override
    public UserResponse findById(Long userId) {
        return userStorage.getById(userId).map(UserMapper::toUserResponse)
                .orElseThrow(() -> new NotFoundException(String.format("Пользователь с id %d не найден", userId)));
    }

    @Override
    public UserResponse updateUser(Long userId, UpdateUserRequest request) {
        User updatedUser = userStorage.getById(userId)
                .map(user -> UserMapper.updateUserFields(user, request))
                .orElseThrow(() -> new NotFoundException("не найден пользователь с id = " + userId));
        return UserMapper.toUserResponse(userStorage.update(updatedUser));
    }

    @Override
    public void deleteUser(Long userId) {
        userStorage.delete(userId);
    }
}

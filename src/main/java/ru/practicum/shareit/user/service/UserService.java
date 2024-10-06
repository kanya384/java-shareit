package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.CreateUserRequest;
import ru.practicum.shareit.user.dto.UpdateUserRequest;
import ru.practicum.shareit.user.dto.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse createUser(CreateUserRequest user);

    List<UserResponse> findAll();

    UserResponse findById(Long userId);

    UserResponse updateUser(Long userId, UpdateUserRequest request);

    void deleteUser(Long userId);
}

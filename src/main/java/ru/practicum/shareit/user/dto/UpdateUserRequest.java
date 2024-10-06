package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UpdateUserRequest {

    @Email
    private String email;
    private String name;

    public boolean hasEmail() {
        return email != null;
    }

    public boolean hasName() {
        return name != null;
    }
}

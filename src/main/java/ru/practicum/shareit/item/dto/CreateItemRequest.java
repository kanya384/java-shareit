package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateItemRequest {
    @NotNull
    @NotBlank
    String name;

    @NotNull
    @NotBlank
    String description;

    @NotNull
    Boolean available;
}

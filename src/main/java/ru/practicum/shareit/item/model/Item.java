package ru.practicum.shareit.item.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * TODO Sprint add-controllers.
 */
@Data
public class Item {
    private Long id;
    @NotNull
    private Long userId;
    @NotBlank(message = "Имя вещи не может быть пустым")
    private String name;
    private String description;
    private String available;
}
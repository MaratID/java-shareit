package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * TODO Sprint add-controllers.
 */
@Data
public class ItemDto {
    private Long id;
    private Long userId;
    @NotBlank(message = "Имя вещи не может быть пустым")
    private String name;
    private String description;
    private String available;
}
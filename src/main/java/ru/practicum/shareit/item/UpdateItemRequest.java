package ru.practicum.shareit.item;

import lombok.Data;

@Data
public class UpdateItemRequest {
    private Long id;
    private Long userId;
    private String name;
    private String description;
    private String available;

    boolean hasName() {
        return !(name == null || name.isBlank());
    }

    boolean hasDescription() {
        return !(description == null || description.isBlank());
    }

    boolean hasAvailable() {
        return !(available == null || available.isBlank());
    }
}
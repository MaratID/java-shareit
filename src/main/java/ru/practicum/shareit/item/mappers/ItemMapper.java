package ru.practicum.shareit.item.mappers;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.item.UpdateItemRequest;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemMapper {
    public static ItemDto mapToItemDto(Item item) {
        ItemDto itemDto = new ItemDto();

        itemDto.setId(item.getId());
        itemDto.setUserId(item.getUserId());
        itemDto.setName(item.getName());
        itemDto.setDescription(item.getDescription());
        itemDto.setAvailable(item.getAvailable());

        return itemDto;
    }

    public static Item mapToItem(UpdateItemRequest request) {
        Item item = new Item();

        item.setId(request.getId());
        item.setUserId(request.getUserId());
        item.setName(request.getName());
        item.setDescription(request.getDescription());
        item.setAvailable(request.getAvailable());

        return item;
    }
}
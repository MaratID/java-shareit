package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {
    List<ItemDto> getItems(long userId);

    ItemDto getItemByUserAndId(Long userId, Long itemId);

    ItemDto addNewItem(Long userId, Item item);

    void deleteItem(long userId, long itemId);

    ItemDto update(Long userId, UpdateItemRequest request);

    List<ItemDto> searchItems(Long userID, String itemName);
}
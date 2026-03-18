package ru.practicum.shareit.item;

import ru.practicum.shareit.item.model.Item;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {
    List<Item> findByUserId(long userId);

    Optional<Item> findByName(String name);

    Optional<Item> findByUserIdAndItemId(Long userId, Long itemId);

    Item save(Long userId, Item item);

    Item update(Long userId, Item item);

    void deleteByUserIdAndItemId(long userId, long itemId);

    Optional<Item> findById(Long itemId);

    List<Item> findByNameAndUserId(Long userID, String itemName);
}
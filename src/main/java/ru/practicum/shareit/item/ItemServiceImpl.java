package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exeptions.NotFoundExeption;
import ru.practicum.shareit.exeptions.ValidationException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mappers.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Override
    public List<ItemDto> getItems(long userId) {
        return itemRepository.findByUserId(userId)
                .stream()
                .map(ItemMapper::mapToItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public ItemDto getItemByUserAndId(Long userId, Long itemId) {

        Optional<User> existingUserOpt = userRepository.findById(userId);

        Optional<Item> existingItemOpt = itemRepository.findById(itemId);
        if (existingUserOpt.isEmpty() || existingItemOpt.isEmpty()) {
            throw new NotFoundExeption("Отсутствует или юзер или вещь");
        }

        Optional<Item> it = itemRepository.findByUserIdAndItemId(userId, itemId);
        return ItemMapper.mapToItemDto(it.get());
    }

    @Override
    public ItemDto addNewItem(Long userId, Item item) {
        if (userId == null) {
            throw new ValidationException("Не задан id пользователя");
        }
        if (item.getAvailable() == null || item.getAvailable().isBlank()) {
            throw new ValidationException("Не задано поле доступности вещи");
        }
        if (item.getName() == null || item.getName().isBlank()) {
            throw new ValidationException("Не задано имя вещи");
        }
        if (item.getDescription() == null || item.getDescription().isBlank()) {
            throw new ValidationException("Не задано описание вещи");
        }

        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {
            item = itemRepository.save(userId, item);
            return ItemMapper.mapToItemDto(item);
        } else {
            throw new NotFoundExeption("Пользователь не найден");
        }
    }

    @Override
    public List<ItemDto> searchItems(Long userID, String itemName) {
        itemName = itemName.toUpperCase();
        return itemRepository.findByNameAndUserId(userID, itemName)
                .stream()
                .filter(item -> Boolean.parseBoolean(item.getAvailable()))
                .map(ItemMapper::mapToItemDto)
                .collect(Collectors.toList());

    }

    @Override
    public void deleteItem(long userId, long itemId) {
        itemRepository.deleteByUserIdAndItemId(userId, itemId);
    }

    @Override
    public ItemDto update(Long userId, UpdateItemRequest request) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new NotFoundExeption("Пользователь не найден");
        }

        Item item = itemRepository.update(userId, ItemMapper.mapToItem(request));
        return ItemMapper.mapToItemDto(item);
    }

}
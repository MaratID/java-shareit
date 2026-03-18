package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@Slf4j
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping
    public List<ItemDto> get(@RequestHeader("X-Sharer-User-Id") long userId) {
        return itemService.getItems(userId);
    }

    @GetMapping("/{itemId}")
    public ItemDto getItem(@RequestHeader("X-Sharer-User-Id") long userId, @PathVariable(name = "itemId") long itemId) {
        return itemService.getItemByUserAndId(userId, itemId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemDto add(@RequestHeader(value = "X-Sharer-User-Id", required = false) Long userId,
                       @RequestBody Item item) {
        return itemService.addNewItem(userId, item);
    }

    @DeleteMapping("/{itemId}")
    public void deleteItem(@RequestHeader("X-Sharer-User-Id") long userId,
                           @PathVariable(name = "itemId") long itemId) {
        itemService.deleteItem(userId, itemId);
    }

    @PatchMapping("/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    public ItemDto update(@Valid @RequestHeader("X-Sharer-User-Id") Long userId, @Valid @RequestBody UpdateItemRequest request) {
        return itemService.update(userId, request);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<ItemDto> searchItems(@RequestHeader("X-Sharer-User-Id") Long userId,
                                     @RequestParam("text") String searchString) {
        return itemService.searchItems(userId, searchString);
    }
}
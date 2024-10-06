package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CreateItemRequest;
import ru.practicum.shareit.item.dto.ItemResponse;
import ru.practicum.shareit.item.dto.UpdateItemRequest;
import ru.practicum.shareit.item.service.ItemService;

import java.util.List;

@RestController
@RequestMapping("/items")
@AllArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<ItemResponse> findAllItemsOfUser(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemService.findAllItemsOfUser(userId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public ItemResponse findItemById(@PathVariable("id") long itemId) {
        return itemService.getById(itemId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public ItemResponse create(@RequestHeader("X-Sharer-User-Id") Long userId, @Valid @RequestBody CreateItemRequest request) {
        return itemService.create(userId, request);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable("id") long itemId) {
        itemService.delete(itemId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}")
    public ItemResponse updateItem(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable("id") long itemId, @Valid @RequestBody UpdateItemRequest request) {
        return itemService.update(itemId, userId, request);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search")
    public List<ItemResponse> searchItemsByName(@RequestParam("text") String name) {
        return itemService.searchItemsByName(name);
    }
}

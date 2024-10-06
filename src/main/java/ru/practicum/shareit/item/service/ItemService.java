package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.CreateItemRequest;
import ru.practicum.shareit.item.dto.ItemResponse;
import ru.practicum.shareit.item.dto.UpdateItemRequest;

import java.util.List;

public interface ItemService {

    List<ItemResponse> findAllItemsOfUser(Long userId);

    List<ItemResponse> searchItemsByName(String name);

    ItemResponse create(Long userId, CreateItemRequest item);

    ItemResponse getById(Long id);

    ItemResponse update(Long itemId, Long userId, UpdateItemRequest item);

    void delete(Long id);
}

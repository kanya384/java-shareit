package ru.practicum.shareit.item.storage;

import ru.practicum.shareit.item.model.Item;

import java.util.List;
import java.util.Optional;

public interface ItemStorage {

    List<Item> findAllItemsOfUser(Long userId);

    List<Item> searchItemsByName(String name);

    Item create(Item item);

    Optional<Item> getById(Long id);

    Item update(Item item);

    void delete(Long id);
}

package ru.practicum.shareit.item.service;

import exception.ForbiddenException;
import exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dto.CreateItemRequest;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.dto.ItemResponse;
import ru.practicum.shareit.item.dto.UpdateItemRequest;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemStorage;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemStorage itemStorage;
    private final UserStorage userStorage;

    @Override
    public List<ItemResponse> findAllItemsOfUser(Long userId) {
        return itemStorage.findAllItemsOfUser(userId).stream().map(ItemMapper::toItemResponse).toList();
    }

    @Override
    public List<ItemResponse> searchItemsByName(String name) {
        if (name.isBlank()) {
            return new ArrayList<>();
        }
        return itemStorage.searchItemsByName(name).stream().map(ItemMapper::toItemResponse).toList();
    }

    @Override
    public ItemResponse create(Long userId, CreateItemRequest request) {
        Optional<User> user = userStorage.getById(userId);
        if (user.isEmpty()) {
            throw new NotFoundException(String.format("Пользователь с id %d не найден", userId));
        }

        Item item = ItemMapper.toItem(request);
        item.setOwner(user.get());

        return ItemMapper.toItemResponse(itemStorage.create(item));
    }

    @Override
    public ItemResponse getById(Long id) {
        return itemStorage.getById(id).map(ItemMapper::toItemResponse)
                .orElseThrow(() -> new NotFoundException(String.format("Вещь с id %d не найдена", id)));
    }

    @Override
    public ItemResponse update(Long itemId, Long userId, UpdateItemRequest request) {
        Optional<Item> oldItem = itemStorage.getById(itemId);
        if (oldItem.isEmpty()) {
            throw new NotFoundException(String.format("Вещь с id %d не найдена", userId));
        }

        if (!oldItem.get().getOwner().getId().equals(userId)) {
            throw new ForbiddenException("Нельзя редактировать вещь другого человека");
        }

        Item updatedItem = ItemMapper.updateItemFields(oldItem.get(), request);

        return ItemMapper.toItemResponse(itemStorage.update(updatedItem));
    }

    @Override
    public void delete(Long id) {
        itemStorage.delete(id);
    }
}

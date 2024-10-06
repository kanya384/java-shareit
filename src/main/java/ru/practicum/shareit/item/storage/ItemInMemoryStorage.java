package ru.practicum.shareit.item.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ItemInMemoryStorage implements ItemStorage {
    private final Map<Long, Item> items;
    private Long nextId = 0L;

    @Override
    public List<Item> findAllItemsOfUser(Long userId) {
        return items.values().stream()
                .filter(item -> item.getOwner().getId().equals(userId)).toList();
    }

    @Override
    public List<Item> searchItemsByName(String name) {
        return items.values().stream().filter(item -> item.getAvailable() && item.getName().toLowerCase().contains(name.toLowerCase())).toList();
    }

    @Override
    public Item create(Item item) {
        item.setId(getNextId());
        items.put(item.getId(), item);
        return item;
    }

    @Override
    public Optional<Item> getById(Long id) {
        Item item = items.get(id);
        if (item == null) {
            return Optional.empty();
        }

        return Optional.of(copyItem(item));
    }

    @Override
    public Item update(Item item) {
        items.put(item.getId(), item);
        return item;
    }

    @Override
    public void delete(Long id) {
        items.remove(id);
    }

    private Long getNextId() {
        return ++nextId;
    }

    private Item copyItem(Item item) {
        return Item.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .owner(item.getOwner())
                .request(item.getRequest())
                .build();
    }
}

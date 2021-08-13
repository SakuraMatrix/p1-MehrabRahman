package com.github.MehrabRahman.pokemart.service;

import com.github.MehrabRahman.pokemart.domain.Item;
import com.github.MehrabRahman.pokemart.repository.ItemRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ItemService {
    private ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Flux<Item> getAll() {
        return itemRepository.getAll();
    }

    public Mono<Item> get(String id) {
        return itemRepository.get(Integer.parseInt(id));
    }

    public Item create(Item item) {
        return itemRepository.create(item);
    }
}

package com.github.MehrabRahman.pokemart.service;

import com.github.MehrabRahman.pokemart.domain.Item;
import com.github.MehrabRahman.pokemart.repository.ItemRepository;
import reactor.core.publisher.Flux;

import java.util.List;

public class ItemService {
    private ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Flux<Item> getAll() {
        return Flux.fromIterable(itemRepository.getAll());
    }
}

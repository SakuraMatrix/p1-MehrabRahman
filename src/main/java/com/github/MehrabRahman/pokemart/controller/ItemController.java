package com.github.MehrabRahman.pokemart.controller;

import com.github.MehrabRahman.pokemart.domain.Item;
import com.github.MehrabRahman.pokemart.service.ItemService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/items")
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("")
    public Flux<Item> getAll() {
        return itemService.getAll();
    }

    @GetMapping("/{id}")
    public Mono<Item> get(@PathVariable("id") int id) {
        return itemService.get(id);
    }

    @PostMapping("")
    public Mono<Item> create(@RequestBody Item item) {
        return itemService.create(item);
    }
}

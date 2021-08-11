package com.github.MehrabRahman.pokemart.repository;

import com.github.MehrabRahman.pokemart.domain.Item;

import java.util.Arrays;
import java.util.List;

public class ItemRepository {
    public List<Item> getAll() {
        return Arrays.asList(new Item(1, "Potion", 20), new Item(2, "Pokeball", 40));
    }
}

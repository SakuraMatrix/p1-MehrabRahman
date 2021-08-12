package com.github.MehrabRahman.pokemart.repository;

import com.datastax.oss.driver.api.core.CqlSession;
import com.github.MehrabRahman.pokemart.domain.Item;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ItemRepository {
    private CqlSession session;

    public ItemRepository(CqlSession session) {
        this.session = session;
    }

    public Flux<Item> getAll() {
        return Flux.from(session.executeReactive("SELECT * FROM pokemart.item"))
                .map(row -> new Item(row.getInt("item_id"), row.getString("name"), row.getDouble("price")));
    }

    public Mono<Item> get(int id) {
        return Mono.from(session.executeReactive("SELECT * FROM pokemart.item WHERE item_id = " + id))
                .map(row -> new Item(row.getInt("item_id"), row.getString("name"), row.getDouble("price")));
    }
}

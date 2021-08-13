package com.github.MehrabRahman.pokemart.repository;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.github.MehrabRahman.pokemart.domain.Item;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class ItemRepository {
    private CqlSession session;

    public ItemRepository(CqlSession session) {
        this.session = session;
    }

    public Flux<Item> getAll() {
        return Flux.from(session.executeReactive("SELECT * FROM pokemart.items"))
                .map(row -> new Item(row.getInt("item_id"), row.getString("name"), row.getDouble("price")));
    }

    public Mono<Item> get(int id) {
        return Mono.from(session.executeReactive("SELECT * FROM pokemart.items WHERE item_id = " + id))
                .map(row -> new Item(row.getInt("item_id"), row.getString("name"), row.getDouble("price")));
    }

    public Item create(Item item) {
        SimpleStatement stmt = SimpleStatement.builder("INSERT INTO pokemart.items (item_id, name, price) values (?, ?, ?)")
                        .addPositionalValues(item.getId(), item.getName(), item.getPrice())
                                .build();
        Flux.from(session.executeReactive(stmt)).subscribe();
        return item;
    }
}

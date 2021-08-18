package com.github.MehrabRahman.pokemart.repository;

import com.github.MehrabRahman.pokemart.CassandraConfig;
import com.github.MehrabRahman.pokemart.domain.Item;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import reactor.test.StepVerifier;

@SpringJUnitConfig(classes = CassandraConfig.class)
public class ItemRepositoryTest {
    @Autowired
    ItemRepository itemRepository;

    @Test
    public void testAddPotion() {
        StepVerifier.create(itemRepository.save(new Item(1, "Potion", 12)))
                .consumeNextWith(Item::getName)
                .verifyComplete();
    }

    @Test
    public void testGetPotion() {
        StepVerifier.create(itemRepository.findById(1))
                .consumeNextWith(item -> Assertions.assertEquals(1, item.getId()))
                .verifyComplete();
    }
}

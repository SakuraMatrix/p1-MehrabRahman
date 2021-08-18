package com.github.MehrabRahman.pokemart;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringJUnitConfig(classes = AppConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AppTest {
    @Autowired
    ApplicationContext context;

    WebTestClient rest;

    @BeforeAll
    public void setup() {
        this.rest = WebTestClient
                .bindToApplicationContext(this.context)
                .configureClient()
                .build();
    }

    @Test
    public void getAllItemsOk() throws Exception {
        this.rest
                .get()
                .uri("/items")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void getOneItemOk() throws Exception {
        this.rest
                .get()
                .uri("/items/1")
                .exchange()
                .expectStatus().isOk();
    }
}

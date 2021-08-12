package com.github.MehrabRahman.pokemart;

import com.datastax.oss.driver.api.core.CqlSession;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.MehrabRahman.pokemart.domain.Item;
import com.github.MehrabRahman.pokemart.repository.ItemRepository;
import com.github.MehrabRahman.pokemart.service.ItemService;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import reactor.core.publisher.Mono;
import reactor.netty.http.server.HttpServer;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;

public class App {
    public static void main(String[] args) throws URISyntaxException {
        Path indexHTML = Paths.get(App.class.getResource("/index.html").toURI());
        Path errorHTML = Paths.get(App.class.getResource("/error.html").toURI());

        CqlSession session = CqlSession.builder().build();
        ItemRepository itemRepository = new ItemRepository(session);
        ItemService itemService = new ItemService(itemRepository);

        HttpServer.create()
            .port(8080)
            .route(routes ->
                routes.get("/items", (request, response) ->
                        response.send(itemService.getAll().map(App::toByteBuf)
                                .log("http-server")))
                    .get("/items/{param}", (request, response) ->
                        response.send(itemService.get(request.param("param")).map(App::toByteBuf)
                                .log("http-server")))
                    .get("/", (request, response) ->
                            response.sendFile(indexHTML))
                    .get("/error", (request, response) ->
                        response.status(404).addHeader("Message", "Goofed")
                                .sendFile(errorHTML))
                    )
            .bindNow()
            .onDispose()
            .block();
    }

    static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static ByteBuf toByteBuf(Object o) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            OBJECT_MAPPER.writeValue(out, o);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return ByteBufAllocator.DEFAULT.buffer().writeBytes(out.toByteArray());
    }
}

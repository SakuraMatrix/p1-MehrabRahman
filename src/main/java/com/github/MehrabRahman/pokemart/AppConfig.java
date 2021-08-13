package com.github.MehrabRahman.pokemart;

import com.datastax.oss.driver.api.core.CqlSession;
import com.github.MehrabRahman.pokemart.repository.ItemRepository;
import com.github.MehrabRahman.pokemart.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import reactor.netty.DisposableServer;
import reactor.netty.http.server.HttpServer;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
@ComponentScan
public class AppConfig {
    @Autowired
    ItemService itemService;

    @Bean
    public CqlSession session() {
        return CqlSession.builder().build();
    }

    @Bean
    public DisposableServer server() throws URISyntaxException {
        Path indexHTML = Paths.get(App.class.getResource("/index.html").toURI());
        Path errorHTML = Paths.get(App.class.getResource("/error.html").toURI());

        return HttpServer.create()
                .port(8080)
                .route(routes ->
                        routes.get("/items", (request, response) ->
                                        response.send(itemService.getAll()
                                                .map(App::toByteBuf)
                                                .log("http-server")))
                                .post("/items", (request, response) ->
                                        response.send(request.receive().asString()
                                                .map(App::parseItem)
                                                .map(itemService::create)
                                                .map(App::toByteBuf)
                                                .log("http-server")))
                                .get("/items/{param}", (request, response) ->
                                        response.send(itemService.get(request.param("param"))
                                                .map(App::toByteBuf)
                                                .log("http-server")))
                                .get("/", (request, response) ->
                                        response.sendFile(indexHTML))
                                .get("/error", (request, response) ->
                                        response.status(404).addHeader("Message", "Goofed")
                                                .sendFile(errorHTML))
                )
                .bindNow();
    }
}

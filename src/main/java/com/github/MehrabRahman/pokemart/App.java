package com.github.MehrabRahman.pokemart;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import reactor.netty.http.server.HttpServer;

import java.time.Duration;

public class App {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        applicationContext.getBean(HttpServer.class).bindUntilJavaShutdown(Duration.ofSeconds(60), null);
    }
}

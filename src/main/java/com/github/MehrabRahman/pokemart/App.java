package com.github.MehrabRahman.pokemart;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.MehrabRahman.pokemart.domain.Item;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import reactor.netty.DisposableServer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

public class App {
    static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static void main(String[] args) throws URISyntaxException {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

        applicationContext.getBean(DisposableServer.class)
                .onDispose()
                .block();
    }

    static ByteBuf toByteBuf(Object o) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            OBJECT_MAPPER.writeValue(out, o);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return ByteBufAllocator.DEFAULT.buffer().writeBytes(out.toByteArray());
    }

    static Item parseItem(String str) {
        Item item = null;
        try {
            item = OBJECT_MAPPER.readValue(str, Item.class);
        } catch (JsonProcessingException ex) {
            String[] params = str.split("&");
            int id = Integer.parseInt(params[0].split("=")[1]);
            String name = params[1].split("=")[1];
            double price = Double.parseDouble(params[2].split("=")[1]);
            item = new Item(id, name, price);
        }
        return item;
    }
}

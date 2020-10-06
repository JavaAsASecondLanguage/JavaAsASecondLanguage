package io.github.javaasasecondlanguage.homework02.webserver;

import io.github.javaasasecondlanguage.homework02.di.Context;
import io.github.javaasasecondlanguage.homework02.di.Injector;

import java.util.Map;
import java.util.concurrent.Executors;

public class Application {
    public static void initDI() {
        new Context()
                .register(8095, "port")
                .register("localhost", "host")
                .register(Executors.newFixedThreadPool(10))
                .register("Hello dear ", "welcomeText")
                .register((Logger) System.out::println)
                .register(Map.of("/test", new MyHttpHandler()))
                .register(new MyWebServer());
    }

    public static void main(String[] args) {
        initDI();
        var server = Injector.inject(WebServer.class);
        server.start();
    }
}

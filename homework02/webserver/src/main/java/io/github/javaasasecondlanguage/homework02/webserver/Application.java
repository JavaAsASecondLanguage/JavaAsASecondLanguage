package io.github.javaasasecondlanguage.homework02.webserver;

import io.github.javaasasecondlanguage.homework02.di.Context;
import io.github.javaasasecondlanguage.homework02.di.Injector;

import java.util.Map;
import java.util.concurrent.Executors;

public class Application {
    public static void initDI() {
        Context.getRoot().openScope("MyWebServer")
            .register(Map.class, () -> Map.of("/test", new MyHttpHandler()))
            .register(MyWebServer.class)
            .register(8080, "port")
            .register("localhost", "host")
            .register(Executors.newFixedThreadPool(10))
            .register(Map.class, () -> Map.of("/test", new MyHttpHandler()))
            .register("Hello dear ", "welcomeText")
            .register(MyLogger.class)
            .register(Logger.class, () -> (Logger) System.out::println);
        Injector.setResolutionScope("MyWebServer");
    }

    public static void main(String[] args) {
        initDI();
        var server = Injector.inject(WebServer.class);
        server.start();
    }
}

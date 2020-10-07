package io.github.javaasasecondlanguage.homework02.webserver;

import io.github.javaasasecondlanguage.homework02.di.Context;
import io.github.javaasasecondlanguage.homework02.di.Injector;

import java.util.Map;
import java.util.concurrent.Executors;

public class Application {
    public static void initDI() {
        Context.getContext()
                .register(() -> (Object) Map.of("/test", new MyHttpHandler()))
                .register(() -> (Object) new MyWebServer())
                .register(() -> 8080, "port")
                .register(() -> "localhost", "host")
                .register(() -> Executors.newFixedThreadPool(10))
                .register(() -> (Object) Map.of("/test", new MyHttpHandler()))
                .register(() -> "Hello dear ", "welcomeText")
                .register(() -> (Object) (Logger) System.out::println);
    }

    public static void main(String[] args) {
        initDI();
        var server = Injector.inject(WebServer.class);
        server.start();
    }
}

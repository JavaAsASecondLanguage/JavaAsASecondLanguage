package io.github.javaasasecondlanguage.homework02.webserver;

import io.github.javaasasecondlanguage.homework02.di.Context;
import io.github.javaasasecondlanguage.homework02.di.Injector;
import java.util.Map;
import java.util.concurrent.Executors;

public class Application {
    public static void initDI() {
        new Context()
                .register(5000, "port")
                .register("localhost", "host")
                .register("Hello dear ", "welcomeText")
                .register(new LoggerImpl())
                .register(Map.of("/test", new MyHttpHandler()))
                .register(Executors.newFixedThreadPool(10))
                .register(new MyWebServer());
    }

    public static void main(String[] args) {
        initDI();
        var server = Injector.inject(WebServer.class);
        server.start();
    }
}

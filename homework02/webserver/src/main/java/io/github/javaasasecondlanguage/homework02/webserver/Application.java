package io.github.javaasasecondlanguage.homework02.webserver;

import io.github.javaasasecondlanguage.homework02.di.Context;
import io.github.javaasasecondlanguage.homework02.di.Injector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.Executors;

public class Application {
    public static void initDI() {
        new Context()
                .register("Hello dear ", "welcomeText")
                .register(8080, "port")
                .register("localhost", "host")
                .register(Map.of("/test", new MyHttpHandler()))
                .register(Executors.newFixedThreadPool(10))
                .register(LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME))
                .register(new MyWebServer());
    }

    public static void main(String[] args) {
        initDI();
        var server = Injector.inject(WebServer.class);
        server.start();
    }
}

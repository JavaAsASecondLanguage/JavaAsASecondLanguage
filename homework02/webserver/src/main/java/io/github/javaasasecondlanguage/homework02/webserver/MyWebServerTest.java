package io.github.javaasasecondlanguage.homework02.webserver;

import io.github.javaasasecondlanguage.homework02.di.Context;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.concurrent.Executors;

public class MyWebServerTest {
    private MyWebServer webServer;

    @BeforeEach
    void setUp() {
        new Context()
                .register("Hello dear ", "welcomeText")
                .register(Map.of("/test", new MyHttpHandler()))
                //.register(new MyWebServer())
                .register(8080, "port")
                .register("localhost", "host")
                .register(Executors.newFixedThreadPool(10))
                .register(Map.of("/test", new MyHttpHandler()))
                //.register("Hello dear ", "welcomeText")
                //.register((Logger) System.out::println);
                .register(new LoggerImpl());
        webServer = new MyWebServer();
    }

    @Test
    void testStartStop() {
        Assertions.assertDoesNotThrow(webServer::start);
        Assertions.assertDoesNotThrow(webServer::stop);
    }
}

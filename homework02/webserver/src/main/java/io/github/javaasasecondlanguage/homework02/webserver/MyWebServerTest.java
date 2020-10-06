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
                .register(5000, "port")
                .register("localhost", "host")
                .register("Hello dear ", "welcomeText")
                .register(new LoggerImpl())
                .register(Map.of("/test", new MyHttpHandler()))
                .register(Executors.newFixedThreadPool(10));
        webServer = new MyWebServer();
    }

    @Test
    void testStartStop() {
        Assertions.assertDoesNotThrow(webServer::start);
        Assertions.assertDoesNotThrow(webServer::stop);
    }
}

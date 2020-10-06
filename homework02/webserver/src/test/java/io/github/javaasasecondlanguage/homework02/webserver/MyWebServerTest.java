package io.github.javaasasecondlanguage.homework02.webserver;

import io.github.javaasasecondlanguage.homework02.di.Context;
import io.github.javaasasecondlanguage.homework02.di.ContextRegistry;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyWebServerTest {
    private static final String HOST = "localhost";
    private static final int PORT = 8080;

    @BeforeEach
    void setupAll() {
        ContextRegistry.clearContext();
        new Context()
                .register(PORT, "port")
                .register(HOST, "host")
                .register(Executors.newFixedThreadPool(1))
                .register("Hello dear ", "welcomeText")
                .register(Map.of("/test", new MyHttpHandler()))
                .register((Logger) System.out::println)
                .register(LoggerFactory.getLogger("default"))
                .register(LoggerFactory.getLogger("errorLogger"), "errorLogger");
    }

    @Test
    void shouldReturn200OnKnownResource() throws IOException, InterruptedException {
        var webserver = new MyWebServer();
        webserver.start();
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(
                        String.format("http://%s:%d/%s?name=%s", HOST, PORT, "test", "TEST_NAME")
                ))
                .build();

        HttpResponse<String> response = client.send(
                request, HttpResponse.BodyHandlers.ofString()
        );

        assertEquals(200, response.statusCode());
        webserver.stop();
    }

    @Test
    void shouldReturn404OnUnknownResource() throws IOException, InterruptedException {
        var webserver = new MyWebServer();
        webserver.start();
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(
                        String.format("http://%s:%d/%s", HOST, PORT, "smth")
                ))
                .build();

        HttpResponse<String> response = client.send(
                request, HttpResponse.BodyHandlers.ofString()
        );

        assertEquals(404, response.statusCode());
        webserver.stop();
    }

}

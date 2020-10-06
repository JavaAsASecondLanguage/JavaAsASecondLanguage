package io.github.javaasasecondlanguage.homework02.webserver;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.ByteBuffer;
import java.util.concurrent.Flow;

import static io.github.javaasasecondlanguage.homework02.di.Injector.inject;
import static org.junit.jupiter.api.Assertions.*;

public class WebServerEnd2EndTest {
    WebServer server = inject(WebServer.class);
    String host = inject(String.class, "host");
    int port = inject(Integer.class, "port");

    @BeforeAll
    static void setupAll() {
        Application.initDI();
    }

    @BeforeEach
    void setup() {
        server.start();
    }

    @AfterEach
    void tearDown() {
        server.stop();
    }

    @Test
    void getResponseTest() throws IOException, InterruptedException {
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(
                        String.format("http://%s:%d/%s?name=%s", host, port, "test", "TEST_NAME")
                ))
                .build();

        HttpResponse<String> response = client.send(
                request, HttpResponse.BodyHandlers.ofString()
        );

        assertEquals(200, response.statusCode());
        assertTrue(response.body().contains("Hello dear TEST_NAME"));


        final HttpClient client2 = HttpClient.newHttpClient();
        final HttpRequest request2 = HttpRequest.newBuilder()
                .POST(new HttpRequest.BodyPublisher() {
                    @Override
                    public long contentLength() {
                        return 0;
                    }

                    @Override
                    public void subscribe(Flow.Subscriber<? super ByteBuffer> subscriber) {
                    }
                })
                .uri(URI.create(
                        String.format("http://%s:%d/%s?name=%s", host, port, "test", "TEST_NAME")
                ))
                .build();
        assertThrows(IOException.class, () -> client2.send(
                request2, HttpResponse.BodyHandlers.ofString()
        ));
    }



}
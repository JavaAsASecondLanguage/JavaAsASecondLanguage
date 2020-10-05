package io.github.javaasasecondlanguage.homework02.webserver;

import com.sun.net.httpserver.HttpExchange;
import io.github.javaasasecondlanguage.homework02.di.Context;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MyHttpHandlerTest {
    private static final String welcomeText = "test";

    @BeforeAll
    static void setupAll() {
        new Context()
                .register(welcomeText, "welcomeText")
                .register(LoggerFactory.getLogger("test"));
    }

    @Test
    void shouldHandleGetRequests() throws Exception {
        var httpHandler = new MyHttpHandler();
        var buffer = new ByteArrayOutputStream();
        var httpExchange = createMockHttpExchange("GET", buffer);
        httpHandler.handle(httpExchange);
        assertTrue(buffer.toString().contains(welcomeText));
    }

    @Test
    void shouldFailForOtherRequestMethods() throws Exception {
        var httpHandler = new MyHttpHandler();
        var buffer = new ByteArrayOutputStream();
        var httpExchange = createMockHttpExchange("POST", buffer);
        assertThrows(RuntimeException.class, () -> httpHandler.handle(httpExchange));
    }

    private HttpExchange createMockHttpExchange(String method, OutputStream buffer)
            throws URISyntaxException {
        var httpExchange = mock(HttpExchange.class);
        when(httpExchange.getRequestMethod()).thenReturn(method);
        when(httpExchange.getRequestURI()).thenReturn(new URI("https://test.com/?foo=bar"));
        when(httpExchange.getResponseBody()).thenReturn(buffer);
        return httpExchange;
    }
}

package io.github.javaasasecondlanguage.homework02.webserver;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpPrincipal;
import io.github.javaasasecondlanguage.homework02.di.Context;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URI;

class MyHttpHandlerTests {

    private MyHttpHandler httpHandler;

    @BeforeEach
    void setUp() {
        new Context()
                .register("Hello dear ", "welcomeText")
                .register(new LoggerImpl());
        httpHandler = new MyHttpHandler();
    }

    @Test
    void testHandleGetRequest() throws IOException {
        httpHandler.handle(getHttpExchange("GET"));
    }

    @Test
    void testHandlePostRequest() {
        Executable executable = () -> httpHandler.handle(getHttpExchange("POST"));
        Assertions.assertThrows(RuntimeException.class, executable);
    }

    private HttpExchange getHttpExchange(String httpMethod) {
        return new HttpExchange() {
            @Override
            public Headers getRequestHeaders() {
                return new Headers();
            }

            @Override
            public Headers getResponseHeaders() {
                return new Headers();
            }

            @Override
            public URI getRequestURI() {
                return URI.create("www.google.com?asdf=1234");
            }

            @Override
            public String getRequestMethod() {
                return httpMethod;
            }

            @Override
            public HttpContext getHttpContext() {
                return null;
            }

            @Override
            public void close() {

            }

            @Override
            public InputStream getRequestBody() {
                return new ByteArrayInputStream("asdfasd".getBytes());
            }

            @Override
            public OutputStream getResponseBody() {
                return new ByteArrayOutputStream();
            }

            @Override
            public void sendResponseHeaders(int code, long responseLength) {

            }

            @Override
            public InetSocketAddress getRemoteAddress() {
                return InetSocketAddress.createUnresolved("www.google.com", 8080);
            }

            @Override
            public int getResponseCode() {
                return 200;
            }

            @Override
            public InetSocketAddress getLocalAddress() {
                return InetSocketAddress.createUnresolved("localhost", 80);
            }

            @Override
            public String getProtocol() {
                return "HTTP";
            }

            @Override
            public Object getAttribute(String name) {
                return name;
            }

            @Override
            public void setAttribute(String name, Object value) {

            }

            @Override
            public void setStreams(InputStream i, OutputStream o) {

            }

            @Override
            public HttpPrincipal getPrincipal() {
                return new HttpPrincipal("user", "realm");
            }
        };
    }
}
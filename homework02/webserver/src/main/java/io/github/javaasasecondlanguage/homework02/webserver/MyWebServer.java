package io.github.javaasasecondlanguage.homework02.webserver;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.Executor;

import static io.github.javaasasecondlanguage.homework02.di.Injector.inject;

/**
 * https://dzone.com/articles/simple-http-server-in-java
 */
public class MyWebServer implements WebServer {
    private static final Logger log = inject(Logger.class);
    private static final org.slf4j.Logger defaultLog = inject(org.slf4j.Logger.class);
    private static final org.slf4j.Logger errorLog = inject(org.slf4j.Logger.class, "errorLogger");
    private String host = inject(String.class, "host");
    private int port = inject(Integer.class, "port");
    private Executor executor = inject(Executor.class);
    private Map<String, HttpHandler> handlers = inject(Map.class);

    private HttpServer server;

    @Override
    public void start() {
        if (server == null) {
            init();
        }
        log.info("Starting server");
        server.start();
    }

    @Override
    public void stop() {
        log.info("Stopping server");
        server.stop(0);
    }

    public void init() {
        try {
            server = HttpServer.create(new InetSocketAddress(host, port), 0);
        } catch (IOException e) {
            log.info("Oops " + e.getMessage());
        } catch (RuntimeException e) {
            errorLog.error("Oops x2 ", e);
        }
        server.setExecutor(executor);
        handlers.forEach(server::createContext);
    }

}

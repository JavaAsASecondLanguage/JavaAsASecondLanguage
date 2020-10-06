package io.github.javaasasecondlanguage.homework02.webserver;

import com.sun.net.httpserver.HttpHandler;
import io.github.javaasasecondlanguage.homework02.di.Context;
import io.github.javaasasecondlanguage.homework02.di.Injector;

import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

public class Application {
    public static void initDI() {
        final int STCK_LVL_CALL = 8;
        Supplier<WebServer> webServerFactory = () -> new MyWebServer();
        Supplier<Map<String, HttpHandler>> handlerFactory =
            () -> Map.of("/test", new MyHttpHandler());
        Supplier<ExecutorService> executorServiceFactory =
            () -> Executors.newFixedThreadPool(10);
        Supplier<Logger> loggerFactory = () -> new LoggerImpl();
        Supplier<Integer> portFactory = () -> 8080;
        Supplier<String> hostFactory = () -> "localhost";
        Supplier<String> welcomeTextFactory = () -> "Hello dear ";
        Supplier<java.util.logging.Logger> stdLogger =
            () -> java.util.logging.Logger.getLogger(
                        Thread.currentThread()
                                .getStackTrace()[STCK_LVL_CALL]
                                .getClassName());

        new Context()
                .register(Map.class, handlerFactory)
                .register(WebServer.class, webServerFactory)
                .register("port", portFactory)
                .register("host", hostFactory)
                .register(Executor.class, executorServiceFactory)
                .register(Map.class, handlerFactory)
                .register("welcomeText", welcomeTextFactory)
                .register(Logger.class, loggerFactory)
                .register(java.util.logging.Logger.class, stdLogger);
    }

    public static void main(String[] args) {
        initDI();
        var server = Injector.inject(WebServer.class);
        server.start();
    }
}

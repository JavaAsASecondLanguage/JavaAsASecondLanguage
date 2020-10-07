package io.github.javaasasecondlanguage.homework02.webserver;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

import static io.github.javaasasecondlanguage.homework02.di.Injector.inject;
import io.github.javaasasecondlanguage.homework02.webserver.Logger.LogLevel;

public class MyHttpHandler implements HttpHandler {
    public static final Logger log = inject(Logger.class);
    private final LogLevel httpRequestLogLevel =
        inject(LogLevel.class, "httpRequestLogLevel");
    private final LogLevel httpErrorLogLevel =
        inject(LogLevel.class, "httpErrorLogLevel");
    private String welcomeText = inject(String.class, "welcomeText");

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        var requestMethod = httpExchange.getRequestMethod();
        log.info("Received " + requestMethod  + "request");
        log.info(httpRequestLogLevel, "Received " + requestMethod  + " request");

        String requestParamValue = switch (requestMethod) {
            case "GET" -> handleGetRequest(httpExchange);
            default -> {
                log.info(httpErrorLogLevel, "method " + requestMethod + " not supported");
                throw new RuntimeException("not supported");
            }
        };

        handleResponse(httpExchange, requestParamValue);
    }

    private String handleGetRequest(HttpExchange httpExchange) {
        return httpExchange.getRequestURI()
                .toString()
                .split("\\?")[1]
                .split("=")[1];
    }

    private void handleResponse(HttpExchange httpExchange, String requestParamValue)
            throws IOException {

        OutputStream outputStream = httpExchange.getResponseBody();
        StringBuilder htmlBuilder = new StringBuilder();

        htmlBuilder.append("<html>")
                .append("<body>")
                .append("<h1>")
                .append(welcomeText)
                .append(requestParamValue)
                .append("</h1>")
                .append("</body>")
                .append("</html>");
        String htmlResponse = htmlBuilder.toString();
        // this line is a must
        httpExchange.sendResponseHeaders(200, htmlResponse.length());
        outputStream.write(htmlResponse.getBytes());
        outputStream.flush();
        outputStream.close();
    }
}

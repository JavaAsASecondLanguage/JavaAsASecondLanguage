package io.github.javaasasecondlanguage.chat.client;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class ChatClient {
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final String PROTOCOL = "http://";
    private static final String HOST = "3.236.217.35";
    private static final String PORT = ":8080";

    //POST host:port/chat/login?name=my_name
    public static HttpResponse<String> login(String name) throws IOException, InterruptedException {
        var request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(""))
                .header("Content-Type", "application/x-www-form-urlencoded")

                .uri(URI.create(PROTOCOL + HOST + PORT + "/chat/login?name=" + name))
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    //GET host:port/chat/chat
    public static HttpResponse<String> viewChat() throws IOException, InterruptedException {
        var request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(PROTOCOL + HOST + PORT + "/chat/chat"))
                .build();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }

    //POST host:port/chat/say?name=my_name
    //Body: "msg='my_message'"
    public static HttpResponse<?> say(String name, String msg)
            throws IOException, InterruptedException {
        var request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(String.format("msg=%s", msg)))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .uri(URI.create(PROTOCOL + HOST + PORT + "/chat/say?name=" + name))
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    //GET host:port/chat/online
    public static HttpResponse<?> viewOnline() throws IOException, InterruptedException {
        var request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(PROTOCOL + HOST + PORT + "/chat/online"))
                .build();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }
}
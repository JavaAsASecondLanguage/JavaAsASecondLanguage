package io.github.javaasasecondlanguage.lecture07.practice1;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Objects;

/**
 * Public hacker news API
 * https://github.com/HackerNews/API
 * 
 * <p>
 * Get latest topic
 * https://hacker-news.firebaseio.com/v0/maxitem.json
 * <p>
 * Get item data
 * https://hacker-news.firebaseio.com/v0/item/24900000.json
 */
public class HackerNewsClient {
    private static final OkHttpClient okHttpClient = new OkHttpClient();

    public static String getLatestContentId() throws IOException {
        Request request = new Request.Builder()
                .url("https://hacker-news.firebaseio.com/v0/maxitem.json")
                .build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            return Objects.requireNonNull(response.body()).string();
        }
    }

    public static String getContent(Integer id) throws IOException {
        Request request = new Request.Builder()
                .url("https://hacker-news.firebaseio.com/v0/item/" + id + ".json")
                .build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            return Objects.requireNonNull(response.body()).string();
        }
    }
}

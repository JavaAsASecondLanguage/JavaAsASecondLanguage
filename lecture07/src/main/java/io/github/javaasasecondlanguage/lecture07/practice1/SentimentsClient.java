package io.github.javaasasecondlanguage.lecture07.practice1;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Objects;

/**
 * Public sentiment analysis API
 * https://deepai.org/machine-learning-model/sentiment-analysis
 * <p>
 * Requires registration, has 10000 free requests
 */
public class SentimentsClient {
    private static final String API_KEY = "INPUT YOUR APPKEY HERE";

    private static final OkHttpClient okHttpClient = new OkHttpClient();

    public static String sentiments(String text) throws IOException {
        FormBody body = new FormBody.Builder().add("text", text).build();

        Request request = new Request.Builder()
                .url("https://api.deepai.org/api/sentiment-analysis")
                .header("api-key", API_KEY)
                .post(body)
                .build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            return Objects.requireNonNull(response.body()).string();
        }
    }
}

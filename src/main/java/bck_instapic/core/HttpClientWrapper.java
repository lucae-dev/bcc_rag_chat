package bck_instapic.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * A small wrapper around OkHttp + Jackson for JSON-based REST calls.
 */
public class HttpClientWrapper {

    private final OkHttpClient client;
    private final ObjectMapper objectMapper;
    private final String baseUrl;
    private final String apiToken;

    public HttpClientWrapper(String baseUrl, String apiToken) {
        this.baseUrl = baseUrl.endsWith("/")
                ? baseUrl.substring(0, baseUrl.length() - 1)
                : baseUrl;
        this.apiToken = apiToken;
        this.objectMapper = new ObjectMapper(); // configure if needed

        // Build a single OkHttpClient (thread-safe, connection pool, etc.)
        this.client = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .callTimeout(45, TimeUnit.SECONDS)
                .build();
    }

    /**
     * Perform a GET request and parse the JSON response into type T.
     */
    public <T> T get(String path, Class<T> responseType) throws IOException {
        Request request = new Request.Builder()
                .url(baseUrl + path)
                .addHeader("Authorization", "Bearer " + apiToken)
                .addHeader("Content-Type", "application/json")
                .addHeader("User-Agent", "replicate-java/1.0") // or your own
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            checkResponse(response);
            String bodyString = Objects.requireNonNull(response.body()).string();
            return objectMapper.readValue(bodyString, responseType);
        }
    }

    /**
     * Perform a POST request with a JSON body, parse the JSON response into type T.
     */
    public <T> T post(String path, Object body, Class<T> responseType) throws IOException {
        RequestBody requestBody = (body == null)
                ? RequestBody.create("", MediaType.parse("application/json"))
                : RequestBody.create(
                objectMapper.writeValueAsString(body),
                MediaType.parse("application/json")
        );

        Request request = new Request.Builder()
                .url(baseUrl + path)
                .addHeader("Authorization", "Bearer " + apiToken)
                .addHeader("Content-Type", "application/json")
                .addHeader("User-Agent", "replicate-java/1.0")
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            checkResponse(response);
            String bodyString = Objects.requireNonNull(response.body()).string();
            return objectMapper.readValue(bodyString, responseType);
        }
    }

    /**
     * Perform a DELETE request (no response body expected).
     */
    public void delete(String path) throws IOException {
        Request request = new Request.Builder()
                .url(baseUrl + path)
                .addHeader("Authorization", "Bearer " + apiToken)
                .addHeader("Content-Type", "application/json")
                .addHeader("User-Agent", "replicate-java/1.0")
                .delete()
                .build();

        try (Response response = client.newCall(request).execute()) {
            checkResponse(response);
        }
    }

    /**
     * Check the HTTP response code and throw an exception if not 2xx.
     */
    private void checkResponse(Response response) throws IOException {
        if (!response.isSuccessful()) {
            String errorBody = (response.body() != null) ? response.body().string() : "null";
            throw new IOException("HTTP " + response.code() + ": " + errorBody);
        }
    }

    // (Optionally) add other methods like PUT, etc.
}

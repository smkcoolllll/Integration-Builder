package com.cloudEagle.integration.AI_Generated.Integration.Builder.Service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ApiClientService {

    private final Gson gson = new Gson();

    public ApiResponse callExternalApi(String fullUrl, String method,
                                       Map<String, String> headers,
                                       String requestBody, int timeout) {

        HttpURLConnection connection = null;

        try {
            URL url = new URL(fullUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setConnectTimeout(timeout);
            connection.setReadTimeout(timeout);
            connection.setRequestProperty("Accept", "application/json");

            if (headers != null) {
                headers.forEach(connection::setRequestProperty);
            }

            if ((method.equalsIgnoreCase("POST") || method.equalsIgnoreCase("PUT")) &&
                    requestBody != null && !requestBody.isEmpty()) {

                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json");
                try (OutputStream os = connection.getOutputStream()) {
                    os.write(requestBody.getBytes(StandardCharsets.UTF_8));
                }
            }

            int statusCode = connection.getResponseCode();
            String body = readResponse(connection);
            boolean success = statusCode >= 200 && statusCode < 300;

            return new ApiResponse(statusCode, body, success);

        } catch (Exception e) {
            return new ApiResponse(0, e.getMessage(), false);
        } finally {
            if (connection != null) connection.disconnect();
        }
    }

    public List<Map<String, Object>> extractArrayFromResponse(String json, String arrayPath) {
        List<Map<String, Object>> result = new ArrayList<>();
        if (json == null || json.isEmpty()) return result;

        try {
            JsonObject root = gson.fromJson(json, JsonObject.class);
            JsonElement element = root;

            for (String part : arrayPath.split("\\.")) {
                if (element != null && element.isJsonObject()) {
                    element = element.getAsJsonObject().get(part);
                } else return result;
            }

            if (element != null && element.isJsonArray()) {
                JsonArray arr = element.getAsJsonArray();
                for (JsonElement item : arr) {
                    if (item.isJsonObject()) {
                        result.add(gson.fromJson(item, Map.class));
                    }
                }
            }
        } catch (Exception e) {
            // Log silently, return empty list
        }

        return result;
    }

    public Map<String, Object> mapFields(Map<String, Object> rawData, Map<String, String> mappings) {
        Map<String, Object> mapped = new HashMap<>();
        if (mappings == null || rawData == null) return mapped;

        mappings.forEach((target, source) -> {
            Object v = rawData.get(source);
            if (v != null) mapped.put(target, v);
        });

        return mapped;
    }

    private String readResponse(HttpURLConnection connection) throws IOException {
        InputStream stream = connection.getResponseCode() >= 400
                ? connection.getErrorStream()
                : connection.getInputStream();

        if (stream == null) return "";

        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) sb.append(line);
        }
        return sb.toString();
    }

    public record ApiResponse(int statusCode, String body, boolean success) {}
}

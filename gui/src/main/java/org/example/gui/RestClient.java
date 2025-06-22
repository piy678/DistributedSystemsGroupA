package org.example.gui;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class RestClient {

    private static final HttpClient client = HttpClient.newHttpClient();
    private static final String BASE_URL = "http://localhost:8080/api";

    private static final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    public static CurrentStatus fetchCurrentData() throws Exception {
        var request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/percentage/current"))
                .build();

        var response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.body() == null || response.body().isBlank() || response.body().equals("null")) {
            return null;
        }

        return mapper.readValue(response.body(), CurrentStatus.class);
    }

    public static List<UsageEntry> fetchHistoricalData(String start, String end) throws Exception {
        String encodedStart = URLEncoder.encode(start, StandardCharsets.UTF_8);
        String encodedEnd = URLEncoder.encode(end, StandardCharsets.UTF_8);

        var request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/usage?start=" + encodedStart + "&end=" + encodedEnd))
                .build();

        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Request: " + BASE_URL + "/usage?start=" + start + "&end=" + end);
        System.out.println("Response: " + response.body());

        return mapper.readValue(response.body(), new TypeReference<List<UsageEntry>>() {});
    }

    public static List<EnergySummary> fetchSummary(int hours) throws Exception {
        var request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/summary?hours=" + hours))
                .build();

        var response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return mapper.readValue(response.body(), new TypeReference<List<EnergySummary>>() {});
    }
}

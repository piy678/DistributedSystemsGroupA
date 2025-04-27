package org.example.gui;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

import org.json.JSONException;
import org.json.JSONObject;

public class MainController {

    @FXML
    private Label communityPoolLabel;

    @FXML
    private Label gridPortionLabel;

    @FXML
    private TextField startField;

    @FXML
    private TextField endField;

    @FXML
    private TextArea historicalDataArea;

    private final HttpClient client = HttpClient.newHttpClient();

    @FXML
    public void refreshData() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/energy/current"))
                .build();

        CompletableFuture<Void> exceptionally = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(this::updateCurrentData)
                .exceptionally(e -> {
                    e.printStackTrace();
                    return null;
                });
    }

    private void updateCurrentData(String response) {
        try {
            JSONObject obj = new JSONObject(response);

            double communityDepleted = obj.getDouble("communityDepleted");
            double gridPortion = obj.getDouble("gridPortion");

            Platform.runLater(() -> {
                communityPoolLabel.setText(String.format("%.2f%% used", communityDepleted));
                gridPortionLabel.setText(String.format("%.2f%%", gridPortion));
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void showHistoricalData() {
        String start = startField.getText();
        String end = endField.getText();

        String uri = "http://localhost:8080/energy/historical?start=" + start + "&end=" + end;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(this::updateHistoricalData)
                .exceptionally(e -> { e.printStackTrace(); return null; });
    }

    private void updateHistoricalData(String response) {
        Platform.runLater(() -> historicalDataArea.setText(response));
    }
}

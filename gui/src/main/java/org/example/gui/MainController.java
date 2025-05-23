package org.example.gui;

import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.json.JSONArray;
import org.json.JSONObject;


import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

public class MainController {

    @FXML private Label communityPoolLabel;
    @FXML private Label gridPortionLabel;
    @FXML private DatePicker startDate;
    @FXML private DatePicker endDate;
    @FXML private ComboBox<String> startTimeComboBox;
    @FXML private ComboBox<String> endTimeComboBox;
    @FXML private TextArea historicalDataArea;
    @FXML
    private TableView<UsageData> usageTable;

    @FXML
    private TableColumn<UsageData, String> timestampCol;

    @FXML
    private TableColumn<UsageData, Number> valueCol;



    private final HttpClient client = HttpClient.newHttpClient();

    @FXML
    public void initialize() {
        // Halbstunden-Schritte in ComboBox
        var halfHours = IntStream.range(0, 48)
                .mapToObj(i -> String.format("%02d:%02d", i / 2, (i % 2) * 30))
                .toList();
        startTimeComboBox.setItems(FXCollections.observableList(halfHours));
        endTimeComboBox.setItems(FXCollections.observableList(halfHours));
        startTimeComboBox.getSelectionModel().select("00:00");
        endTimeComboBox.getSelectionModel().select("23:30");

        startDate.setValue(LocalDate.now());
        endDate.setValue(LocalDate.now());
    }

    @FXML
    private void refreshData() {
        var request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/energy/current"))
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(this::updateCurrentData)
                .exceptionally(e -> { showError("Fehler beim Aktualisieren: " + e.getMessage()); return null; });
    }

    private void updateCurrentData(String response) {
        Platform.runLater(() -> {
            try {
                // Beispielhafte Verarbeitung: Dummy-Daten setzen
                communityPoolLabel.setText(response.contains("communityDepleted") ? "35%" : "0%");
                gridPortionLabel.setText(response.contains("gridPortion") ? "65%" : "0%");
            } catch (Exception e) {
                showError("Fehler beim Verarbeiten: " + e.getMessage());
            }
        });
    }

    @FXML
    private void showHistoricalData() {
        LocalDate sDate = startDate.getValue();
        LocalDate eDate = endDate.getValue();
        String sTime = startTimeComboBox.getValue();
        String eTime = endTimeComboBox.getValue();

        String start = sDate + "T" + sTime;
        String end   = eDate + "T" + eTime;

        var request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/energy/historical?start=" + start + "&end=" + end))
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(this::updateHistoricalData)
                .exceptionally(e -> { showError("Fehler beim Laden historischer Daten: " + e.getMessage()); return null; });
    }



    private void updateHistoricalData(String response) {
        Platform.runLater(() -> {
            StringBuilder builder = new StringBuilder();
            try {
                JSONArray array = new JSONArray(response);

                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);

                    builder.append("Hour: ").append(obj.getString("hour")).append("\n")
                            .append("Produced: ").append(obj.getDouble("communityProduced")).append(" kWh\n")
                            .append("Used (Community): ").append(obj.getDouble("communityUsed")).append(" kWh\n")
                            .append("Used (Grid): ").append(obj.getDouble("gridUsed")).append(" kWh\n")
                            .append("-------------------------\n");
                }
            } catch (Exception e) {
                builder.append(" Fehler beim Verarbeiten: ").append(e.getMessage());
            }

            historicalDataArea.setText(builder.toString());
        });
    }



    private void showError(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
            alert.showAndWait();
        });
    }

    public void initialize(URL location, ResourceBundle resources) {
        timestampCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTimestamp()));
        valueCol.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getValue()));

        // Beispielhafte Daten laden
        List<UsageData> dataList = List.of(
                new UsageData("2024-05-01T10:00", 123.45),
                new UsageData("2024-05-01T11:00", 130.10)
        );
        usageTable.setItems(FXCollections.observableArrayList(dataList));
    }


}


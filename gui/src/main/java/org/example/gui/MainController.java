package org.example.gui;

import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.example.gui.UsageEntry;

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
    @FXML private Label communityProducedLabel;
    @FXML private Label communityUsedLabel;
    @FXML private Label gridUsedLabel;
    @FXML private Label errorLabel;

    @FXML
    private TableView<UsageData> usageTable;

    @FXML
    private TableColumn<UsageData, String> timestampCol;

    @FXML
    private TableColumn<UsageData, Number> valueCol;



    @FXML
    public void initialize() {
        var halfHours = IntStream.range(0, 48)
                .mapToObj(i -> String.format("%02d:%02d", i / 2, (i % 2) * 30))
                .toList();

        startTimeComboBox.setItems(FXCollections.observableList(halfHours));
        endTimeComboBox.setItems(FXCollections.observableList(halfHours));
        startTimeComboBox.getSelectionModel().select("00:00");
        endTimeComboBox.getSelectionModel().select("23:30");

        startDate.setValue(LocalDate.now());
        endDate.setValue(LocalDate.now());

        timestampCol.setCellValueFactory(d ->
                new SimpleStringProperty(d.getValue().getTimestamp()));
        valueCol.setCellValueFactory(d ->
                new SimpleDoubleProperty(d.getValue().getValue()));

        // sinnvolle Zell-/Tabellen­größen
        usageTable.setFixedCellSize(450);
        usageTable.setPrefHeight(350);
        usageTable.setPrefWidth(200);
    }


    @FXML
    private void refreshData() {
        new Thread(() -> {
            try {
                CurrentStatus status = RestClient.fetchCurrentData();
                Platform.runLater(() -> {
                    communityPoolLabel.setText(String.format("%.2f %%", status.communityDepleted));
                    gridPortionLabel.setText(String.format("%.2f %%", status.gridPortion));
                    errorLabel.setText("");
                });
            } catch (Exception e) {
                showError("Fehler beim Laden aktueller Daten: " + e.getMessage());
            }
        }).start();
    }


    @FXML
    private void showHistoricalData() {
        if (startDate.getValue() == null || endDate.getValue() == null ||
                startTimeComboBox.getValue() == null || endTimeComboBox.getValue() == null) {
            showError("Bitte Start/End-Datum und Zeit wählen.");
            return;
        }

        String start = startDate.getValue() + "T" + startTimeComboBox.getValue();
        String end = endDate.getValue() + "T" + endTimeComboBox.getValue();

        new Thread(() -> {
            try {
                List<UsageEntry> list = RestClient.fetchHistoricalData(start, end);
                Platform.runLater(() -> updateHistoricalData(list));
            } catch (Exception e) {
                showError("Fehler beim Laden historischer Daten: " + e.getMessage());
            }
        }).start();
    }





    private void updateHistoricalData(List<UsageEntry> list) {
        try {
            ObservableList<UsageData> dataList = FXCollections.observableArrayList();
            double totalProduced = 0, totalUsed = 0, totalGrid = 0;

            StringBuilder sb = new StringBuilder();

            for (UsageEntry entry : list) {
                totalProduced += entry.communityProduced;
                totalUsed += entry.communityUsed;
                totalGrid   += entry.gridUsed;

                dataList.add(new UsageData(entry.hour, entry.communityUsed + entry.gridUsed));

                sb.append(entry.hour)
                        .append(": Produced=")
                        .append(entry.communityProduced)
                        .append(", Used=")
                        .append(entry.communityUsed)
                        .append(", Grid=")
                        .append(entry.gridUsed)
                        .append("\n");
            }

            errorLabel.setText("");
            usageTable.setItems(dataList);
            historicalDataArea.setText(sb.toString());

            communityProducedLabel.setText(String.format("%.3f kWh", totalProduced));
            communityUsedLabel.setText(String.format("%.3f kWh", totalUsed));
            gridUsedLabel.setText(String.format("%.3f kWh", totalGrid));
        } catch (Exception e) {
            historicalDataArea.setText("Fehler beim Verarbeiten: " + e.getMessage());
        }
    }







    private void showError(String message) {
        Platform.runLater(() -> errorLabel.setText("!" + message));
    }


}


package org.example.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        Label communityPoolLabel = new Label("Community Pool: 0% used");
        Label gridPortionLabel = new Label("Grid Portion: 0%");

        Button refreshButton = new Button("Refresh");

        TextField startField = new TextField();
        startField.setPromptText("YYYY-MM-DDTHH:MM");

        TextField endField = new TextField();
        endField.setPromptText("YYYY-MM-DDTHH:MM");

        Button showDataButton = new Button("Show Data");

        TextArea historicalDataArea = new TextArea();
        historicalDataArea.setEditable(false);

        VBox layout = new VBox(10,
                communityPoolLabel,
                gridPortionLabel,
                refreshButton,
                new Label("Start:"), startField,
                new Label("End:"), endField,
                showDataButton,
                historicalDataArea
        );
        layout.setPadding(new Insets(10));

        Scene scene = new Scene(layout, 400, 500);

        primaryStage.setTitle("Energy Community GUI");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

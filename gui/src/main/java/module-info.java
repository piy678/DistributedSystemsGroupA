module org.example.gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires android.json;


    opens org.example.gui to javafx.fxml;
    exports org.example.gui;
}
module org.example.gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    // requires android.json;
    requires org.json;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires javafx.base;
    requires javafx.graphics;

    opens org.example.gui to javafx.fxml;
    exports org.example.gui;
}
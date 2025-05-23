module org.example.database_class {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.database_class to javafx.fxml;
    exports org.example.database_class;
}
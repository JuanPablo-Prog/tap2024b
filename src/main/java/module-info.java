module com.example.tap2024b {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.tap2024b to javafx.fxml;
    exports com.example.tap2024b;
}
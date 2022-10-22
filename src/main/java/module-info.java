module com.example.karkulator {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.karkulator to javafx.fxml;
    exports com.example.karkulator;
}
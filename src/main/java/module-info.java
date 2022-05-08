module com.example.production {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.production to javafx.fxml;
    exports com.example.production;
    exports com.example.production.controllers;
    opens com.example.production.controllers to javafx.fxml;
}
module com.example.production {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.production to javafx.fxml;
    exports com.example.production;
    exports com.example.production.model;

}
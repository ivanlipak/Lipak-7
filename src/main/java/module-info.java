module com.example.production {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.production to javafx.fxml;
    exports com.example.production;
}
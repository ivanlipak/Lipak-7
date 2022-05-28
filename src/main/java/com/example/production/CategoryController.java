package com.example.production;

import com.example.production.documents.Database;
import com.example.production.model.*;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.production.documents.ReadTxt.readCategories;

public class CategoryController {

    @FXML
    private TextField categroyNameTextField;
    @FXML
    private TextField categoryDescriptionTextField;
    @FXML
    private TableView<Category> categoryTableView;
    @FXML
    private TableColumn<Category, String> categoryNameColumn;
    @FXML
    private TableColumn<Category, String> categoryDescriptionColumn;

    public static List<Category> categoryList;
    public static ObservableList<Category> categoryObservableList;
    public static Integer forEdit;

    public void initialize() throws SQLException {
        categoryNameColumn.
                setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getName()));

        categoryDescriptionColumn.
                setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getDescription()));



        categoryList = Database.databaseReadCategories();

        categoryObservableList = FXCollections.observableList(categoryList);
        categoryTableView.setItems(categoryObservableList);
    }

    @FXML
    public void search(){
        String categoryName = categroyNameTextField.getText();
        String categoryDescription = categoryDescriptionTextField.getText();
        List<Category> filteredList = new ArrayList<>(categoryList);

        if(!categoryName.isEmpty()){
            filteredList = filteredList
                    .stream()
                    .filter(category -> category.getName().toLowerCase().contains(categoryName.toLowerCase()))
                    .toList();
        }

        if(!categoryDescription.isEmpty()){
            filteredList = filteredList
                    .stream()
                    .filter(category -> category.getDescription().toLowerCase().contains(categoryDescription.toLowerCase()))
                    .toList();
        }

        ObservableList<Category> observableList = FXCollections.observableList(filteredList);
        categoryTableView.setItems(observableList);
    }


    public void editButton () throws IOException {
        forEdit = Integer.valueOf(categoryTableView.getSelectionModel().getSelectedItem().getId().toString());

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Edit-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 400);
        Stage stage = new Stage();
        stage.setTitle("Edit");
        stage.setScene(scene);
        stage.show();
    }

    public void deleteButton () throws SQLException {
        Database.deleteCategoryById(categoryTableView.getSelectionModel().getSelectedItem().getId());
        List<Category> temporaryList = Database.databaseReadCategories();
        categoryObservableList = FXCollections.observableList(temporaryList);
        initialize();
    }


}
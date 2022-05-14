package com.example.production;

import com.example.production.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

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

    private List<Category> categoryList;

    public void initialize(){
        categoryNameColumn.
                setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getName()));

        categoryDescriptionColumn.
                setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getDescription()));

        Category[] categoryArray = readCategories();
        categoryList = Arrays.stream(categoryArray).toList();

        ObservableList<Category> categoryObservableList = FXCollections.observableList(categoryList);
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
}
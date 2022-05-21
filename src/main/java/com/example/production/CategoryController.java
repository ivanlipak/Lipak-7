package com.example.production;

import com.example.production.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
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
    @FXML
    private TableColumn<Category, String> radioButton;
    private ToggleGroup group1;

    public static List<Category> categoryList;
    public static ObservableList<Category> categoryObservableList;
    public static Integer forEdit;

    public void initialize(){
        categoryNameColumn.
                setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getName()));

        categoryDescriptionColumn.
                setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getDescription()));



        categoryList = readCategories();

        categoryObservableList = FXCollections.observableList(categoryList);
        categoryTableView.setItems(categoryObservableList);

        group1 = new ToggleGroup();
        for(Category category : categoryObservableList){
            category.getRadioButton().setToggleGroup(group1);
        }
        radioButton.setCellValueFactory(
                new PropertyValueFactory<Category, String>("radioButton")
        );

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


    public void editB () throws IOException {
        for (Category category : categoryObservableList){
            if(category.getRadioButton().isSelected()){
                forEdit = Integer.valueOf(category.getId().toString())-1;
            }
        }

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Edit-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 400);
        Stage stage = new Stage();
        stage.setTitle("Edit");
        stage.setScene(scene);
        stage.show();
    }
}
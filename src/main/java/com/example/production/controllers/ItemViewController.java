package com.example.production.controllers;


import com.example.production.model.Item;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.example.production.documents.ReadTxt.readCategories;
import static com.example.production.documents.ReadTxt.readItems;

public class ItemViewController {


    @FXML
    private TextField itemNameTextField;
    @FXML
    private TextField itemCategoryTextField;
    @FXML
    private TextField itemWidthTextField;
    @FXML
    private TextField itemLengthTextField;
    @FXML
    private TextField itemHeightTextField;
    @FXML
    private TextField itemPriceTextField;

    @FXML
    private TableView<Item> itemTableView;

    @FXML
    private TableColumn<Item, String> itemNameColumn;
    @FXML
    private TableColumn<Item, String> itemCategoryColumn;
    @FXML
    private TableColumn<Item, String> itemWidthColumn;
    @FXML
    private TableColumn<Item, String> itemLengthColumn;
    @FXML
    private TableColumn<Item, String> itemHeightColumn;
    @FXML
    private TableColumn<Item, String> itemPriceColumn;

    private List<Item> itemList;

    public void initialize(){
        itemNameColumn.
                setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getName()));

        itemCategoryColumn.
                setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getCategory().getName()));

        itemWidthColumn.
                setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getWidth().toString()));

        itemLengthColumn.
                setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getLength().toString()));

        itemHeightColumn.
                setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getHeight().toString()));

        itemPriceColumn.
                setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getSellingPrice().toString()));

        itemList = readItems(readCategories());

        ObservableList<Item> itemsObservableList = FXCollections.observableList(itemList);
        itemTableView.setItems(itemsObservableList);

    }

    @FXML
    public void search(){
        String itemName = itemNameTextField.getText();
        String itemCategory = itemCategoryTextField.getText();
        String itemWidth = itemWidthTextField.getText();
        String itemLength = itemLengthTextField.getText();
        String itemHeight = itemHeightTextField.getText();
        String itemPrice = itemPriceTextField.getText();

        List<Item> filteredList = new ArrayList<>(itemList);

        if(!itemName.isEmpty()){
            filteredList = filteredList
                    .stream()
                    .filter(item -> item.getName().toLowerCase().contains(itemName.toLowerCase()))
                    .toList();
        }

        if(!itemCategory.isEmpty()){
            filteredList = filteredList
                    .stream()
                    .filter(item -> item.getCategory().getName().toLowerCase().contains(itemCategory.toLowerCase()))
                    .toList();
        }
        if(!itemHeight.isEmpty()){
            filteredList = filteredList
                    .stream()
                    .filter(item -> item.getHeight().compareTo(new BigDecimal(itemHeight))<=0)
                    .toList();
        }
        if(!itemLength.isEmpty()){
            filteredList = filteredList
                    .stream()
                    .filter(item -> item.getLength().compareTo(new BigDecimal(itemLength))<=0)
                    .toList();
        }
        if(!itemWidth.isEmpty()){
            filteredList = filteredList
                    .stream()
                    .filter(item -> item.getWidth().compareTo(new BigDecimal(itemWidth))<=0)
                    .toList();
        }
        if(!itemPrice.isEmpty()){
            filteredList = filteredList
                    .stream()
                    .filter(item -> item.getSellingPrice().compareTo(new BigDecimal(itemPrice))<=0)
                    .toList();
        }

        ObservableList<Item> observableList = FXCollections.observableList(filteredList);
        itemTableView.setItems(observableList);
    }
}

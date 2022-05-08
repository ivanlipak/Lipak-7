package com.example.production.controllers;

import com.example.production.model.Factory;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class FactoryViewController {
    @FXML
    private TextField factoryNameTextField;
    @FXML
    private TextField factoryAddressTextField;
    @FXML
    private TextField factoryItemsTextField;
    @FXML
    private TableView factoryTableVieW;
    @FXML
    private TableColumn<Factory, String> factoryNameColumn;
    @FXML
    private TableColumn<Factory, String> factoryAddressColumn;
    @FXML
    private TableColumn<Factory, String> factoryItemsColumn;
    @FXML
    private TableView factoryItemsTableView;
    @FXML
    private TableColumn factoryItemsNameColumn;

    public void initialize(){

    }

    public void search(){

    }

}

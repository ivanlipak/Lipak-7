package com.example.production;

import com.example.production.model.Item;
import com.example.production.model.Store;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import java.util.List;

import static com.example.production.documents.ReadTxt.*;


public class StoreSearchViewController {

    @FXML
    private TextField storeNameTextField;
    @FXML
    private TextField storeWebAddressTextField;
    @FXML
    private TextField storeItemTextField;

    @FXML
    private TableView storeTableView;

    @FXML
    private TableColumn<Store, String> storeNameTextColumn;
    @FXML
    private TableColumn<Store, String> storeWebAddressTextColumn;
    @FXML
    private TableColumn<Store, String> storeItemTextColumn;

    public static List<Store> storeList;
    private String itemsString;


    public void initialize(){
        storeNameTextColumn.
                setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getName()));

        storeWebAddressTextColumn.
                setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getWebAddress()));

        storeItemTextColumn.
                setCellValueFactory(cellData ->
                        new SimpleStringProperty(getItemiString(cellData.getValue())));

        List<Item> itemList = readItems(readCategories());
        storeList = readStore(itemList);


        ObservableList<Store> factoryObservableList = FXCollections.observableList(storeList);
        storeTableView.setItems(factoryObservableList);

    }

    public void search(){

    }

    public String getItemiString (Store store){
        itemsString = "";
        for(Item item : store.getItems()){
            itemsString = itemsString + item.getName() + "\n";
        }
        return itemsString;
    }
}

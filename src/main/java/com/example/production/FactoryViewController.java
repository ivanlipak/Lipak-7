package com.example.production;

import com.example.production.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.production.documents.ReadTxt.*;

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

    private List<Factory> factoryList;
    private String itemiString;
    private String addressString;

    public void initialize(){
        factoryNameColumn.
                setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getName()));

        factoryAddressColumn.
                setCellValueFactory(cellData ->
                        new SimpleStringProperty(getAddressString(cellData.getValue())));

        factoryItemsColumn.
                setCellValueFactory(cellData ->
                        new SimpleStringProperty(getItemiString(cellData.getValue())));

         List<Item> itemList = readItems(readCategories());
         List<Address> addresses = readAddresses();
         factoryList = List.of(readFactory(itemList, addresses));


        ObservableList<Factory> factoryObservableList = FXCollections.observableList(factoryList);
        factoryTableVieW.setItems(factoryObservableList);
   }

   public String getItemiString (Factory factory){
       itemiString = "";
       for(Item item : factory.getItems()){
           itemiString = itemiString + item.getName() + "\n";
       }
       return itemiString;
   }

   public String getAddressString (Factory factory){
        addressString = "";
        addressString = factory.getAddress().getCity().getCityName() + System.lineSeparator() + factory.getAddress().getCity().getPostNr()+ System.lineSeparator()
                + factory.getAddress().getStreet() + System.lineSeparator() + factory.getAddress().getHouseNumber() + " ";
        return addressString;
   }


    public void search(){
        String factoryName = factoryNameTextField.getText();
        String factoryAddress = factoryAddressTextField.getText();
        String factoryItem = factoryItemsTextField.getText();

        List<Factory> filteredList = new ArrayList<>(factoryList);

        if(!factoryName.isEmpty()){
            filteredList = filteredList
                    .stream()
                    .filter(f -> f.getName().toLowerCase().contains(factoryName.toLowerCase()))
                    .toList();
        }
        if(!factoryAddress.isEmpty()){
            filteredList = filteredList
                    .stream()
                    .filter(f -> f.getAddress().toString().toLowerCase().contains(factoryAddress.toLowerCase()))
                    .toList();
        }
        if(!factoryItem.isEmpty()){
            filteredList = filteredList
                    .stream()
                    .filter(f -> f.getItems().toString().toLowerCase().contains(factoryItem.toLowerCase()))
                    .toList();
        }

        ObservableList<Factory> observableList = FXCollections.observableList(filteredList);
        factoryTableVieW.setItems(observableList);

    }

}

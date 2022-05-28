package com.example.production;

import com.example.production.documents.Database;
import com.example.production.enums.City;
import com.example.production.model.Address;
import com.example.production.model.Category;
import com.example.production.model.Item;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.production.documents.ReadTxt.*;

public class NewFactoryController {

    @FXML
    private TextField factoryName;
    @FXML
    private TextField factoryStreet;
    @FXML
    private TextField factoryHouseNumber;

    @FXML
    private ChoiceBox cityChoiceBox;

    //item things
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

    private ObservableList<Item> itemsObservableList;

    public static List<Item> itemList;

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

        try {
            itemList = Database.databaseReadItems();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        itemsObservableList = FXCollections.observableList(itemList);
        itemTableView.setItems(itemsObservableList);

        List<String> addressesListString = new ArrayList<>();
        addressesListString.add("Zagreb");
        addressesListString.add("Velika Gorica");
        addressesListString.add("Sisak");
        addressesListString.add("Crikvenica");
        ObservableList<String> obsCityList = FXCollections.observableList(addressesListString);
        cityChoiceBox.setItems(obsCityList);


    }

    public void addStore() throws Exception {
        StringBuilder errorMessages = new StringBuilder();
        String nameString = factoryName.getText();
        String streetString = factoryStreet.getText();
        String houseNrString = factoryHouseNumber.getText();
        String cityString = cityChoiceBox.getValue().toString();


        if(nameString.isEmpty()){
            errorMessages.append("Store name is mandatory!");
        }

        if(streetString.isEmpty()){
            errorMessages.append("Street name is mandatory!");
        }

        if(houseNrString.isEmpty()){
            errorMessages.append("House number is mandatory!");
        }

        // postoji li adresa
        List<Address> addresses = Database.databaseReadAddresses();
        Long idAdress = null;
        for (Address address : addresses){
            if (address.getCity().getCityName().equals(cityString)
            && address.getStreet().equals(streetString)
            && address.getHouseNumber().equals(houseNrString)){
                idAdress=address.getId();
            }
        }
        // napravi novu adresu
        City city = null;
        switch (cityString.toLowerCase()){
            case "sisak":
                city= City.SISAK;
                break;
            case "zagreb":
                city= City.ZAGREB;
                break;
            case "crikvenica":
                city= City.CRIKVENICA;
                break;
            case "velika gorica":
                city= City.VELIKAGORICA;
                break;
            case "velikagorica":
                city= City.VELIKAGORICA;
                break;
        }

        //poberi iteme
        List<Long> chosenItems = new ArrayList<>();
        ObservableList<Item> items = itemTableView.getSelectionModel().getSelectedItems();
        for(Item item : items){
            chosenItems.add(item.getId());
        }

       if (errorMessages.isEmpty()) {
            if(idAdress==null){
                Address newAddress = new Address().Builder()
                        .Id(1L)
                        .Street(streetString)
                        .HouseNumber(houseNrString)
                        .City(city);
                Database.addAddress(newAddress);


                Address adresaFactory = Database.databaseReadAddresses().get(Database.databaseReadAddresses().size()-1);
                Database.addFactory(nameString, adresaFactory.getId());
            }


            for(Long itemId : chosenItems){
                Item newItem = Database.getItemById(itemId);
                System.out.println(Database.databaseReadFactories().get(Database.databaseReadFactories().size()-1).getId());
                Database.addFactoryItem(Database.databaseReadFactories().get(Database.databaseReadFactories().size()-1), newItem);
            }


            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("New Factory has been successfully added!");
            alert.setHeaderText("Factory " + nameString + " has been added!");
            alert.setContentText("Factory " + nameString + " has been successfully added!");

            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error while trying to save new factory");
            alert.setHeaderText("Factory has not been added!");
            alert.setContentText(errorMessages.toString());

            alert.showAndWait();
        }
    }
}

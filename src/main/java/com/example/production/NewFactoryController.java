package com.example.production;

import com.example.production.enums.City;
import com.example.production.model.Address;
import com.example.production.model.Category;
import com.example.production.model.Item;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.*;
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
    @FXML
    private TableColumn<Item, String> checkBoxTableColumn;

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

        checkBoxTableColumn.setCellValueFactory(
                new PropertyValueFactory<Item, String>("checkBox")
        );
        itemList = readItems(readCategories());
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

    public void addStore() {
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
        List<Address> addresses = readAddresses();
        Long idAdress = null;
        for (Address address : addresses){
            if (address.getCity().getCityName().equals(cityString)
            && address.getStreet().equals(streetString)
            && address.getHouseNumber().equals(houseNrString)){
                idAdress=address.getId();
            }
        }
        // napravi novu adresu
        String city = null;
        if(idAdress==null){
            switch (cityString.toLowerCase()){
                case "sisak":
                    city= "SISAK";
                    break;
                case "zagreb":
                    city= "ZAGREB";
                    break;
                case "crikvenica":
                    city= "CRIKVENICA";
                    break;
                case "velika gorica":
                    city= "VELIKAGORICA";
                    break;
            }
        }

        //poberi iteme
        List<Long> chosenItems = new ArrayList<>();
        for(Item item : itemsObservableList){
            if(item.getCheckBox().isSelected()){
                chosenItems.add(item.getId());
            }
        }
        String chosenItemsString="";
        if(chosenItems.isEmpty()){
            errorMessages.append("You must choose at least one article!");
        }else{
            int brojac=0;
            for (Long i : chosenItems){
                brojac++;
                if(brojac == chosenItems.size()){
                    chosenItemsString = chosenItemsString + i.toString();
                }else{
                    chosenItemsString = chosenItemsString + i.toString() + ",";
                }
            }
        }

        if (errorMessages.isEmpty()) {
            if(idAdress==null){
                File addressesFile = new File("dat/addresses.txt");
                try (BufferedReader lineReader = new BufferedReader(new FileReader(addressesFile))){
                    String line;
                    String content="";
                    int i = 1;
                    while((line = lineReader.readLine())!=null){
                        content = content + line + System.lineSeparator();
                        i++;
                    }
                    idAdress = Long.valueOf((i/4)+1);
                    content = content +
                            Integer.valueOf((i/4) + 1).toString() +
                            System.lineSeparator() +
                            streetString +
                            System.lineSeparator() +
                            houseNrString +
                            System.lineSeparator() +
                            city;
                    FileWriter writer = new FileWriter(addressesFile);
                    writer.write(content);
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            File factoriesFile = new File("dat/factories.txt");
            try (BufferedReader lineReader = new BufferedReader(new FileReader(factoriesFile))){
                String line;
                String content="";
                int i = 1;
                while((line = lineReader.readLine())!=null){
                    content = content + line + System.lineSeparator();
                    i++;
                }
                content = content +
                        Integer.valueOf((i/4) + 1).toString() +
                        System.lineSeparator() +
                        nameString +
                        System.lineSeparator() +
                        idAdress.toString() +
                        System.lineSeparator() +
                        chosenItemsString;
                FileWriter writer = new FileWriter(factoriesFile);
                writer.write(content);
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
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

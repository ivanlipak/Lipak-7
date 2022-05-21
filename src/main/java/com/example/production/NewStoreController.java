package com.example.production;

import com.example.production.model.Item;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.production.documents.ReadTxt.readCategories;
import static com.example.production.documents.ReadTxt.readItems;

public class NewStoreController {

    @FXML
    private TextField storeName;
    @FXML
    private TextField storeWebAddress;

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
    @FXML
    private CheckBox checkBox;
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

    }

    public void addStore() {
        String nameString = storeName.getText();
        String wwwString = storeWebAddress.getText();
        List<Long> chosenItems = new ArrayList<>();

        StringBuilder errorMessages = new StringBuilder();

        if(nameString.isEmpty()){
            errorMessages.append("Store name is mandatory!");
        }

        if(wwwString.isEmpty()){
            errorMessages.append("Web address is mandatory!");
        }

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
            File categoryFile = new File("dat/stores.txt");
            try (BufferedReader lineReader = new BufferedReader(new FileReader(categoryFile))){
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
                        wwwString +
                        System.lineSeparator() +
                        chosenItemsString;
                FileWriter writer = new FileWriter(categoryFile);
                writer.write(content);
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("New store has been successfully added!");
            alert.setHeaderText("Store " + nameString + " has been added!");
            alert.setContentText("Store " + nameString + " with web address: "
                    + wwwString + " has been successfully added!");

            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error while trying to save new store");
            alert.setHeaderText("Store has not been added!");
            alert.setContentText(errorMessages.toString());

            alert.showAndWait();
        }
    }
}

package com.example.production;

import com.example.production.model.Category;
import com.example.production.model.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

import static com.example.production.documents.ReadTxt.readCategories;
import static com.example.production.documents.ReadTxt.readItems;

public class NewItemController {
    @FXML
    private TextField itemName;
    @FXML
    private ChoiceBox itemCategory;
    @FXML
    private TextField itemLength;
    @FXML
    private TextField itemWidth;
    @FXML
    private TextField itemHeight;
    @FXML
    private TextField itemProductionPrice;
    @FXML
    private TextField itemSellingPrice;
    @FXML
    private TextField itemDiscount;


    public void initialize(){
        List<Category> categoriesList = readCategories();
        List<String> categoryListString = new ArrayList<>();
        categoryListString.addAll(categoriesList.stream().map(c -> c.getName()).toList());

        ObservableList<String> obsCatList = FXCollections.observableList(categoryListString);
        itemCategory.setItems(obsCatList);
        itemCategory.setValue(obsCatList.get(0));
    }


    public void addItem(){
        StringBuilder errorMessages = new StringBuilder();
        String itemNameString = itemName.getText();
        String itemCategoryString = itemCategory.getValue().toString();
        String itemCategoryStringForTxt="";
        for (Category category : readCategories()){
            if (category.getName().equalsIgnoreCase(itemCategoryString)){
                itemCategoryStringForTxt = category.getId().toString();
            }
        }
        String itemLengthString = itemLength.getText();
        String itemWidthString = itemWidth.getText();
        String itemHeightString = itemHeight.getText();
        String itemProductionPriceString = itemProductionPrice.getText();
        String itemSellingPriceString = itemSellingPrice.getText();
        String itemDiscountString = itemDiscount.getText();
        try{
            new BigDecimal(itemHeightString);
            new BigDecimal(itemWidthString);
            new BigDecimal(itemLengthString);
            new BigDecimal(itemProductionPriceString);
            new BigDecimal(itemSellingPriceString);
            new BigDecimal(itemDiscountString);
        }catch(RuntimeException e){
            errorMessages.append("Length, Heigth, Width, Dicount and Prices of an item must be numbers!");
        }

        List<Item> items = readItems(readCategories());
        for(Item item : items){
            if (itemNameString.equalsIgnoreCase(item.getName())){
                errorMessages.append("Item already exists!");
            }
        }

        if (itemNameString.isEmpty()
                || itemCategoryString.isEmpty()
                || itemHeightString.isEmpty()
                || itemWidthString.isEmpty()
                || itemLengthString.isEmpty()
                || itemProductionPriceString.isEmpty()
                || itemSellingPriceString.isEmpty()
                || itemDiscountString.isEmpty()){
            errorMessages.append("No fields can be left empty!");
        }

        if (errorMessages.isEmpty()) {
            File categoryFile = new File("dat/items.txt");
            try (BufferedReader lineReader = new BufferedReader(new FileReader(categoryFile))){
                String line;
                String content="";
                int i = 1;
                while((line = lineReader.readLine())!=null){
                    content = content + line + System.lineSeparator();
                    i++;
                }
                content = content +
                        Integer.valueOf((i/9) + 1).toString() +
                        System.lineSeparator() +
                        itemNameString +
                        System.lineSeparator() +
                        itemCategoryStringForTxt +
                        System.lineSeparator() +
                        itemWidthString +
                        System.lineSeparator() +
                        itemHeightString +
                        System.lineSeparator() +
                        itemLengthString +
                        System.lineSeparator() +
                        itemProductionPriceString +
                        System.lineSeparator() +
                        itemSellingPriceString +
                        System.lineSeparator() +
                        itemDiscountString;
                FileWriter writer = new FileWriter(categoryFile);
                writer.write(content);
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("New category has been successfully added!");
            alert.setHeaderText("Item " + itemNameString + " is has been added!");
            alert.setContentText("Item " + itemNameString + " has been successfully added!");

            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error while trying to save new item");
            alert.setHeaderText("Item has not been added!");
            alert.setContentText(errorMessages.toString());

            alert.showAndWait();
        }

    }
}

package com.example.production;

import com.example.production.model.Category;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class EditCategoryController {

    @FXML
    private TextField name;
    @FXML
    private TextField description;

    private String nameS;
    private String descriptionS;

    public void initialize(){
        nameS = CategoryController.categoryObservableList.get(CategoryController.forEdit).getName();
        descriptionS = CategoryController.categoryObservableList.get(CategoryController.forEdit).getDescription();

        name.setText(nameS);
        description.setText(descriptionS);
    }

    public void change(){
        String nameString = name.getText();
        String descriptionString = description.getText();

        CategoryController.categoryObservableList.get(CategoryController.forEdit).setName(nameString);
        CategoryController.categoryObservableList.get(CategoryController.forEdit).setDescription(descriptionString);
        txtMaker();
    }

    public void txtMaker(){
        String content = "";
        for (Category category : CategoryController.categoryObservableList){
            content = content + category.getId().toString() + System.lineSeparator()
                    + category.getName() + System.lineSeparator()
                    + category.getDescription();

            if((category.getId().compareTo(Long.valueOf(CategoryController.categoryObservableList.size())))<0){
                content = content +System.lineSeparator();
            }
        }
        System.out.println(content);
        File categoryFile = new File("dat/categories.txt");
        try (FileWriter writer = new FileWriter(categoryFile)) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

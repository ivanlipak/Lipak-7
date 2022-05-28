package com.example.production;

import com.example.production.documents.Database;
import com.example.production.model.Category;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.SQLException;

import static com.example.production.CategoryController.forEdit;

public class EditCategoryController {

    @FXML
    private TextField name;
    @FXML
    private TextField description;

    private String nameS;
    private String descriptionS;

    public void initialize(){
        nameS = CategoryController.categoryObservableList.get(forEdit-1).getName();
        descriptionS = CategoryController.categoryObservableList.get(forEdit-1).getDescription();

        name.setText(nameS);
        description.setText(descriptionS);
    }

    public void change() throws SQLException {
        String nameString = name.getText();
        String descriptionString = description.getText();

        CategoryController.categoryObservableList.get(forEdit-1).setName(nameString);
        CategoryController.categoryObservableList.get(forEdit-1).setDescription(descriptionString);

        Database.updateCategoryById(Long.valueOf(forEdit), nameString, descriptionString);
    }
}

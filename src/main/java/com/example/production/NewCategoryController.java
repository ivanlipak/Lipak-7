package com.example.production;

import com.example.production.model.Category;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.*;

public class NewCategoryController {

    @FXML
    private TextField categoryName;
    @FXML
    private TextField categoryDescription;

    public void add(){
        String categoryNameString = categoryName.getText();
        String categoryDescriptionString = categoryDescription.getText();
        StringBuilder errorMessages = new StringBuilder();

        if(categoryNameString.isEmpty()){
            errorMessages.append("Category name is mandatory!");
        }

        if(categoryNameString.isEmpty()){
            errorMessages.append("Category description is mandatory!");
        }
        for (Category category : CategoryController.categoryList){
            if(categoryNameString.equals(category.getName())){
                errorMessages.append("Category name already exists!");
            }
        }

        if (errorMessages.isEmpty()) {
            File categoryFile = new File("dat/categories.txt");
            try (BufferedReader lineReader = new BufferedReader(new FileReader(categoryFile))){
                String line;
                String content="";
                int i = 1;
                while((line = lineReader.readLine())!=null){
                    content = content + line + System.lineSeparator();
                    i++;
                }
                content = content +
                        Integer.valueOf((i/3) + 1).toString() +
                        System.lineSeparator() +
                        categoryNameString +
                        System.lineSeparator() +
                        categoryDescriptionString;
                FileWriter writer = new FileWriter(categoryFile);
                writer.write(content);
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("New category has been successfully added!");
            alert.setHeaderText("Category " + categoryNameString + " has been added!");
            alert.setContentText("Category " + categoryNameString + " with description: "
                    + categoryDescriptionString + " has been successfully added!");

            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error while trying to save new category");
            alert.setHeaderText("Category has not been added!");
            alert.setContentText(errorMessages.toString());

            alert.showAndWait();
        }







    }
}

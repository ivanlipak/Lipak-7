package com.example.production;

import com.example.production.model.Category;
import javafx.fxml.FXML;
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

        if(!categoryNameString.isEmpty() || !categoryDescriptionString.isEmpty()){
            File categoryFile = new File("dat/category.txt");
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
        }
    }
}

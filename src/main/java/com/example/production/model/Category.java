package com.example.production.model;

import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

import java.io.Serializable;

public class Category extends NamedEntity implements Serializable {

    private String description;
    private RadioButton radioButton;


    public Category(Long id, String name, String description) {
        super(id, name);
        this.description = description;
        this.radioButton = new RadioButton(getId().toString());

    }

    public RadioButton getRadioButton() {
        return radioButton;
    }

    public void setRadioButton(RadioButton checkBox) {
        this.radioButton = checkBox;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    @Override
    public String toString() {
        return "Category{" +
                "id='" + getId() + '\'' +
                "name='" + getName() + '\'' +
                "description='" + description + '\'' +
                '}';
    }
}

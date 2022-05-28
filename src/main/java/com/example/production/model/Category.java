package com.example.production.model;

import javafx.scene.control.RadioButton;

import java.io.Serializable;

public class Category extends NamedEntity implements Serializable {

     private String description;

     public Category(Long id, String name, String description) {
        super(id, name);
        this.description = description;
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

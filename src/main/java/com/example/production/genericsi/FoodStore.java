package com.example.production.genericsi;

import com.example.production.model.*;

import java.util.HashSet;
import java.util.Set;

public class FoodStore<T extends Edible> extends Store {

    private Set<T> items;


    public FoodStore(Long id, String name, String webAddress, Set<T> items) {
        super(id, name, webAddress);
        this.items = items;
    }

    public Set<T> getEdibleItems(){
        return items;
    }
    public void setEdibleItems(Set<T> edibleItems) {
        this.items = items;
    }

    public Set<Item> getItems() {
        Set<Item> varka  = new HashSet<>();
        for(T item : this.items){
            Item itemCast = (Item)item;
            varka.add(itemCast);
        }
        return varka;
    }
}

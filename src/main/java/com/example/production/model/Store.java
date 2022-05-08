package com.example.production.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

public class Store extends NamedEntity implements Serializable {

    private String webAddress;
    private Set<Item> items;

    public Store(Long id, String name, String webAddress, Set<Item> items) {
        super(id, name);
        this.webAddress = webAddress;
        this.items = items;
    }
    public Store(Long id, String name, String webAddress) {
        super(id, name);
        this.webAddress = webAddress;
    }

    public String getWebAddress() {
        return webAddress;
    }
    public void setWebAddress(String webAddress) {
        this.webAddress = webAddress;
    }
    public Set<Item> getItems() {
        return items;
    }
    public void setItems(Set<Item> items) {
        this.items = items;
    }
    public Item getCheapestItem(){
        Integer len = items.size();
        Item[] proba = items.toArray(new Item[len]);

        BigDecimal cheapestItem = proba[0].getSellingPrice();
        Integer i = 0;
        for (Item item : proba){
            if(item.getSellingPrice().compareTo(cheapestItem)>0){
                cheapestItem = item.getSellingPrice();
                i++;
            }
        }
        return proba[i];
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Store store = (Store) o;
        return webAddress.equals(store.webAddress) && items.equals(store.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), webAddress, items);
    }

    @Override
    public String toString() {
        return "Store{" +
                "webAddress='" + webAddress + '\'' +
                ", items=" + items +
                '}';
    }
}

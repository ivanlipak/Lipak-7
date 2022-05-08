package com.example.production.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

public class Factory extends NamedEntity implements Serializable {


    private Address address;
    private Set<Item> items;

    public Factory(Long id, String name, Address address, Set<Item> items) {
        super(id, name);
        this.address = address;
        this.items = items;
    }

    public Address getAddress() {
        return address;
    }
    public void setAddress(Address address) {
        this.address = address;
    }
    public Set<Item> getItems() {
        return items;
    }
    public void setItems(Set<Item> items) {
        this.items = items;
    }
    public Item getBiggestItem(){
        Integer len = items.size();
        Item[] proba = items.toArray(new Item[len]);
        BigDecimal biggestItem = proba[0].getHeight().multiply(proba[0].getWidth().multiply(proba[0].getLength()));
        Integer i = 0;
        for(Item item : this.items){
            BigDecimal checker = item.getHeight().multiply(item.getLength().multiply(item.getWidth()));
            if(checker.compareTo(biggestItem)>0){
                biggestItem = checker;
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
        Factory factory = (Factory) o;
        return address.equals(factory.address) && items.equals(factory.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), address, items);
    }

    @Override
    public String toString() {
        return "Factory{" +
                "address=" + address +
                ", items=" + items +
                '}';
    }
}

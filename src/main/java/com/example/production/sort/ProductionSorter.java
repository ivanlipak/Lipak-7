package com.example.production.sort;

import com.example.production.model.*;

import java.util.Comparator;

public class ProductionSorter implements Comparator<Item> {

    private Boolean sortTypeUp = false;
    private Boolean sortTypeVolume = false;

    public ProductionSorter(Boolean sortTypeUp, Boolean sortTypeVolume) {
        this.sortTypeUp = sortTypeUp;
        this.sortTypeVolume = sortTypeVolume;
    }

    public ProductionSorter(Boolean sortTypeUp) {
        this.sortTypeUp = sortTypeUp;
    }

    @Override
    public int compare(Item item1, Item item2) {
        if(sortTypeVolume=false){
            if (item1.getSellingPrice().compareTo(item2.getSellingPrice())==1){
                if(this.sortTypeUp == true){
                    return 1;
                }else{
                    return -1;
                }
            }else if (item1.getSellingPrice().compareTo(item2.getSellingPrice())==-1){
                if(this.sortTypeUp == true){
                    return -1;
                }else{
                    return 1;
                }
            }else{
                return 0;
            }
        }else{
            if (item1.getVolume().compareTo(item2.getVolume())==1){
                return 1;
            }else if (item1.getVolume().compareTo(item2.getVolume())==-1){
                return -1;
            }else{
                return 0;
            }
        }
    }
}

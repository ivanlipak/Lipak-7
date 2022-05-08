package com.example.production.genericsi;

import com.example.production.model.*;


import java.util.List;


public class TechnicalStore<T extends Technical> extends Store{

    private List<T> texhnicalItem;

    public TechnicalStore(Long id, String name, String webAddress, List<T> texhnicalItems) {
        super(id, name, webAddress);
        this.texhnicalItem = texhnicalItems;
    }

    public List<T> getTexhnicalItem() {
        return texhnicalItem;
    }

    public void setTexhnicalItem(List<T> texhnicalItem) {
        this.texhnicalItem = texhnicalItem;
    }
}

package com.example.production.documents;


import com.example.production.enums.*;
import com.example.production.model.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class ReadTxt {

    public static List<Category> readCategories() {
        List<Category> categoryList = new ArrayList<>();

        File coursesFile = new File("dat/categories.txt");

        try (BufferedReader lineReader = new BufferedReader(new FileReader(coursesFile))) {
            String line;
            while((line = lineReader.readLine()) != null)  {
                Long id = Long.parseLong(line);
                String categoryName = lineReader.readLine();
                String categoryDescription = lineReader.readLine();
                Category newCategory = new Category(id, categoryName, categoryDescription);
                categoryList.add(newCategory);
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        return categoryList;
    }
    public static List<Factory> readFactory(List<Item> items, List<Address> addresses) {
        List<Factory> factoryArray = new ArrayList<>();

        File coursesFile = new File("dat/factories.txt");

        try (BufferedReader lineReader = new BufferedReader(new FileReader(coursesFile))) {
            String line;
            int i = 0;
            while((line = lineReader.readLine()) != null)  {
                Long id = Long.parseLong(line);
                String factoryName = lineReader.readLine();
                String addressString = lineReader.readLine();
                String itemsString = lineReader.readLine();
                List<String> itemsStringList = Arrays.stream(itemsString.split(",")).toList();
                Set<Item> itemSet = new HashSet<>();

                for (String ids : itemsStringList){
                    for(Item item : items){
                        if(item.getId().equals(Long.valueOf(ids))){
                            itemSet.add(item);
                        }
                    }
                }
                Factory newFactory = new Factory(id, factoryName, addresses.get(Integer.valueOf(addressString)), itemSet);
                factoryArray.add(newFactory);
                i++;
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        return factoryArray;
    }
    public static List<Store> readStore(List<Item> items) {
        List<Store> storeArray = new ArrayList<>();

        File coursesFile = new File("dat/stores.txt");

        try (BufferedReader lineReader = new BufferedReader(new FileReader(coursesFile))) {
            String line;
            int i =0;
            while((line = lineReader.readLine()) != null)  {
                Long id = Long.parseLong(line);
                String storeName = lineReader.readLine();
                String webAddress = lineReader.readLine();
                String itemsString = lineReader.readLine();
                List<String> itemsStringList = Arrays.stream(itemsString.split(",")).toList();
                Set<Item> itemSet = new HashSet<>();

                for (String ids : itemsStringList){
                    for(Item item : items){
                        if(item.getId().equals(Long.valueOf(ids))){
                            itemSet.add(item);
                        }
                    }
                }
                Store newStore = new Store(id, storeName, webAddress, itemSet);
                storeArray.add(newStore);
                i++;
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

        return storeArray;
    }
    public static List<Item> readItems(List<Category> categories) {
        List<Item> itemList = new ArrayList<>();

        File coursesFile = new File("dat/items.txt");

        try (BufferedReader lineReader = new BufferedReader(new FileReader(coursesFile))) {
            String line;

            while((line = lineReader.readLine()) != null)  {
                Long id = Long.parseLong(line);
                String itemName = lineReader.readLine();
                String categoryString = lineReader.readLine();
                String width = lineReader.readLine();
                String height = lineReader.readLine();
                String length = lineReader.readLine();
                String prodCost = lineReader.readLine();
                String sellPrice = lineReader.readLine();
                String discount = lineReader.readLine();

                Item newItem = new Item(id, itemName, categories.get(Integer.valueOf(categoryString)-1), BigDecimal.valueOf(Integer.valueOf(width)),
                        BigDecimal.valueOf(Integer.valueOf(height)), BigDecimal.valueOf(Integer.valueOf(length)), BigDecimal.valueOf(Integer.valueOf(prodCost)),
                        BigDecimal.valueOf(Integer.valueOf(sellPrice)), new Discount(BigDecimal.valueOf(Integer.valueOf(discount))));
                itemList.add(newItem);
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        return itemList;
    }
    public static List<Address> readAddresses() {
        List<Address> addressList = new ArrayList<>();

        File coursesFile = new File("dat/addresses.txt");

        try (BufferedReader lineReader = new BufferedReader(new FileReader(coursesFile))) {
            String line;

            while((line = lineReader.readLine()) != null)  {
                Long id = Long.parseLong(line);
                String factoryStreet = lineReader.readLine();
                String factoryHouseNumber = lineReader.readLine();
                String cityString = lineReader.readLine();
                City city = null;
                switch (cityString.toLowerCase()){
                    case "sisak":
                        city= City.SISAK;
                        break;
                    case "zagreb":
                        city= City.ZAGREB;
                        break;
                    case "crikvenica":
                        city= City.CRIKVENICA;
                        break;
                    case "velika gorica":
                        city= City.VELIKAGORICA;
                        break;
                }

                Address newAddress = new Address().Builder()
                        .Id(id)
                        .Street(factoryStreet)
                        .HouseNumber(factoryHouseNumber)
                        .City(city);
                addressList.add(newAddress);
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

        return addressList;
    }
}

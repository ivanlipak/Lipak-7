package com.example.production.documents;

import com.example.production.enums.City;
import com.example.production.model.*;
import javafx.fxml.FXML;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

public class Database {

    private static Connection databaseConnection() throws SQLException {
        Properties props = new Properties();
        try {
            props.load(new FileReader("dat/database-properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String databaseUrl = props.getProperty("databaseUrl");
        String username = props.getProperty("username");
        String password = props.getProperty("password");

        Connection con = DriverManager.getConnection(
                    databaseUrl,
                    username,
                    password
            );
        return con;
    }

    public static void databaseCloseConnection(Connection con) throws SQLException {
        con.close();
    }

    public static List<Category> databaseReadCategories() throws SQLException {
        Connection con = databaseConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM category");
        List<Category> catList = new ArrayList<>();

        while(rs.next()){
            Long id = rs.getLong("ID");
            String catName = rs.getString("name");
            String catDesc = rs.getString("description");

            Category newCategory = new Category(id,
                    catName,
                    catDesc);
            catList.add(newCategory);
        }

        databaseCloseConnection(con);
        return catList;
    }

    public static List<Item> databaseReadItems() throws SQLException {
        Connection con = databaseConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM item");
        List<Item> itemList = new ArrayList<>();

        while (rs.next()){
            Long itemId = rs.getLong("id");
            Long categoryId = rs.getLong("category_id");
            String itemName = rs.getString("name");
            BigDecimal itemWidth = rs.getBigDecimal("width");
            BigDecimal itemHeight = rs.getBigDecimal("height");
            BigDecimal itemLength = rs.getBigDecimal("length");
            BigDecimal itemProductionCost = rs.getBigDecimal("production_cost");
            BigDecimal itemSellingPrice = rs.getBigDecimal("selling_price");
            BigDecimal discount = rs.getBigDecimal("DISCOUNT");
            Category categoryForNewItem = null;
            for(Category category : databaseReadCategories()){
                if(category.getId().equals(categoryId)){
                    categoryForNewItem = category;
                }
            }

            Item newItem = new Item(
                    itemId,
                    itemName,
                    categoryForNewItem,
                    itemWidth,
                    itemHeight,
                    itemLength,
                    itemProductionCost,
                    itemSellingPrice,
                    new Discount(discount));
            itemList.add(newItem);
        }

        databaseCloseConnection(con);
        return itemList;
    }

    public static List<Address> databaseReadAddresses() throws SQLException {
        List<Address> addressList = new ArrayList<>();
        Connection con = databaseConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM address");

         while(rs.next()){
             Long addressId = rs.getLong("id");
             String street = rs.getString("street");
             String houseNr = rs.getString("house_number");
             String cityString = rs.getString("city");

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
                 case "velikagorica":
                     city= City.VELIKAGORICA;
                     break;
             }

             Address newAddress = new Address().Builder()
                     .Id(addressId)
                     .Street(street)
                     .HouseNumber(houseNr)
                     .City(city);

             addressList.add(newAddress);
         }

        databaseCloseConnection(con);
        return addressList;
    }

    public static List<Factory> databaseReadFactories() throws SQLException {
        List<Factory> factoryList = new ArrayList<>();
        Connection con = databaseConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM factory");
        while (rs.next()){
            Set<Item> itemSet = new HashSet<>();
            Long factoryId = rs.getLong("id");
            String name = rs.getString("name");
            Long addressID = rs.getLong("id");
            Address address = null;
            for(Address add : databaseReadAddresses()){
                if (add.getId().equals(addressID)){
                    address = add;
                }
            }
            Statement stmt2 = con.createStatement();
            ResultSet rs2 = stmt2.executeQuery(
                    "SELECT * FROM FACTORY_ITEM FI, ITEM I  " +
                    "WHERE FI.FACTORY_ID =" + factoryId +
                    "AND FI.ITEM_ID = I.ID;");
            while(rs2.next()){
                Long itemId = rs2.getLong("item_id");
                for(Item item : databaseReadItems()){
                    if(item.getId().equals(itemId)){
                        itemSet.add(item);
                    }
                }
            }
            Factory newFactory = new Factory(
                    factoryId,
                    name,
                    address,
                    itemSet);
            factoryList.add(newFactory);
        }
        databaseCloseConnection(con);
        return factoryList;
    }

    public static List<Store> databaseReadStores() throws SQLException {
        List<Store> storeList = new ArrayList<>();
        Connection con = databaseConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM store");
        while (rs.next()){
            Set<Item> itemSet = new HashSet<>();
            Long id = rs.getLong("id");
            String name = rs.getString("name");
            String www = rs.getString("web_address");
            Statement stmt2 = con.createStatement();
            ResultSet rs2 = stmt2.executeQuery(
                    "SELECT * FROM STORE_ITEM SI, ITEM I  " +
                            "WHERE SI.STORE_ID =" + id +
                            "AND SI.ITEM_ID = I.ID;");
            while(rs2.next()){
                Long itemId = rs2.getLong("item_id");
                for(Item item : databaseReadItems()){
                    if(item.getId().equals(itemId)){
                        itemSet.add(item);
                    }
                }
            }
            Store newStore = new Store(
                    id,
                    name,
                    www,
                    itemSet);
            storeList.add(newStore);
        }
        databaseCloseConnection(con);
        return storeList;
    }

    public static Item getItemById(Long id) throws SQLException {
        Connection con = databaseConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM item WHERE id =" + id);
        rs.next();
        Long itemId = rs.getLong("id");
        Long categoryId = rs.getLong("category_id");
        String itemName = rs.getString("name");
        BigDecimal itemWidth = rs.getBigDecimal("width");
        BigDecimal itemHeight = rs.getBigDecimal("height");
        BigDecimal itemLength = rs.getBigDecimal("length");
        BigDecimal itemProductionCost = rs.getBigDecimal("production_cost");
        BigDecimal itemSellingPrice = rs.getBigDecimal("selling_price");
        BigDecimal discount = rs.getBigDecimal("DISCOUNT");
        Category category = null;
        for ( Category cat : databaseReadCategories()){
            if(cat.getId().equals(categoryId)){
                category = cat;
            }
        }

        Item newItem = new Item(
                itemId,
                itemName,
                category,
                itemWidth,
                itemHeight,
                itemLength,
                itemProductionCost,
                itemSellingPrice,
                new Discount(discount));
        con.close();
        return newItem;
    }

    public static Category getCategoryById (Long id) throws SQLException {
        Connection con = databaseConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM category WHERE id = 1");
        rs.next();
        Long catid = rs.getLong("ID");
        String catName = rs.getString("name");
        String catDesc = rs.getString("description");

        Category newCategory = new Category(catid,
                catName,
                catDesc);
        con.close();
        return newCategory;
    }

    public static Store getStoreById (Long id) throws SQLException {
        Connection con = databaseConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM store WHERE id =" + id);
        rs.next();
        Set<Item> itemSet = new HashSet<>();
        Long storeid = rs.getLong("id");
        String name = rs.getString("name");
        String www = rs.getString("web_address");
        Statement stmt2 = con.createStatement();
        ResultSet rs2 = stmt2.executeQuery(
                "SELECT * FROM STORE_ITEM SI, ITEM I  " +
                        "WHERE SI.STORE_ID =" + id +
                        "AND SI.ITEM_ID = I.ID;");
        while(rs2.next()){
            Long itemId = rs2.getLong("item_id");
            for(Item item : databaseReadItems()){
                if(item.getId().equals(itemId)){
                    itemSet.add(item);
                }
            }
        }
        Store newStore = new Store(
                storeid,
                name,
                www,
                itemSet);
        con.close();
        return newStore;
    }

    public static Factory getFactoryById (Long id) throws SQLException {
        Connection con = databaseConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM factory WHERE id =" + id);
        rs.next();
        Set<Item> itemSet = new HashSet<>();
        Long factoryId = rs.getLong("id");
        String name = rs.getString("name");
        Long addressID = rs.getLong("id");
        Statement stmt2 = con.createStatement();
        ResultSet rs2 = stmt2.executeQuery(
                "SELECT * FROM FACTORY_ITEM FI, ITEM I  " +
                        "WHERE FI.FACTORY_ID =" + factoryId +
                        "AND FI.ITEM_ID = I.ID;");
        while(rs2.next()){
            Long itemId = rs2.getLong("item_id");
            for(Item item : databaseReadItems()){
                if(item.getId().equals(itemId)){
                    itemSet.add(item);
                }
            }
        }
        Address address = null;
        for(Address add : databaseReadAddresses()){
            if(add.getId().equals(addressID)){
                address = add;
            }
        }
        Factory newFactory = new Factory(
                factoryId,
                name,
                address,
                itemSet);
        con.close();
        return newFactory;
    }

    public static Address getAddressById (Long id) throws SQLException {
        Connection con = databaseConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM address WHERE id =" + id);
        rs.next();
        Long addressId = rs.getLong("id");
        String street = rs.getString("street");
        String houseNr = rs.getString("house_number");
        String cityString = rs.getString("city");

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
            case "velikagorica":
                city= City.VELIKAGORICA;
                break;
        }

        Address newAddress = new Address().Builder()
                .Id(addressId)
                .Street(street)
                .HouseNumber(houseNr)
                .City(city);
        con.close();
        return newAddress;
    }

    public static void addAddress(Address address) throws SQLException {
        Connection con = databaseConnection();
        PreparedStatement pstmt = con.prepareStatement("INSERT INTO address(STREET, HOUSE_NUMBER, CITY, POSTAL_CODE) "
                + "VALUES(?, ?, ?, ?)");
        pstmt.setString(1, address.getStreet());
        pstmt.setString(2, address.getHouseNumber());
        pstmt.setString(3, address.getCity().getCityName());
        pstmt.setInt(4, Integer.valueOf(address.getCity().getPostNr()));
        pstmt.executeUpdate();
        con.close();
    }

    public static void deleteCategoryById (Long id) throws SQLException {
        Connection con = databaseConnection();
        Statement stmt = con.createStatement();
        stmt.execute("DELETE FROM category WHERE id =" + id);
        con.close();
    }

    public static void updateCategoryById(Long id, String name, String desc) throws SQLException {
        Connection con = databaseConnection();
        PreparedStatement ps = con.prepareStatement("update category set name = ?, description = ? where id =" + id);
        ps.setString(1, name);
        ps.setString(2, desc);
        ps.executeUpdate();
        con.close();
    }

    public static void addCategory(Category category) throws Exception {
        Connection con = databaseConnection();
        PreparedStatement pstmt = con.prepareStatement("INSERT INTO CATEGORY(NAME, DESCRIPTION) "
                + "VALUES(?, ?)");
        pstmt.setString(1, category.getName());
        pstmt.setString(2, category.getDescription());
        pstmt.executeUpdate();
        con.close();
    }

    public static void addFactory(String name, Long addressId) throws Exception {
        Connection con = databaseConnection();
        PreparedStatement pstmt = con.prepareStatement("INSERT INTO Factory(NAME, ADDRESS_ID) "
                + "VALUES(?, ?)");
        pstmt.setString(1, name);
        pstmt.setLong(2, addressId);
        pstmt.executeUpdate();
        con.close();
    }

    public static void deleteItemById(Long id) throws SQLException {
        Connection con = databaseConnection();
        Statement stmt = con.createStatement();
        stmt.execute("DELETE FROM store_item WHERE item_id =" + id);
        stmt.execute("DELETE FROM factory_item WHERE item_id =" + id);
        stmt.execute("DELETE FROM item WHERE id =" + id);
        con.close();
    }

    public static void addFactoryItem(Factory factory, Item item) throws SQLException {
        Connection con = databaseConnection();
        PreparedStatement pstmt = con.prepareStatement("INSERT INTO FACTORY_ITEM(factory_id, item_id) "
                + "VALUES(?, ?)");
        pstmt.setLong(1, factory.getId());
        pstmt.setLong(2, item.getId());
        pstmt.executeUpdate();
        con.close();
    }

    public static void updateItem(Item item) throws SQLException {
        Connection con = databaseConnection();
        PreparedStatement ps = con.prepareStatement(
                "update item set " +
                "category_id = ?, " +
                "name = ?, " +
                "width = ?," +
                "height = ?," +
                "length = ?," +
                "production_cost = ?," +
                "selling_price = ?," +
                "discount = ?" +
                "where id =" + item.getId());
        ps.setLong(1, item.getCategory().getId());
        ps.setString(2, item.getName());
        ps.setBigDecimal(3, item.getWidth());
        ps.setBigDecimal(4, item.getHeight());
        ps.setBigDecimal(5, item.getLength());
        ps.setBigDecimal(6, item.getProductionCost());
        ps.setBigDecimal(7, item.getSellingPrice());
        ps.setBigDecimal(8, item.getDiscount().discountAmount());
        ps.executeUpdate();
        con.close();
    }

    public static void addItem(Item item) throws Exception {
        Connection con = databaseConnection();
        PreparedStatement pstmt = con.prepareStatement("INSERT INTO ITEM(" +
                "category_id, " +
                "name, " +
                "width, " +
                "height, " +
                "length , " +
                "production_cost, " +
                "selling_price, " +
                "discount) "
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
        pstmt.setLong(1, item.getCategory().getId());
        pstmt.setString(2, item.getName());
        pstmt.setBigDecimal(3, item.getWidth());
        pstmt.setBigDecimal(4, item.getHeight());
        pstmt.setBigDecimal(5, item.getLength());
        pstmt.setBigDecimal(6, item.getProductionCost());
        pstmt.setBigDecimal(7, item.getSellingPrice());
        pstmt.setBigDecimal(8, item.getDiscount().discountAmount());
        pstmt.executeUpdate();
        con.close();
    }


}

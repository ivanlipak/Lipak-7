package com.example.production.model;


import com.example.production.enums.*;

import java.io.Serializable;
import java.util.Objects;

public class Address implements Serializable {

    private Long id;
    private String street;
    private String houseNumber;
    private City city;

    public Address Id (Long id) {
        this.id = id;
        return this;
    }

    public Address Street(String street){
        this.street = street;
        return this;
    }

    public Address HouseNumber(String houseNumber){
        this.houseNumber = houseNumber;
        return this;
    }

    public Address City(City city){
        this.city = city;
        return this;
    }

    public Address Builder(){
        Address address = new Address();
        address.id = this.id;
        address.street = this.street;
        address.houseNumber = this.houseNumber;
        address.city = this.city;

        return address;
    }

    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }
    public String getHouseNumber() {
        return houseNumber;
    }
    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }
    public City getCity() {
        return city;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", street='" + street + '\'' +
                ", houseNumber='" + houseNumber + '\'' +
                ", city=" + city +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return street.equals(address.street) && houseNumber.equals(address.houseNumber) && city.equals(address.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, houseNumber, city);
    }
}

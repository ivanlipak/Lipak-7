package com.example.production.enums;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum City {

    ZAGREB ("Zagreb", "10000"),
    VELIKAGORICA ("Velika Gorica", "10410"),
    SISAK ("Sisak", "44000"),
    CRIKVENICA ("Crikvenica", "51260");


    private String cityName, postNr;

    City(String cityName, String postNr) {
        this.cityName = cityName;
        this.postNr = postNr;
    }

    public String getCityName() {
        return cityName;
    }

    public String getPostNr() {
        return postNr;
    }

    public static String printAll(){
        return Stream.of(City.values())
                .map(City::getCityName).collect(Collectors.joining(", "));
    }

}

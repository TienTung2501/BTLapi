package com.example.btlapi.Domain;

public class Price {
    private int Id;
    private String Prices;


    public Price(int id, String prices) {
        Id = id;
        Prices = prices;
    }

    @Override
    public String toString() {
        return   Prices;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getValue() {
        return Prices;
    }

    public void setValue(String value) {
        Prices = value;
    }
}

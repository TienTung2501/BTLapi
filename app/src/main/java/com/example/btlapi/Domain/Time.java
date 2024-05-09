package com.example.btlapi.Domain;

public class Time {
    private int Id;
    private String Times;

    public Time(int id, String times) {
        Id = id;
        Times = times;
    }

    @Override
    public String toString() {
        return Times;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getValue() {
        return Times;
    }

    public void setValue(String value) {
        Times = value;
    }
}

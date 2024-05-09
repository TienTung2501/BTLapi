package com.example.btlapi.Domain;

public class Location {
    private int Id;
    private String Locations;


    @Override
    public String toString() {
        return Locations;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public Location(int id, String locations) {
        Id = id;
        Locations = locations;
    }

    public String getLoc() {
        return Locations;
    }

    public void setLoc(String loc) {
        Locations = loc;
    }
}
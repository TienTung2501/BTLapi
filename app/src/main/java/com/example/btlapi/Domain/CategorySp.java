package com.example.btlapi.Domain;

public class CategorySp {
    private int Id;
    private String Names;

    public CategorySp(int id, String names) {
        Id = id;
        Names = names;
    }

    public int getId() {
        return Id;
    }

    public String getNames() {
        return Names;
    }

    @Override
    public String toString() {
        return Names;
    }
}

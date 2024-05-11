package com.example.btlapi.Domain;

public class CategorySp {
    private int Id;
    private String Names;

    public int getId() {
        return Id;
    }

    public String getNames() {
        return Names;
    }

    public void setId(int id) {
        Id = id;
    }

    public CategorySp(int id, String names) {
        Id = id;
        Names = names;
    }
}

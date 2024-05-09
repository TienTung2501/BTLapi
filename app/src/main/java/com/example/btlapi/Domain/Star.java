package com.example.btlapi.Domain;

public class Star {
    private int Id;
    private String Stars;

    public Star(int id, String stars) {
        Id = id;
        Stars = stars;
    }

    public int getId() {
        return Id;
    }

    public String getStars() {
        return Stars;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setStars(String stars) {
        Stars = stars;
    }
}

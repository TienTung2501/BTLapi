package com.example.btlapi.Request;

import com.google.gson.annotations.SerializedName;

public class FoodRequest {
    @SerializedName("bestfood")
    private int bestFood;

    @SerializedName("location")
    private int location;

    @SerializedName("time")
    private int time;

    @SerializedName("price")
    private int price;

    public FoodRequest(int bestFood, int location, int time, int price) {
        this.bestFood = bestFood;
        this.location = location;
        this.time = time;
        this.price = price;
    }
}

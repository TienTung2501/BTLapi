package com.example.btlapi.Interface;
import com.example.btlapi.Domain.Price;
import com.example.btlapi.Domain.Time;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
public interface PriceInterface {
    @GET("/filter/getall_price")
    Call<ArrayList<Price>> getAllPrices();
}

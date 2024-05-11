package com.example.btlapi.Interface;

import com.example.btlapi.Domain.Star;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface StarInterface {
    @GET("/filter/getall_star")
    Call<ArrayList<Star>> getAllStars();
}

package com.example.btlapi.Interface;

import com.example.btlapi.Domain.Location;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface LocationInterface {
    @GET("/filter/getall_location")
    Call<ArrayList<Location>> getAllLocations();
}

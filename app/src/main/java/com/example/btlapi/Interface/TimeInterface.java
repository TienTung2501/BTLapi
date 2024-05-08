package com.example.btlapi.Interface;

import com.example.btlapi.Domain.Time;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
public interface TimeInterface {
    @GET("/filter/getall_time")
    Call<ArrayList<Time>> getAllTimes();
}

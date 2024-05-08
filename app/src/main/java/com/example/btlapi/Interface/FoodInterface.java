package com.example.btlapi.Interface;

import com.example.btlapi.Domain.Food;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FoodInterface {
    @GET("/food/searchFoodByText")
    Call<ArrayList<Food>> searchFoodByText(@Query("searchText") String searchText);

    @GET("/food/getfoodby_bestfood_location_time_price")
    Call<ArrayList<Food>> getFoodByLocationOrPriceOrTime(@Query("bestfood") String bestFood, @Query("time") String time, @Query("price") String price, @Query("location") String location);

    @GET("/food/getfoodby_Category")
    Call<ArrayList<Food>> getFoodByCategory(@Query("idCategory") int idCategory);

    @GET("/food/getdetailfoodby_id")
    Call<Food> getDetailFoodById(@Query("idFood") int idFood);
}

package com.example.btlapi.Interface;

import com.example.btlapi.Domain.Food;
import com.example.btlapi.Request.FoodRequest;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface FoodInterface {
    @GET("/food/searchFoodByText")
    Call<ArrayList<Food>> searchFoodByText(@Query("searchText") String searchText);
    @GET("/food/getfoodby_bestfood_location_time_price_star_category_searchtext")
    Call<ArrayList<Food>> getFood(
            @Query("bestfood") Integer bestFood,
            @Query("location") Integer location,
            @Query("time") Integer time,
            @Query("price") Integer price,
            @Query("star") Integer star,
            @Query("category") Integer category,
            @Query("searchText") String searchtext
    );



    @GET("/food/getfoodby_Category")
    Call<ArrayList<Food>> getFoodByCategory(@Query("idCategory") int idCategory);

    @GET("/food/getdetailfoodby_id")
    Call<Food> getDetailFoodById(@Query("idFood") int idFood);
}

package com.example.btlapi.Interface;

import com.example.btlapi.Domain.Category;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoryInterface {
    @GET("/filter/getall_category")
    Call<ArrayList<Category>> getAllCategories();
}

package com.example.btlapi.Interface;

import com.example.btlapi.Domain.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserInterface {
    @GET("/authenticate/user/by/phone_and_password")
    Call<User> getUser(@Query("Mobile") String Mobile, @Query("Passwords") String Passwords);
    @POST("/user/insert")
    Call<User> insertUser(@Body User user);
}

package com.example.btlapi.Interface;

import com.example.btlapi.Domain.Order;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OrderInterface {
    @GET("/order/getall_order_by_userid")
    Call<ArrayList<Order>> getAllOrdersByUserId(@Query("userId") int userId, @Query("orderStatus") String orderStatus);
}

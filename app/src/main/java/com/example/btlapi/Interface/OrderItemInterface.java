package com.example.btlapi.Interface;

import com.example.btlapi.Domain.OrderItem;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OrderItemInterface {
    @GET("/order/getall_orderitem_orderid")
    Call<ArrayList<OrderItem>> getAllOrderItems(@Query("orderId") int orderId);
}

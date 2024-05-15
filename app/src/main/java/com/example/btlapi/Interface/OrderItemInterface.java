package com.example.btlapi.Interface;

import com.example.btlapi.Domain.OrderItem;
import com.example.btlapi.Request.OrderItemRequest;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface OrderItemInterface {
    @GET("/order/getall_orderitem_orderid")
    Call<ArrayList<OrderItem>> getAllOrderItems(@Query("orderId") int orderId);
    @POST("/order/insert_order_item")
    Call<OrderItemRequest> insertOrderItem(@Body OrderItemRequest orderItem);
}

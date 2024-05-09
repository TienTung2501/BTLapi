package com.example.btlapi;

import android.widget.Toast;

import com.example.btlapi.Activity.CartActivity;
import com.example.btlapi.Domain.OrderItem;
import com.example.btlapi.Interface.OrderItemInterface;
import com.example.btlapi.Utils.utils;

import java.util.Queue;
import java.util.LinkedList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderItemQueue {
    private Queue<OrderItem> queue;
    private boolean processing;

    public OrderItemQueue() {
        this.queue = new LinkedList<>();
        this.processing = false;
    }

    public synchronized void enqueue(OrderItem item) {
        queue.offer(item);
        processQueue();
    }

    private synchronized void processQueue() {
        if (!processing && !queue.isEmpty()) {
            processing = true;
            OrderItem item = queue.poll();
            insertOrderItem(item, new Callback<OrderItem>() {
                @Override
                public void onResponse(Call<OrderItem> call, Response<OrderItem> response) {
                    processing = false;
                    processQueue(); // Xử lý tiếp tục hàng đợi sau khi kết thúc xử lý hiện tại
                }

                @Override
                public void onFailure(Call<OrderItem> call, Throwable t) {
                    processing = false;
                    processQueue(); // Xử lý tiếp tục hàng đợi sau khi kết thúc xử lý hiện tại
                }
            });
        }
    }

    private void insertOrderItem(OrderItem item, Callback<OrderItem> callback) {
        OrderItemInterface orderItemInterface;
        orderItemInterface = utils.getOrderItemService();
        OrderItem user = new OrderItem(item.getId(),item.getOrderId(),item.getProductId(),item.getQuantity(),item.getPrice());
        System.out.println(item.getId()+" "+item.getOrderId()+" "+item.getProductId()+" "+item.getQuantity()+" "+item.getPrice());
        orderItemInterface.insertOrderItem(user).enqueue(new Callback<OrderItem>() {
            @Override
            public void onResponse(Call<OrderItem> call, Response<OrderItem> response) {
                if(response.isSuccessful()){
//                    Toast.makeText(,"Thanh Cong",Toast.LENGTH_SHORT);
                }

//                    Toast.makeText(CartActivity.this,"Hello",Toast.LENGTH_SHORT);
            }

            @Override
            public void onFailure(Call<OrderItem> call, Throwable t) {
//                Toast.makeText(CartActivity.this,"Loi",Toast.LENGTH_SHORT);
            }
        });
    }
}

package com.example.btlapi.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.btlapi.Adapter.ListOrderAdapter;
import com.example.btlapi.Adapter.ListOrderDetailAdapter;
import com.example.btlapi.Domain.Food;
import com.example.btlapi.Domain.Order;
import com.example.btlapi.Domain.OrderItemDisplay;
import com.example.btlapi.GlobalVariable;
import com.example.btlapi.Interface.OrderInterface;
import com.example.btlapi.Interface.OrderItemInterface;
import com.example.btlapi.R;
import com.example.btlapi.Utils.utils;
import com.example.btlapi.databinding.ActivityOrderDetailBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailActivity extends AppCompatActivity {
    ActivityOrderDetailBinding binding;
    Order object;
    ArrayList<OrderItemDisplay> orderItems;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityOrderDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        orderItems=new ArrayList<>();
        getIntentExtra();
        loadData();
    }

    private void loadData() {
        binding.orderIdTxt.setText("Your order Id:"+String.valueOf(object.getId()));
        binding.priceTxt.setText(String.valueOf(object.getTotalPrice()));
        binding.paymentTxt.setText(String.valueOf(object.getPaymentStatus()));
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        String formattedDate = sdf.format(object.getOrderDate());
        binding.orderDateTxt.setText(formattedDate);
    }

    private void getIntentExtra() {
        Intent intent = getIntent();
        object = (Order) intent.getSerializableExtra("object");
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        loadData();
        initOrderItem();
    }

    private void initOrderItem() {
        OrderItemInterface orderItemInterface;
        orderItemInterface = utils.getOrderItemService();
        orderItemInterface.getAllOrderItemDisplay(object.getId())
                .enqueue(new Callback<ArrayList<OrderItemDisplay>>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(Call<ArrayList<OrderItemDisplay>> call, Response<ArrayList<OrderItemDisplay>> response) {
                        if (response.isSuccessful()) {
                            ArrayList<OrderItemDisplay> result = response.body();
//                            Toast.makeText(OrderActivity.this, String.valueOf(result.get(0).getOrderStatus()), Toast.LENGTH_SHORT).show();
                            if (result != null && !result.isEmpty()) {
                                orderItems.clear(); // Clear old data
                                orderItems.addAll(result); // Add new data from the response

                                LinearLayoutManager layoutManager = new LinearLayoutManager(OrderDetailActivity.this);
                                binding.listOrder.setLayoutManager(layoutManager);// Initialize adapterListFood only when it's null

                                adapter = new ListOrderDetailAdapter(orderItems);
                                binding.listOrder.setAdapter(adapter);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<OrderItemDisplay>> call, Throwable t) {
                        Log.e("OrderActivity", "Order data fetch failed", t);
                        Toast.makeText(OrderDetailActivity.this, "Order data fetch failed", Toast.LENGTH_SHORT).show();
                    }
                });

    }

}
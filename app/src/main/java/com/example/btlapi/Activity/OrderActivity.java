package com.example.btlapi.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.btlapi.Adapter.ListFoodAdapter;
import com.example.btlapi.Adapter.ListOrderAdapter;
import com.example.btlapi.Domain.Food;
import com.example.btlapi.Domain.Order;
import com.example.btlapi.Domain.OrderItem;
import com.example.btlapi.GlobalVariable;
import com.example.btlapi.Interface.OrderInterface;
import com.example.btlapi.R;
import com.example.btlapi.Utils.utils;
import com.example.btlapi.databinding.ActivityOrderBinding;
import com.example.btlapi.databinding.ActivityOrderDetailBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderActivity extends AppCompatActivity {
    ActivityOrderBinding binding;
    private ArrayList<Order> orders;
    private RecyclerView.Adapter adapterOrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        orders = new ArrayList<>();
        getIntentExtra();
        loadData();
    }

    private void getIntentExtra() {
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadData() {
        OrderInterface orderInterface;
        orderInterface = utils.getOrderService();
        orderInterface.getAllOrdersByUserId(GlobalVariable.userId, "Processing")
                .enqueue(new Callback<ArrayList<Order>>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(Call<ArrayList<Order>> call, Response<ArrayList<Order>> response) {
                        if (response.isSuccessful()) {
                            ArrayList<Order> result = response.body();
//                            Toast.makeText(OrderActivity.this, String.valueOf(result.get(0).getOrderStatus()), Toast.LENGTH_SHORT).show();
                            if (result != null && !result.isEmpty()) {
                                    orders.clear(); // Clear old data
                                    orders.addAll(result); // Add new data from the response

                                    LinearLayoutManager layoutManager = new LinearLayoutManager(OrderActivity.this);
                                    binding.listOrder.setLayoutManager(layoutManager);// Initialize adapterListFood only when it's null

                                    adapterOrder = new ListOrderAdapter(orders);
                                    binding.listOrder.setAdapter(adapterOrder);
                                }
                            }
                        }

                    @Override
                    public void onFailure(Call<ArrayList<Order>> call, Throwable t) {
                        Log.e("OrderActivity", "Order data fetch failed", t);
                        Toast.makeText(OrderActivity.this, "Order data fetch failed", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}

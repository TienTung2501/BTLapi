package com.example.btlapi.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btlapi.Adapter.CartAdapter;
import com.example.btlapi.GlobalVariable;
import com.example.btlapi.Interface.OrderInterface;
import com.example.btlapi.Interface.OrderItemInterface;
import com.example.btlapi.OrderItemManager;
import com.example.btlapi.R;
import com.example.btlapi.Domain.Order;
import com.example.btlapi.Domain.OrderItem;
import com.example.btlapi.Request.OrderItemRequest;
import com.example.btlapi.Request.OrderRequest;
import com.example.btlapi.Utils.utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.btlapi.databinding.ActivityCartBinding;

import androidx.recyclerview.widget.LinearLayoutManager;
public class CartActivity extends AppCompatActivity {
    private ActivityCartBinding binding;

    RecyclerView cardView;
    TextView TextViewtotal;
    AppCompatButton placeOrder;
    CartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getIntentExtra();
        setVariable();
        InitWiget();

        cardView.setLayoutManager(new LinearLayoutManager(CartActivity.this));
        if (!OrderItemManager.getOrderItems(this,GlobalVariable.userId).isEmpty()) {
            ArrayList<OrderItem> item = new ArrayList<>(OrderItemManager.getOrderItems(this,GlobalVariable.userId));
            System.out.println("Trung 123: " + OrderItemManager.getOrderItems(this,GlobalVariable.userId).get(0).getProductId());
            adapter = new CartAdapter(CartActivity.this, item, GlobalVariable.listFood);
            cardView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<OrderItem> listItemInsert = new ArrayList<>(OrderItemManager.getOrderItems(CartActivity.this,GlobalVariable.userId));
                if (!listItemInsert.isEmpty()){
                    String priceString = TextViewtotal.getText().toString();
                    String numberString = priceString.substring(1); // Bỏ qua ký tự đầu tiên là "$"
                    double price = Double.parseDouble(numberString);
                    OrderRequest item = new OrderRequest(String.valueOf(GlobalVariable.userId),"Processing","Pending",String.valueOf(price));
                    insertOrder(item,listItemInsert);
                }
            }
        });
    }


    private void getIntentExtra() {
        Intent intent = getIntent();
    }
    private void setVariable(){
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void insertOrder (OrderRequest item,ArrayList<OrderItem> listItemInsert){

        OrderInterface orderInterface;
        orderInterface = utils.getOrderService();
        orderInterface.insertOrder(item).enqueue(new Callback<OrderRequest>() {
            @Override
            public void onResponse(Call<OrderRequest> call, Response<OrderRequest> response) {
                if(response.isSuccessful()){
                    Toast.makeText(CartActivity.this,"Thanh Cong",Toast.LENGTH_SHORT).show();
                    initOrder(GlobalVariable.userId,listItemInsert);
                }
                else{
                    Toast.makeText(CartActivity.this,"Hello",Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<OrderRequest> call, Throwable t) {
                Toast.makeText(CartActivity.this,"Loi",Toast.LENGTH_SHORT).show();
                System.out.println("Lỗi nặng: " + t.getMessage());
            }
        });
    }
    private void insertOrderItem(OrderItemRequest item){
        System.out.println("Test Order item: "+item.getOrderId()+" "+item.getProductId()+" "+item.getPrice()+" "+item.getQuantity());
        OrderItemInterface orderItemInterface;
        orderItemInterface = utils.getOrderItemService();

        orderItemInterface.insertOrderItem(item).enqueue(new Callback<OrderItemRequest>() {
            @Override
            public void onResponse(Call<OrderItemRequest> call, Response<OrderItemRequest> response) {
                if(response.isSuccessful()){
                    Toast.makeText(CartActivity.this,"Thanh Cong",Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(CartActivity.this,"Hello",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<OrderItemRequest> call, Throwable t) {
                Toast.makeText(CartActivity.this,"Loi",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initOrder(int userId,ArrayList<OrderItem>listItemInsert) {
        final int[] orderId = new int[1];
        OrderInterface orderInterface = utils.getOrderService();
        orderInterface.getAllOrdersByUserId(userId, "Processing").enqueue(new Callback<ArrayList<Order>>() {
            @Override
            public void onResponse(Call<ArrayList<Order>> call, Response<ArrayList<Order>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Order> result = new ArrayList<>(response.body());
                    if (result != null && !result.isEmpty()) {
//                        GlobalVariable.listOrder.addAll(result);
                        for (OrderItem x: listItemInsert){
                            OrderItemRequest itemInsert = new OrderItemRequest(String.valueOf(result.get(result.size()-1).getId()),String.valueOf(x.getProductId()),String.valueOf(x.getPrice()),String.valueOf(x.getQuantity()));
                            System.out.println("Test Order ID : "+itemInsert.getOrderId());
                            insertOrderItem(itemInsert);
                        }
                        Toast.makeText(CartActivity.this, "Thanh toán thành công", Toast.LENGTH_SHORT).show();
                        OrderItemManager.clearOrderItems(CartActivity.this,GlobalVariable.userId);
                        adapter.setOrderItems(new ArrayList<>());
                        adapter.notifyDataSetChanged();

                    }

                }
            }

            @Override
            public void onFailure(Call<ArrayList<Order>> call, Throwable t) {
                Toast.makeText(CartActivity.this, "Failed to fetch orders", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void InitWiget() {
        cardView =(RecyclerView) findViewById(R.id.cardView);
        placeOrder = (AppCompatButton) findViewById(R.id.btnPlaceOrder);
        TextViewtotal = (TextView) findViewById(R.id.textViewTotal);
    }

}


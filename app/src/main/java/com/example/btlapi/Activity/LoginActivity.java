package com.example.btlapi.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.btlapi.Domain.Food;
import com.example.btlapi.Domain.Order;
import com.example.btlapi.Domain.OrderItem;
import com.example.btlapi.Domain.User;
import com.example.btlapi.GlobalVariable;
import com.example.btlapi.Interface.FoodInterface;
import com.example.btlapi.Interface.OrderInterface;
import com.example.btlapi.Interface.OrderItemInterface;
import com.example.btlapi.Interface.UserInterface;
import com.example.btlapi.R;
import com.example.btlapi.Utils.utils;
import com.example.btlapi.databinding.ActivityLoginBinding;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
        private ActivityLoginBinding binding;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                binding = ActivityLoginBinding.inflate(getLayoutInflater());
                setContentView(binding.getRoot());
                evenListener();
        }

        private void evenListener() {
                binding.loginBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                String mobile = binding.userEdt.getText().toString();
                                String password = binding.passEdt.getText().toString();

                                if (password.isEmpty()) {
                                        Toast.makeText(LoginActivity.this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
                                } else {
                                        getUserFromApi(mobile, password);
                                }
                        }
                });
                binding.textViewSignUp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
                        }
                });
        }
        private void getUserFromApi(String mobile, String password) {
                UserInterface userInterface = utils.getUserService();
                userInterface.getUser(mobile, password).enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                                if (response.isSuccessful()) {
                                        User result=response.body();
                                        GlobalVariable.islogin = true;
                                        GlobalVariable.userId = result.getId();
                                        GlobalVariable.userName = result.getNames();
                                        GlobalVariable.phone = result.getMobile();
                                        GlobalVariable.email = result.getAddresss();
                                        int userId=result.getId();
                                        initOrder(userId);
                                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                } else {
                                        Toast.makeText(LoginActivity.this, "No data user respone", Toast.LENGTH_SHORT).show();
                                }
                        }
                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                                Toast.makeText(LoginActivity.this, "Fetch Failed", Toast.LENGTH_SHORT).show();
                        }
                });
        }

        private void initOrder(int userId) {
                OrderInterface orderInterface = utils.getOrderService();
                orderInterface.getAllOrdersByUserId(userId, "Processing").enqueue(new Callback<ArrayList<Order>>() {
                        @Override
                        public void onResponse(Call<ArrayList<Order>> call, Response<ArrayList<Order>> response) {
                                if (response.isSuccessful()) {
                                        ArrayList<Order> result = new ArrayList<>(response.body());
                                        if (result != null && !result.isEmpty()) {
                                                GlobalVariable.listOrder.addAll(result);
                                                int orderId = result.get(0).getId();
                                                initOrderItem(orderId);
                                        }
                                }
                        }

                        @Override
                        public void onFailure(Call<ArrayList<Order>> call, Throwable t) {
                                Toast.makeText(LoginActivity.this, "Failed to fetch orders", Toast.LENGTH_SHORT).show();
                        }
                });
        }

        private void initOrderItem(int orderId) {
                OrderItemInterface orderItemInterface = utils.getOrderItemService();
                orderItemInterface.getAllOrderItems(orderId)
                        .enqueue(new Callback<ArrayList<OrderItem>>() {
                                @Override
                                public void onResponse(Call<ArrayList<OrderItem>> call, Response<ArrayList<OrderItem>> response) {
                                        if (response.isSuccessful()) {
                                                ArrayList<OrderItem> result = new ArrayList<>(response.body());
                                                if (result != null && !result.isEmpty()) {
                                                        GlobalVariable.listOrderItem.addAll(result);
                                                        initListFoods();
                                                }
                                        }
                                }
                                @Override
                                public void onFailure(Call<ArrayList<OrderItem>> call, Throwable t) {
                                        Toast.makeText(LoginActivity.this, "Failed to fetch order items", Toast.LENGTH_SHORT).show();
                                }
                        });
        }

        private void initListFoods() {
                FoodInterface foodInterface = utils.getFoodService();
                foodInterface.getFood(null, null, null, null, null, null, null)
                        .enqueue(new Callback<ArrayList<Food>>() {
                                @Override
                                public void onResponse(Call<ArrayList<Food>> call, Response<ArrayList<Food>> response) {
                                        if (response.isSuccessful()) {
                                                ArrayList<Food> result = new ArrayList<>(response.body());
                                                if (result != null && !result.isEmpty()) {
                                                        GlobalVariable.listFood.addAll(result);
                                                }
                                        }
                                }

                                @Override
                                public void onFailure(Call<ArrayList<Food>> call, Throwable t) {
                                        Toast.makeText(LoginActivity.this, "Failed to fetch foods", Toast.LENGTH_SHORT).show();
                                }
                        });
        }

}

package com.example.btlapi.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.btlapi.Domain.Food;
import com.example.btlapi.Domain.OrderItem;
import com.example.btlapi.Domain.User;
import com.example.btlapi.GlobalVariable;
import com.example.btlapi.Interface.FoodInterface;
import com.example.btlapi.OrderItemManager;
import com.example.btlapi.Utils.utils;
import com.example.btlapi.Utils.utils.*;
import com.example.btlapi.Interface.UserInterface;
import com.example.btlapi.R;
import com.example.btlapi.databinding.ActivitySignupBinding;

import java.util.ArrayList;
import java.util.List;

import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {
    ActivitySignupBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_signup);
        setContentView(binding.getRoot());

        setVariable();
    }
    //binding.backBtn.setOnClickListener(v -> finish());
    private void setVariable() {
        binding.textViewLogin.setOnClickListener(v -> finish());
        binding.signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = binding.userEdt.getText().toString();
                String password = binding.passEdt.getText().toString();
                String name = binding.nameEdt.getText().toString();
                String address = binding.AddressEdt.getText().toString();
                if(password.length()<6){
                    Toast.makeText(SignupActivity.this,"your password must be 6 character",Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    UserInterface userInterface;
                    userInterface = utils.getUserService();
                    User user = new User(0,name,password,phone,address,"");
                    userInterface.insertUser(user).enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if(response.isSuccessful()){
                                GlobalVariable.islogin = true;
                                GlobalVariable.userId = user.getId();
                                GlobalVariable.userName = user.getNames();
                                GlobalVariable.phone = user.getMobile();
                                GlobalVariable.email = user.getAddresss();
                                GlobalVariable.imagePath=user.getImagePath();
                                GlobalVariable.password=user.getPasswords();
                                GlobalVariable.address=user.getAddresss();
                                initListFoods();
                                if (!OrderItemManager.findOrderUser(SignupActivity.this,GlobalVariable.userId)){
                                    List<OrderItem> item1 = new ArrayList<>();
                                    OrderItemManager.saveOrderItems(SignupActivity.this,GlobalVariable.userId,item1);
                                }
                                startActivity(new Intent(SignupActivity.this,MainActivity.class));
                                Toast.makeText(SignupActivity.this,"Đăng kí Thanh Cong",Toast.LENGTH_SHORT).show();
                            }
                            else
                                Toast.makeText(SignupActivity.this,"Đăng Kí thất bại",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Toast.makeText(SignupActivity.this,"Đăng Kí thất bại",Toast.LENGTH_SHORT).show();
                        }
                    });
                }



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

                    }
                });
    }
}
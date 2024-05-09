package com.example.btlapi.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.btlapi.Adapter.ListFoodAdapter;
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
import com.example.btlapi.databinding.ActivityIntroBinding;
import com.example.btlapi.databinding.ActivityLoginBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
        ActivityLoginBinding binding;
        EditText moblile,pass;
        AppCompatButton Login;
        TextView SignUp;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                binding=ActivityLoginBinding.inflate(getLayoutInflater());

                setContentView(binding.getRoot());

                setVariable();

        }
        private void setVariable() {

                moblile = (EditText) findViewById(R.id.userEdt);
                pass = (EditText) findViewById(R.id.passEdt);
                Login =(AppCompatButton) findViewById(R.id.loginBtn);
                SignUp = (TextView) findViewById(R.id.textViewSignUp);
                binding.loginBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                String mobile = moblile.getText().toString();
                                String password = pass.getText().toString();

                                if (password.isEmpty())  Toast.makeText(LoginActivity.this, "Không được để trống", Toast.LENGTH_SHORT).show();
                                        // Gọi phương thức getUser khi activity được tạo
                                else
                                {
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
                UserInterface UserInterface;
                // Khởi tạo đối tượng userInterface
                UserInterface = utils.getUserService();
                // Gọi phương thức getUser từ userInterface
                UserInterface.getUser(mobile, password).enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                                if (response.isSuccessful()) {
                                        User user = response.body(); // Lấy người dùng từ phản hồi
                                        if (user != null) {
                                                GlobalVariable.islogin=true;
                                                GlobalVariable.userName=user.getNames();
                                                GlobalVariable.phone=user.getMobile();
                                                GlobalVariable.email=user.getAddresss();
                                                initOrder(user.getId());
                                                // Hiển thị thông tin người dùng
                                                Toast.makeText(LoginActivity.this, user.getNames(), Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                        } else {
                                                // Không tìm thấy người dùng
                                                Toast.makeText(LoginActivity.this, "Không tìm thấy người dùng", Toast.LENGTH_SHORT).show();
                                        }

                                } else {
                                        // Xử lý trường hợp không thành công
                                        Toast.makeText(LoginActivity.this, "Có lỗi xảy ra khi gọi API", Toast.LENGTH_SHORT).show();
                                }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                                // Xử lý lỗi khi gọi API
                                System.out.println(t.getMessage());
                                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                });
        }
        private void initOrder( int userId){
                OrderInterface orderInterface;
                orderInterface=utils.getOrderService();
                orderInterface.getAllOrdersByUserId(userId,"Processing").enqueue(new Callback<ArrayList<Order>>() {
                        @Override
                        public void onResponse(Call<ArrayList<Order>> call, Response<ArrayList<Order>> response) {
                                if (response.isSuccessful()) {
                                        ArrayList<Order> result = response.body();
                                        if (result != null && !result.isEmpty()) {
                                                GlobalVariable.listOrder.addAll(result);
                                                initOrderItem();
                                        }
                                }
                        }

                        @Override
                        public void onFailure(Call<ArrayList<Order>> call, Throwable t) {
                               // Toast.makeText(LoginActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                        }

                });

        }
        private void initOrderItem() {
                OrderItemInterface orderItemInterface;
                orderItemInterface=utils.getOrderItemService();
                orderItemInterface.getAllOrderItems(GlobalVariable.listOrder.get(0).getId()).enqueue(new Callback<ArrayList<OrderItem>>() {
                        @Override
                        public void onResponse(Call<ArrayList<OrderItem>> call, Response<ArrayList<OrderItem>> response) {
                                if (response.isSuccessful()) {
                                        ArrayList<OrderItem> result = response.body();
                                        if (result != null && !result.isEmpty()) {
                                                GlobalVariable.listOrderItem.addAll(result);
                                                initListFoods();
                                        }
                                }
                        }
                        @Override
                        public void onFailure(Call<ArrayList<OrderItem>> call, Throwable t) {
                                //Toast.makeText(LoginActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
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
                                                ArrayList<Food> result = response.body();
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
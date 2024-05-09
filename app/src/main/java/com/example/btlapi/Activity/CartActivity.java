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
import com.example.btlapi.Domain.Food;
import com.example.btlapi.Domain.User;
import com.example.btlapi.GlobalVariable;
import com.example.btlapi.Interface.FoodInterface;
import com.example.btlapi.Interface.OrderItemInterface;
import com.example.btlapi.Interface.UserInterface;
import com.example.btlapi.OrderItemManager;
import com.example.btlapi.R;
import com.example.btlapi.Domain.Order;
import com.example.btlapi.Domain.OrderItem;
import com.example.btlapi.Utils.utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.example.btlapi.OrderItemQueue;
import com.example.btlapi.databinding.ActivityCartBinding;
import com.example.btlapi.databinding.ActivityMainBinding;

import androidx.recyclerview.widget.LinearLayoutManager;
public class CartActivity extends AppCompatActivity {
    private ActivityCartBinding binding;
    private Integer userId;

    ArrayList<Order> listOrder=new ArrayList<>();
    Food items =new Food();
    ArrayList<OrderItem> listOrderItem = new ArrayList<OrderItem>();
    OrderItem item,item2,item3;
    RecyclerView cardView;
    AppCompatButton placeOrder;
    private OrderItemQueue orderItemQueue =new OrderItemQueue();
    int orderID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        listOrder= GlobalVariable.listOrder;
        listOrderItem=GlobalVariable.listOrderItem;
        cardView.setLayoutManager(new LinearLayoutManager(CartActivity.this));
        CartAdapter adapter = new CartAdapter(CartActivity.this, listOrderItem,GlobalVariable.listFood);
        cardView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        getIntentExtra();
        setVariable();
        InitWiget();
    }
    private void getIntentExtra() {
        Intent intent = getIntent();
        userId =  intent.getIntExtra("userid",-1);
    }
    private void setVariable(){
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void insertOrderItem(OrderItem item){
        OrderItemInterface orderItemInterface;
        orderItemInterface = utils.getOrderItemService();
        OrderItem user = new OrderItem(item.getId(),item.getOrderId(),item.getProductId(),item.getQuantity(),item.getPrice());
        System.out.println(item.getId()+" "+item.getOrderId()+" "+item.getProductId()+" "+item.getQuantity()+" "+item.getPrice());
        orderItemInterface.insertOrderItem(user).enqueue(new Callback<OrderItem>() {
            @Override
            public void onResponse(Call<OrderItem> call, Response<OrderItem> response) {
                if(response.isSuccessful()){
                    Toast.makeText(CartActivity.this,"Thanh Cong",Toast.LENGTH_SHORT);
                }
                else
                    Toast.makeText(CartActivity.this,"Hello",Toast.LENGTH_SHORT);
            }

            @Override
            public void onFailure(Call<OrderItem> call, Throwable t) {
                Toast.makeText(CartActivity.this,"Loi",Toast.LENGTH_SHORT);
            }
        });
    }
    public Food getfoodbyid(int id) {
        FoodInterface foodInterface;
        final Food[] result = new Food[1];
        foodInterface = utils.getFoodService();
        // Gọi phương thức getUser từ userInterface
        foodInterface.getDetailFoodById(id).enqueue(new Callback<Food>() {
            @Override
            public void onResponse(Call<Food> call, Response<Food> response) {
                if (response.isSuccessful()) {
                    Food food = response.body(); // Lấy người dùng từ phản hồi
                    if (food != null) {
                        // Hiển thị thông tin người dùng
//                        Toast.makeText(CartActivity.this, food.getTitle(), Toast.LENGTH_SHORT).show();
                        items = food;
                        Toast.makeText(CartActivity.this, items.getTitle(), Toast.LENGTH_SHORT).show();

                    }

                }
            }

            @Override
            public void onFailure(Call<Food> call, Throwable t) {
                // Xử lý lỗi khi gọi API
                System.out.println(t.getMessage());
//                Toast.makeText(CartActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return result[0];
    }
    private void InitWiget() {
        cardView =(RecyclerView) findViewById(R.id.cardView);
        placeOrder = (AppCompatButton) findViewById(R.id.btnPlaceOrder);
    }


}


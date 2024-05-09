package com.example.btlapi.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.btlapi.Adapter.ListFoodAdapter;
import com.example.btlapi.Domain.Food;
import com.example.btlapi.Interface.FoodInterface;
import com.example.btlapi.R;
import com.example.btlapi.Utils.utils;
import com.example.btlapi.databinding.ActivityListFoodsBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListFoodsActivity extends AppCompatActivity {
    private ActivityListFoodsBinding binding;
    private RecyclerView.Adapter adapterListFood;
    private Integer categoryId;
    private String categoryName;
    private String searchText;
    private Integer location;
    private Integer time;
    private Integer price;
    private Integer bestFood;
    private Integer star;
    private int locationChange, timeChange, priceChange, categoryChange, starChange;
    private String searchTextChange;

    private ArrayList<Food> listFoods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListFoodsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        listFoods = new ArrayList<>();
        location=-1;
        time=-1;
        price=-1;
        getIntentExtra();
        initListFoods();
    }

    private void initListFoods() {
        binding.progressBar.setVisibility(View.VISIBLE);
        FoodInterface foodInterface = utils.getFoodService();
        foodInterface.getFood(null, null, null, null, null, categoryChange==-1?null:categoryChange, searchTextChange)
                .enqueue(new Callback<ArrayList<Food>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Food>> call, Response<ArrayList<Food>> response) {
                        if (response.isSuccessful()) {
                            ArrayList<Food> result = response.body();
                            if (result != null && !result.isEmpty()) {
                                listFoods.addAll(result);
                                binding.foodListView.setLayoutManager(new GridLayoutManager(ListFoodsActivity.this, 2));
                                adapterListFood = new ListFoodAdapter(listFoods);
                                binding.foodListView.setAdapter(adapterListFood);
                                binding.progressBar.setVisibility(View.GONE);
                            } else {
                                Toast.makeText(ListFoodsActivity.this, "No data available", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(ListFoodsActivity.this, "Request failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Food>> call, Throwable t) {
                        Toast.makeText(ListFoodsActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getIntentExtra() {
        categoryId = getIntent().getIntExtra("CategoryId", -1);
        categoryName = getIntent().getStringExtra("CategoryName");
        searchText = getIntent().getStringExtra("searchText");
        searchTextChange = searchText;
        categoryChange = categoryId;
        binding.titleTxt.setText(categoryName);
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

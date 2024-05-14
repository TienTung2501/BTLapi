package com.example.btlapi.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.btlapi.Adapter.BestFoodAdapter;
import com.example.btlapi.Adapter.CategoryAdapter;
import com.example.btlapi.Domain.Category;
import com.example.btlapi.Domain.Food;
import com.example.btlapi.Domain.Location;
import com.example.btlapi.Domain.Price;
import com.example.btlapi.Domain.Star;
import com.example.btlapi.Domain.Time;
import com.example.btlapi.Interface.CategoryInterface;
import com.example.btlapi.Interface.FoodInterface;
import com.example.btlapi.Interface.LocationInterface;
import com.example.btlapi.Interface.PriceInterface;
import com.example.btlapi.Interface.TimeInterface;
import com.example.btlapi.R;
import com.example.btlapi.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import com.example.btlapi.Utils.utils;
import com.example.btlapi.GlobalVariable;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ArrayList<Location> locations;
    private ArrayList<Time> times;
    private ArrayList<Price> prices;
    private ArrayList<Star> stars;
    private ArrayList<Food> bestFoods;
    private ArrayList<Category> categories;
    private int locationChange, timeChange, priceChange;
    private String searchtext;
    public  boolean isFetChing=false;
    private ExecutorService executorService = Executors.newCachedThreadPool();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        locations = new ArrayList<>();
        locations.add(new Location(-1, "ALL Location"));
        times = new ArrayList<>();
        times.add(new Time(-1, "ALL Time"));
        prices = new ArrayList<>();
        prices.add(new Price(-1, "ALL Price"));
        stars = new ArrayList<>();
        stars.add(new Star(-1, "ALL Star"));
        categories = new ArrayList<>();
        bestFoods = new ArrayList<>();
        initData();
        binding.userNameTxt.setText(GlobalVariable.userName);
        evenListener();

    }
    private void initData() {
        initLocation();
        initTime();
        initPrice();
        initBestFood();
        initCategory();
    }

    private void evenListener() {
        binding.locationSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                locationChange = locations.get(position).getId();
                    reloadFood();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.timeSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                timeChange = times.get(position).getId();
                    reloadFood();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.priceSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                priceChange = prices.get(position).getId();
                    reloadFood();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchtext = binding.editTextText.getText().toString();
                Intent intent = new Intent(MainActivity.this, ListFoodsActivity.class);
                intent.putExtra("searchText", searchtext);
                startActivity(intent);
            }
        });
        binding.cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int userId=GlobalVariable.userId;
                Intent intent=new Intent(MainActivity.this,CartActivity.class);
                startActivity(intent);
            }
        });
        binding.viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ListFoodsActivity.class);
                startActivity(intent);
            }
        });
        binding.userNameTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,AccountActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initLocation() {
        LocationInterface location;
        location = utils.getLocationService();
        location.getAllLocations().enqueue(new Callback<ArrayList<Location>>() {
            @Override
            public void onResponse(Call<ArrayList<Location>> call, Response<ArrayList<Location>> response) {
                if (response.isSuccessful()) {
                    locations.addAll(response.body());
                    if (locations != null && !locations.isEmpty()) {
                        ArrayAdapter<Location> locationAdapter = new ArrayAdapter<>(MainActivity.this, R.layout.sp_item, locations);
                        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        binding.locationSp.setAdapter(locationAdapter);
                    } else {
                        Toast.makeText(MainActivity.this, "No response for locations", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "No response for locations", Toast.LENGTH_SHORT).show();
                }
                //callback.run(); // Gọi callback khi hoàn thành
            }

            @Override
            public void onFailure(Call<ArrayList<Location>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed to fetch locations", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initTime() {
        TimeInterface time;
        time = utils.getTimeService();
        time.getAllTimes().enqueue(new Callback<ArrayList<Time>>() {
            @Override
            public void onResponse(Call<ArrayList<Time>> call, Response<ArrayList<Time>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Time> result = response.body();
                    if (result != null && !result.isEmpty()) {
                        times.addAll(result);
                        ArrayAdapter<Time> timeAdapter = new ArrayAdapter<>(MainActivity.this, R.layout.sp_item, times);
                        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        binding.timeSp.setAdapter(timeAdapter);
                    } else {
                        Toast.makeText(MainActivity.this, "No response for times", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "No response for times", Toast.LENGTH_SHORT).show();
                }
                //callback.run(); // Gọi callback khi hoàn thành
            }
            @Override
            public void onFailure(Call<ArrayList<Time>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed to fetch times", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initPrice() {
        PriceInterface price;
        price = utils.getPriceService();
        price.getAllPrices().enqueue(new Callback<ArrayList<Price>>() {
            @Override
            public void onResponse(Call<ArrayList<Price>> call, Response<ArrayList<Price>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Price> result = response.body();
                    if (result != null && !result.isEmpty()) {
                        prices.addAll(result);
                        ArrayAdapter<Price> priceAdapter = new ArrayAdapter<>(MainActivity.this, R.layout.sp_item, prices);
                        priceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        binding.priceSp.setAdapter(priceAdapter);
                        // Continue to fetch prices for the next time
                    } else {
                        Toast.makeText(MainActivity.this, "No response for prices", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "No response for prices", Toast.LENGTH_SHORT).show();
                }
                //callback.run(); // Gọi callback khi hoàn thành
            }

            @Override
            public void onFailure(Call<ArrayList<Price>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed to fetch prices", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initBestFood() {
        binding.progressBarBestFood.setVisibility(View.VISIBLE);
        FoodInterface foodInterface;
        foodInterface = utils.getFoodService();
        // Gửi yêu cầu với dữ liệu JSON
        foodInterface.getFood(1, null, null, null, null, null, null)
                .enqueue(new Callback<ArrayList<Food>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Food>> call, Response<ArrayList<Food>> response) {
                        if (response.isSuccessful()) {
                            ArrayList<Food> result = response.body();
                            if (result != null && !result.isEmpty()) {
                                bestFoods.addAll(result);
                                binding.bestFoodView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
                                RecyclerView.Adapter adapterBestFood = new BestFoodAdapter(bestFoods);
                                adapterBestFood.notifyDataSetChanged();
                                binding.bestFoodView.setAdapter(adapterBestFood);
                                binding.progressBarBestFood.setVisibility(View.GONE);
                            } else {
                                Toast.makeText(MainActivity.this, "No response for best food", Toast.LENGTH_SHORT).show();
                                binding.progressBarBestFood.setVisibility(View.GONE);
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "No response for best food", Toast.LENGTH_SHORT).show();
                            binding.progressBarBestFood.setVisibility(View.GONE);
                        }
                       // callback.run(); // Gọi callback khi hoàn thành
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Food>> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Failed to fetch best food", Toast.LENGTH_SHORT).show();
                        binding.progressBarBestFood.setVisibility(View.GONE);
                    }
                });
    }

    private void initCategory() {
        binding.progressBarCategory.setVisibility(View.VISIBLE);
        CategoryInterface categoryInterface;
        categoryInterface = utils.getCategoryService();
        // Gửi yêu cầu với dữ liệu JSON
        categoryInterface.getAllCategories()
                .enqueue(new Callback<ArrayList<Category>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Category>> call, Response<ArrayList<Category>> response) {
                        if (response.isSuccessful()) {
                            ArrayList<Category> result = response.body();
                            if (result != null && !result.isEmpty()) {
                                categories.addAll(result);
                                binding.categoryView.setLayoutManager(new GridLayoutManager(MainActivity.this, 4));
                                RecyclerView.Adapter adapterCategory = new CategoryAdapter(categories);
                                binding.categoryView.setAdapter(adapterCategory);
                                binding.progressBarCategory.setVisibility(View.GONE);
                            } else {
                                Toast.makeText(MainActivity.this, "No response for best food", Toast.LENGTH_SHORT).show();
                                binding.progressBarCategory.setVisibility(View.GONE);
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "No response for best food", Toast.LENGTH_SHORT).show();
                            binding.progressBarCategory.setVisibility(View.GONE);
                        }
                        //callback.run(); // Gọi callback khi hoàn thành

                    }

                    @Override
                    public void onFailure(Call<ArrayList<Category>> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Failed to fetch best food", Toast.LENGTH_SHORT).show();
                        binding.progressBarCategory.setVisibility(View.GONE);
                    }
                });

    }

    private void reloadFood() {
        FoodInterface foodInterface;
        foodInterface = utils.getFoodService();
        foodInterface.getFood(1, locationChange == -1 ? null : locationChange, timeChange == -1 ? null : timeChange, priceChange == -1 ? null : priceChange, null, null, null)
                .enqueue(new Callback<ArrayList<Food>>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(Call<ArrayList<Food>> call, Response<ArrayList<Food>> response) {
                        if (response.isSuccessful()) {
                            ArrayList<Food> result = response.body();
                            if (result != null && !result.isEmpty()) {
                                bestFoods.clear(); // Xóa dữ liệu cũ
                                bestFoods.addAll(result); // Thêm dữ liệu mới từ phản hồi
                                binding.bestFoodView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
                                RecyclerView.Adapter adapterBestFood = new BestFoodAdapter(bestFoods);
                                adapterBestFood.notifyDataSetChanged();
                                binding.bestFoodView.setAdapter(adapterBestFood);

                            } else {
                                Toast.makeText(MainActivity.this, "No response for best food", Toast.LENGTH_SHORT).show();
                                binding.progressBarBestFood.setVisibility(View.GONE);
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "No response for best food", Toast.LENGTH_SHORT).show();
                            binding.progressBarBestFood.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Food>> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Failed to fetch best food", Toast.LENGTH_SHORT).show();
                        binding.progressBarBestFood.setVisibility(View.GONE);
                    }
                });
    }
}

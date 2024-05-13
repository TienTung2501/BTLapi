package com.example.btlapi.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.btlapi.Adapter.BestFoodAdapter;
import com.example.btlapi.Adapter.CategoryAdapter;
import com.example.btlapi.Adapter.ListFoodAdapter;
import com.example.btlapi.Domain.Category;
import com.example.btlapi.Domain.CategorySp;
import com.example.btlapi.Domain.Food;
import com.example.btlapi.Domain.Location;
import com.example.btlapi.Domain.Price;
import com.example.btlapi.Domain.Star;
import com.example.btlapi.Domain.Time;
import com.example.btlapi.GlobalVariable;
import com.example.btlapi.Interface.CategoryInterface;
import com.example.btlapi.Interface.FoodInterface;
import com.example.btlapi.Interface.LocationInterface;
import com.example.btlapi.Interface.PriceInterface;
import com.example.btlapi.Interface.StarInterface;
import com.example.btlapi.Interface.TimeInterface;
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
    private Integer category;
    private Integer star;
    private int locationChange, timeChange, priceChange, categoryChange, starChange;
    private String searchTextChange;
    private ArrayList<Location> locations;
    private ArrayList<Time> times;
    private ArrayList<Price> prices;
    private ArrayList<Star> stars;
    private ArrayList<CategorySp> categories;

    private ArrayList<Food> listFoods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListFoodsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        location=-1;
        time=-1;
        price=-1;
        category=-1;
        star=-1;
        listFoods = new ArrayList<>();
        locations = new ArrayList<>();
        times = new ArrayList<>();
        prices = new ArrayList<>();
        stars = new ArrayList<>();
        categories = new ArrayList<>();

        locations.add(new Location(-1, "ALL Location"));
        times.add(new Time(-1, "ALL Time"));
        prices.add(new Price(-1, "ALL Price"));
        stars.add(new Star(-1, "ALL Star"));
        categories.add(new CategorySp(-1,"ALL Category"));

        getIntentExtra();
        initListFoods();
        initData();
        evenListener();
    }

    private void initData() {
        initLocation();
        initPrice();
        initTime();
        initStar();
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
        binding.categorySp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoryChange = categories.get(position).getId();
                reloadFood();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.starSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                starChange = stars.get(position).getId();
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
                searchTextChange = binding.editTextText.getText().toString();
                reloadFood();
            }
        });
        binding.cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int userId= GlobalVariable.userId;
                Intent intent=new Intent(ListFoodsActivity.this,CartActivity.class);
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
                        ArrayAdapter<Location> locationAdapter = new ArrayAdapter<>(ListFoodsActivity.this, R.layout.sp_item, locations);
                        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        binding.locationSp.setAdapter(locationAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Location>> call, Throwable t) {
                Toast.makeText(ListFoodsActivity.this, "Failed to fetch locations", Toast.LENGTH_SHORT).show();
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
                        ArrayAdapter<Time> timeAdapter = new ArrayAdapter<>(ListFoodsActivity.this, R.layout.sp_item, times);
                        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        binding.timeSp.setAdapter(timeAdapter);
                    }
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Time>> call, Throwable t) {
                Toast.makeText(ListFoodsActivity.this, "Failed to fetch times", Toast.LENGTH_SHORT).show();
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
                        ArrayAdapter<Price> priceAdapter = new ArrayAdapter<>(ListFoodsActivity.this, R.layout.sp_item, prices);
                        priceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        binding.priceSp.setAdapter(priceAdapter);
                        // Continue to fetch prices for the next time
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Price>> call, Throwable t) {
                Toast.makeText(ListFoodsActivity.this, "Failed to fetch prices", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void initCategory() {
        CategoryInterface categoryInterface;
        categoryInterface = utils.getCategoryService();
        categoryInterface.getAllCategories()
                .enqueue(new Callback<ArrayList<Category>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Category>> call, Response<ArrayList<Category>> response) {
                        if (response.isSuccessful()) {
                            ArrayList<Category> result = response.body();
                            if (result != null && !result.isEmpty()) {
                                for(Category c:result){
                                    categories.add(new CategorySp(c.getId(),c.getName()));
                                }
                                ArrayAdapter<CategorySp> adapterCategory = new ArrayAdapter<>(ListFoodsActivity.this,R.layout.sp_item,categories);
                                adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                adapterCategory.notifyDataSetChanged();
                                binding.categorySp.setAdapter(adapterCategory);

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Category>> call, Throwable t) {
                        Toast.makeText(ListFoodsActivity.this, "Failed to fetch best food", Toast.LENGTH_SHORT).show();
                    }
                });

    }
    private void initStar() {
        StarInterface starInterface;
        starInterface = utils.getStarService();
        starInterface.getAllStars().enqueue(new Callback<ArrayList<Star>>() {
            @Override
            public void onResponse(Call<ArrayList<Star>> call, Response<ArrayList<Star>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Star> result = response.body();
                    if (result != null && !result.isEmpty()) {
                        stars.addAll(result);
                        ArrayAdapter<Star> adapterStar = new ArrayAdapter<>(ListFoodsActivity.this,R.layout.sp_item,stars);
                        adapterStar.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        adapterStar.notifyDataSetChanged();
                        binding.starSp.setAdapter(adapterStar);

                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Star>> call, Throwable t) {
                Toast.makeText(ListFoodsActivity.this, "Failed to fetch locations", Toast.LENGTH_SHORT).show();
            }
        });
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
                            }
                            else {
                                // Nếu danh sách rỗng, hiển thị thông báo
                                listFoods.clear();
                                adapterListFood.notifyDataSetChanged();
                                Toast.makeText(ListFoodsActivity.this, "This food is over, please choose other filter", Toast.LENGTH_SHORT).show();
                                binding.progressBar.setVisibility(View.GONE);
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
    private void reloadFood() {
        FoodInterface foodInterface;
        foodInterface = utils.getFoodService();
        foodInterface.getFood(null, locationChange == -1 ? null : locationChange, timeChange == -1 ? null : timeChange, priceChange == -1 ? null : priceChange, starChange == -1 ? null : starChange, categoryChange == -1 ? null : categoryChange, searchTextChange == "" ? null : searchTextChange)
                .enqueue(new Callback<ArrayList<Food>>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(Call<ArrayList<Food>> call, Response<ArrayList<Food>> response) {
                        if (response.isSuccessful()) {
                            ArrayList<Food> result = response.body();
                            if (result != null && !result.isEmpty()) {
                                listFoods.clear(); // Clear old data
                                listFoods.addAll(result); // Add new data from the response
                                if (adapterListFood == null) {
                                    // Initialize adapterListFood only when it's null
                                    adapterListFood = new ListFoodAdapter(listFoods);
                                    binding.foodListView.setAdapter(adapterListFood);
                                } else {
                                    // Update existing adapter
                                    adapterListFood.notifyDataSetChanged();
                                }
                                binding.progressBar.setVisibility(View.GONE);
                            } else {
                                // Handle case when there are no results
                                listFoods.clear();
                                if (adapterListFood != null) {
                                    adapterListFood.notifyDataSetChanged();
                                }
                                Toast.makeText(ListFoodsActivity.this, "This food is over, please choose other filter", Toast.LENGTH_SHORT).show();
                                binding.progressBar.setVisibility(View.GONE);
                            }
                        } else {
                            Toast.makeText(ListFoodsActivity.this, "No response for best food", Toast.LENGTH_SHORT).show();
                            binding.progressBar.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Food>> call, Throwable t) {
                        Toast.makeText(ListFoodsActivity.this, "Failed to fetch best food", Toast.LENGTH_SHORT).show();
                        binding.progressBar.setVisibility(View.GONE);
                    }
                });

    }

    private void getIntentExtra() {
        categoryId = getIntent().getIntExtra("CategoryId", -1);
        categoryName = getIntent().getStringExtra("CategoryName");
        searchText = getIntent().getStringExtra("searchText");
        searchTextChange = searchText;
        categoryChange = categoryId;

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

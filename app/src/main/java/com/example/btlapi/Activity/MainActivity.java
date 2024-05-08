package com.example.btlapi.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.example.btlapi.Domain.Location;
import com.example.btlapi.Domain.Price;
import com.example.btlapi.Domain.Time;
import com.example.btlapi.Interface.LocationInterface;
import com.example.btlapi.Interface.PriceInterface;
import com.example.btlapi.Interface.TimeInterface;
import com.example.btlapi.R;
import com.example.btlapi.databinding.ActivityIntroBinding;
import com.example.btlapi.databinding.ActivityMainBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.btlapi.Utils.utils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ArrayList<Location> locations;
    private ArrayList<Time> times;
    private ArrayList<Price> prices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        locations = new ArrayList<>();
        times = new ArrayList<>();
        prices = new ArrayList<>();
        initLocation();
    }

    private void initLocation() {
        LocationInterface location;
        location = utils.getLocationService();
        location.getAllLocations().enqueue(new Callback<ArrayList<Location>>() {
            @Override
            public void onResponse(Call<ArrayList<Location>> call, Response<ArrayList<Location>> response) {
                if (response.isSuccessful()) {
                    locations = response.body();
                    if (locations != null && !locations.isEmpty()) {
                        initTime(0);
                    } else {
                        Toast.makeText(MainActivity.this, "No response for locations", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "No response for locations", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Location>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed to fetch locations", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initTime(final int index) {
        if (index < locations.size()) {
            TimeInterface time;
            time = utils.getTimeService();
            time.getAllTimes().enqueue(new Callback<ArrayList<Time>>() {
                @Override
                public void onResponse(Call<ArrayList<Time>> call, Response<ArrayList<Time>> response) {
                    if (response.isSuccessful()) {
                        ArrayList<Time> result = response.body();
                        if (result != null && !result.isEmpty()) {
                            times.addAll(result);
                            initPrice(2);
                        } else {
                            Toast.makeText(MainActivity.this, "No response for times", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "No response for times", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<Time>> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Failed to fetch times", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void initPrice(final int index) {
        if (index < times.size()) {
            PriceInterface price;
            price = utils.getPriceService();
            price.getAllPrices().enqueue(new Callback<ArrayList<Price>>() {
                @Override
                public void onResponse(Call<ArrayList<Price>> call, Response<ArrayList<Price>> response) {
                    if (response.isSuccessful()) {
                        ArrayList<Price> result = response.body();
                        if (result != null && !result.isEmpty()) {
                            prices.addAll(result);
                            // Continue to fetch prices for the next time
                            initPrice(index+1);
                        } else {
                            Toast.makeText(MainActivity.this, "No response for prices", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "No response for prices", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<Price>> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Failed to fetch prices", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            // After fetching all prices, set up adapters for spinners
            setUpAdapters();
        }
    }

    private void setUpAdapters() {
        // Set up adapter for location spinner
        ArrayAdapter<Location> locationAdapter = new ArrayAdapter<>(MainActivity.this, R.layout.sp_item, locations);
        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.locationSp.setAdapter(locationAdapter);

        // Set up adapter for time spinner
        ArrayAdapter<Time> timeAdapter = new ArrayAdapter<>(MainActivity.this, R.layout.sp_item, times);
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.timeSp.setAdapter(timeAdapter);

        // Set up adapter for price spinner
        ArrayAdapter<Price> priceAdapter = new ArrayAdapter<>(MainActivity.this, R.layout.sp_item, prices);
        priceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.priceSp.setAdapter(priceAdapter);
    }
}

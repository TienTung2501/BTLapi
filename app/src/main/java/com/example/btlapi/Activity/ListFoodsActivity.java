package com.example.btlapi.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.btlapi.R;
import com.example.btlapi.databinding.ActivityListFoodsBinding;

public class ListFoodsActivity extends AppCompatActivity {
    ActivityListFoodsBinding binding;
    private RecyclerView.Adapter adapterListFood;
    private int categoryId;
    private String categoryName;
    private String searchText;
    private boolean isSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_foods);
        binding=ActivityListFoodsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getIntenExtra();
    }

    private void getIntenExtra() {
        categoryId=getIntent().getIntExtra("CategoryId",0);
        categoryName=getIntent().getStringExtra("Category");
        searchText=getIntent().getStringExtra("text");
        isSearch=getIntent().getBooleanExtra("isSearch",false);
    }
}
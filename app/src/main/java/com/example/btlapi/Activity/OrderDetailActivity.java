package com.example.btlapi.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.btlapi.R;
import com.example.btlapi.databinding.ActivityOrderDetailBinding;

public class OrderDetailActivity extends AppCompatActivity {
    ActivityOrderDetailBinding binding;
    RecyclerView.Adapter adapterOrderDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityOrderDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getIntentExtra();
    }

    private void getIntentExtra() {
        int orderId=getIntent().getIntExtra("orderid",-1);
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
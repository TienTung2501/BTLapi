package com.example.btlapi.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.btlapi.R;
import com.example.btlapi.databinding.ActivityOrderBinding;
import com.example.btlapi.databinding.ActivityOrderDetailBinding;

public class OrderActivity extends AppCompatActivity {
    ActivityOrderBinding binding;
    RecyclerView.Adapter    adapterListOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getIntentExtra();
    }

    private void getIntentExtra() {
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
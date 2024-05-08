package com.example.btlapi.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.btlapi.R;
import com.example.btlapi.databinding.ActivityDetailBinding;
import com.example.btlapi.databinding.ActivityIntroBinding;

import java.security.PrivateKey;

public class DetailActivity extends AppCompatActivity {
    ActivityDetailBinding binding;
   // private Food object;
    private int num=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        binding=ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //getIntentExtra();
    }

}
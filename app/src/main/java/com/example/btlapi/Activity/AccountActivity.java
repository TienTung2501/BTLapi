package com.example.btlapi.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.btlapi.GlobalVariable;
import com.example.btlapi.R;
import com.example.btlapi.databinding.ActivityAccountBinding;

public class AccountActivity extends AppCompatActivity {
    ActivityAccountBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initComponent();
    }

    private void initComponent() {
        loadInfo();
        binding.logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AccountActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        binding.editProfileTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AccountActivity.this, ProfileDetailActivity.class);
                startActivity(intent);
            }
        });
        binding.viewOrderTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AccountActivity.this, OrderActivity.class);
                startActivity(intent);
            }
        });
        binding.viewCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AccountActivity.this, CartActivity.class);
                startActivity(intent);

            }
        });
        binding.viewProfileTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AccountActivity.this, ProfileDetailActivity.class);
                startActivity(intent);
            }
        });
    }
    private int getImageResource(String imageName) {
        return getResources().getIdentifier(imageName, "drawable", getPackageName());
    }
    private void loadInfo(){
        binding.userNameTxt.setText(GlobalVariable.userName);
        int imageResourceId = getImageResource(GlobalVariable.imagePath);

        if (imageResourceId != 0) {
            Glide.with(AccountActivity.this)
                    .load(imageResourceId)
                    .transform(new CenterCrop(), new RoundedCorners(30))
                    .into(binding.picAvatar);
        } else {
            binding.picAvatar.setImageResource(R.drawable.google);
        }
    }
}
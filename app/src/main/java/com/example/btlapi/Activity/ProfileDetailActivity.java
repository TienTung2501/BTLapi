package com.example.btlapi.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.btlapi.Domain.User;
import com.example.btlapi.GlobalVariable;
import com.example.btlapi.Interface.UserInterface;
import com.example.btlapi.R;
import com.example.btlapi.Utils.utils;
import com.example.btlapi.databinding.ActivityProfileDetailBinding;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class ProfileDetailActivity extends AppCompatActivity {
    private Uri selectedImageUri;

    public static boolean isChoseUpdate = false;
    private int userId;
    private String userName, phone, addr, passWord, imagePath;
    ActivityProfileDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getIntentExtra();
        loadInfo();
        eventListener();
    }

    private void eventListener() {

        binding.saveBtn.setOnClickListener(v -> {
            String name = binding.edittextUserName.getText().toString();
            String phonee = binding.edittextPhoneNumber.getText().toString();
            String addrr = binding.edittextAddress.getText().toString();
            String passwordd = binding.edittextPassword.getText().toString();

                if(name!=userName||phonee!=phone||addrr!=addr||passwordd!=passWord){

                    updateUser(name, phonee, addrr, passwordd);
                    getUserFromApi(phonee,passwordd);

                }

        });
        binding.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 2. Cho phép nhập vào các EditText và click vào TextView "UploadAvatar" khi nhấn nút chỉnh sửa
                binding.edittextUserName.setEnabled(true);
                binding.edittextPhoneNumber.setEnabled(true);
                binding.edittextAddress.setEnabled(true);
                binding.edittextPassword.setEnabled(true);

                // Hiển thị nút Save
                binding.saveBtn.setVisibility(View.VISIBLE);
            }
        });
    }
    private void getUserFromApi(String mobile, String password) {
        UserInterface userInterface = utils.getUserService();
        userInterface.getUser(mobile, password).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User result=response.body();
                    GlobalVariable.islogin = true;
                    GlobalVariable.userId = result.getId();
                    GlobalVariable.userName = result.getNames();
                    GlobalVariable.phone = result.getMobile();
                    GlobalVariable.email = result.getAddresss();
                    GlobalVariable.imagePath=result.getImagePath();
                    GlobalVariable.password=result.getPasswords();
                    GlobalVariable.address=result.getAddresss();
                    startActivity(new Intent(ProfileDetailActivity.this,AccountActivity.class));
                } else {
                    Toast.makeText(ProfileDetailActivity.this, "No data user respone", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(ProfileDetailActivity.this, "Fetch Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getIntentExtra() {
        binding.backBtn.setOnClickListener(v -> finish());
    }

    private void loadInfo() {
        int imageResourceId = getImageResource(GlobalVariable.imagePath);

        if (imageResourceId != 0) {
            Glide.with(ProfileDetailActivity.this)
                    .load(imageResourceId)
                    .transform(new CenterCrop(), new RoundedCorners(30))
                    .into(binding.picAvatar);
        } else {
            binding.picAvatar.setImageResource(R.drawable.google);
        }
        userId=GlobalVariable.userId;
        userName=GlobalVariable.userName;
        passWord=GlobalVariable.password;
        addr=GlobalVariable.address;
        phone=GlobalVariable.phone;
        binding.saveBtn.setVisibility(View.GONE);
        binding.userNameTxt.setText(GlobalVariable.userName);
        binding.edittextUserName.setText(GlobalVariable.userName);
        binding.edittextPhoneNumber.setText(GlobalVariable.phone);
        binding.edittextAddress.setText(GlobalVariable.address);
        binding.edittextPassword.setText(GlobalVariable.password);
        binding.edittextUserName.setEnabled(false);
        binding.edittextPhoneNumber.setEnabled(false);
        binding.edittextAddress.setEnabled(false);
        binding.edittextPassword.setEnabled(false);
    }

    private int getImageResource(String imageName) {
        return getResources().getIdentifier(imageName, "drawable", getPackageName());
    }

    private void updateUser(String username, String phone, String addr, String passWord) {
        User user = new User(userId, username, passWord, phone, addr,"avatar"+String.valueOf(userId));
        UserInterface userInterface;
        userInterface = utils.getUserService();
        Call<User> call = userInterface.updateUser(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ProfileDetailActivity.this, "Update Success", Toast.LENGTH_SHORT).show();
                    loadInfo();
                } else {
                    Toast.makeText(ProfileDetailActivity.this, "Update Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                Toast.makeText(ProfileDetailActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}

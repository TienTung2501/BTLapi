package com.example.btlapi.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.btlapi.Domain.User;
import com.example.btlapi.Utils.utils;
import com.example.btlapi.Utils.utils.*;
import com.example.btlapi.Interface.UserInterface;
import com.example.btlapi.R;
import com.example.btlapi.databinding.ActivitySignupBinding;

import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {
    ActivitySignupBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_signup);
        setContentView(binding.getRoot());

        setVariable();
    }
//binding.backBtn.setOnClickListener(v -> finish());
    private void setVariable() {
        binding.textViewLogin.setOnClickListener(v -> finish());
        binding.signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = binding.userEdt.getText().toString();
                String password = binding.passEdt.getText().toString();
                String name = binding.nameEdt.getText().toString();
                String address = binding.AddressEdt.getText().toString();
                if(password.length()<6){
                    Toast.makeText(SignupActivity.this,"your password must be 6 character",Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    UserInterface userInterface;
                    userInterface = utils.getUserService();
                    User user = new User(name,password,phone,address);
                    userInterface.insertUser(user).enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if(response.isSuccessful()){
                                Toast.makeText(SignupActivity.this,"Thanh Cong",Toast.LENGTH_SHORT);
                            }
                            else
                                Toast.makeText(SignupActivity.this,"Hello",Toast.LENGTH_SHORT);
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Toast.makeText(SignupActivity.this,"Loi",Toast.LENGTH_SHORT);
                        }
                    });
                }


            }
        });
    }
}
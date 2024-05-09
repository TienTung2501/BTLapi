package com.example.btlapi.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.btlapi.databinding.ActivityIntroBinding;

public class IntroActivity extends BaseActivity {
    ActivityIntroBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding= ActivityIntroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        super.onCreate(savedInstanceState);
        setVariable();
    }

    private void setVariable() {

        binding.LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(IntroActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });
        binding.SignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(IntroActivity.this,SignupActivity.class);
                startActivity(intent);
            }
        });
    }
}
package com.example.btlapi.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.example.btlapi.R;
import com.example.btlapi.databinding.ActivityIntroBinding;

public class IntroActivity extends BaseActivity {
    ActivityIntroBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding=ActivityIntroBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        setVariable();
    }

    private void setVariable() {
        binding.LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        binding.SignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
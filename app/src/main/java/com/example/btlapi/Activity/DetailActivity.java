package com.example.btlapi.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.btlapi.Domain.OrderItem;
import com.example.btlapi.GlobalVariable;
import com.example.btlapi.OrderItemManager;
import com.example.btlapi.R;
import com.example.btlapi.databinding.ActivityDetailBinding;
import com.example.btlapi.databinding.ActivityIntroBinding;
import com.example.btlapi.Domain.Food;
import java.security.PrivateKey;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    ActivityDetailBinding binding;
    private Food object;
    private int num=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getIntentExtra();
        setVariable();
    }
    private void setVariable(){
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        int imageResourceId = getImageResource(object.getImagePath());
        if (imageResourceId != 0) {
            Glide.with(DetailActivity.this)
                    .load(imageResourceId)
                    .transform(new CenterCrop(), new RoundedCorners(30))
                    .into(binding.pic);
        } else {
            binding.pic.setImageResource(R.drawable.google);
        }
        binding.priceTxt.setText(String.valueOf(object.getPrice()));
        binding.titleTxt.setText(object.getTitle());
        binding.rateTxt.setText(String.valueOf(object.getStar()));
        binding.ratingBar.setRating((float) object.getStar());
        binding.descriptionTxt.setText(object.getDescriptions());
        binding.totalTxt.setText(String.valueOf( object.getPrice()*num));
        binding.priceTxt.setText(String.valueOf(object.getPrice()));
        binding.numTxt.setText(String.valueOf(num));
        binding.plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num=num+1;
                binding.numTxt.setText(String.valueOf(num));
                binding.totalTxt.setText(String.valueOf( object.getPrice()*num));
            }
        });
        binding.subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num=num>0?num-1:0;
                binding.numTxt.setText(String.valueOf(num));
                binding.totalTxt.setText(String.valueOf( object.getPrice()*num));
            }
        });
        binding.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<OrderItem> orderItemList = OrderItemManager.getOrderItems(DetailActivity.this,GlobalVariable.userId);
                if (!orderItemList.isEmpty()){
                    for (OrderItem x : orderItemList) {
                        if (x.getProductId() == object.getId()) {
                            //System.out.println("Food:  " + x.getProductId());
                            int quantity = x.getQuantity() + Integer.parseInt(binding.numTxt.getText().toString());
                            double total = quantity * object.getPrice();
                            x.setQuantity(quantity);
                            x.setPrice(total);
                            OrderItemManager.editOrderItems(DetailActivity.this, GlobalVariable.userId, orderItemList);
                            Toast.makeText(DetailActivity.this,"Thêm vào giỏ hàng thành công",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }
                //System.out.println("Food111:  " + object.getId());
                OrderItem item1 = new OrderItem(object.getId(),Integer.parseInt(binding.numTxt.getText().toString()),Double.parseDouble(binding.totalTxt.getText().toString()));
                orderItemList.add(item1);
                OrderItemManager.addNewOrderItem(DetailActivity.this, GlobalVariable.userId,orderItemList);
                Toast.makeText(DetailActivity.this,"Thêm vào giỏ hàng thành công ",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getIntentExtra() {
        Intent intent = getIntent();
        object = (Food) intent.getSerializableExtra("object");
    }
    private int getImageResource(String imageName) {
        return this.getResources().getIdentifier(imageName, "drawable", this.getPackageName());
    }

}
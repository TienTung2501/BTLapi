package com.example.btlapi.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.btlapi.Activity.ListFoodsActivity;
import com.example.btlapi.Domain.Category;
import com.example.btlapi.R;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.viewholder>{
    ArrayList<Category> items;
    Context context;
    public CategoryAdapter(ArrayList<Category> items){
        this.items=items;
    }

    @NonNull
    @Override
    public CategoryAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View inflate= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_category,parent,false);
        return new viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.viewholder holder, @SuppressLint("RecyclerView") int position) {
        holder.titleTxt.setText(items.get(position).getName());
        //int imageResourceId = getImageResource(items.get(position).getImagePath());
        int imageResourceId = getImageResource(items.get(position).getImagePath());
        switch (position){
            case 0:{
                holder.pic.setBackgroundResource(R.drawable.cart_0_background);
                break;
            }case 1:{
                holder.pic.setBackgroundResource(R.drawable.cart_1_background);
                break;
            }case 2:{
                holder.pic.setBackgroundResource(R.drawable.cart_2_background);
                break;
            }case 3:{
                holder.pic.setBackgroundResource(R.drawable.cart_3_background);
                break;
            }case 4:{
                holder.pic.setBackgroundResource(R.drawable.cart_4_background);
                break;
            }case 5:{
                holder.pic.setBackgroundResource(R.drawable.cart_5_background);
                break;
            }case 6:{
                holder.pic.setBackgroundResource(R.drawable.cart_6_background);
                break;
            }case 7:{
                holder.pic.setBackgroundResource(R.drawable.cart_7_background);
                break;
            }

        }
        if (imageResourceId != 0) {
            Glide.with(context)
                    .load(imageResourceId)
                    .into(holder.pic);
        } else {
            holder.pic.setImageResource(R.drawable.google);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ListFoodsActivity.class);
                intent.putExtra("CategoryId",items.get(position).getId());
                intent.putExtra("CategoryName",items.get(position).getName());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{
        TextView titleTxt;
        ImageView pic;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            titleTxt=itemView.findViewById(R.id.cartNameTxt);
            pic=itemView.findViewById(R.id.imgCart);
        }
    }
    private int getImageResource(String imageName) {
        return context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
    }
}

package com.example.btlapi.Adapter;

import android.content.Context;
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
import com.example.btlapi.Domain.Food;
import com.example.btlapi.R;

import java.util.ArrayList;

public class BestFoodAdapter extends RecyclerView.Adapter<BestFoodAdapter.viewholder> {

    ArrayList<Food> items;
    Context context;
    public BestFoodAdapter(ArrayList<Food> items){
        this.items=items;
    }

    @NonNull
    @Override
    public BestFoodAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View inflate= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_best_deal,parent,false);
        return new viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull BestFoodAdapter.viewholder holder, int position) {
        holder.titleTxt.setText(items.get(position).getTitle());
        holder.priceTxt.setText("$"+items.get(position).getPrice());
        holder.starTxt.setText(String.valueOf(items.get(position).getStar()));
        holder.timeTxt.setText(items.get(position).getTimeValue()+"min");

        int imageResourceId = getImageResource(items.get(position).getImagePath());
        if (imageResourceId != 0) {
            Glide.with(context)
                    .load(imageResourceId)
                    .transform(new CenterCrop(), new RoundedCorners(30))
                    .into(holder.pic);
        } else {
            holder.pic.setImageResource(R.drawable.plus_background);
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{
        TextView titleTxt,priceTxt,starTxt,timeTxt;
        ImageView pic;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            titleTxt=itemView.findViewById(R.id.titleTxt);
            priceTxt=itemView.findViewById(R.id.priceTxt);
            starTxt=itemView.findViewById(R.id.starTxt);
            timeTxt=itemView.findViewById(R.id.timeTxt);

        }
    }
    private int getImageResource(String imageName) {
        return context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
    }
}

package com.example.btlapi.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.btlapi.Activity.DetailActivity;
import com.example.btlapi.Domain.Food;
import com.example.btlapi.Domain.OrderItem;
import com.example.btlapi.GlobalVariable;
import com.example.btlapi.OrderItemManager;
import com.example.btlapi.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
    public void onBindViewHolder(@NonNull BestFoodAdapter.viewholder holder, @SuppressLint("RecyclerView") int position) {
        holder.titleTxt.setText(items.get(position).getTitle());
        holder.priceTxt.setText("$"+items.get(position).getPrice());
        holder.starTxt.setText(String.valueOf(items.get(position).getStar()));
        holder.timeTxt.setText(items.get(position).getTimeValue()+"min");


        //int imageResourceId = getImageResource(items.get(position).getImagePath());
        int imageResourceId = getImageResource(items.get(position).getImagePath());
        if (imageResourceId != 0) {
            Glide.with(context)
                    .load(imageResourceId)
                    .transform(new CenterCrop(), new RoundedCorners(30))
                    .into(holder.pic);
        } else {
            holder.pic.setImageResource(R.drawable.google);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, DetailActivity.class);
                intent.putExtra("object", (Serializable) items.get(position));
                context.startActivity(intent);
            }
        });
        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<OrderItem> orderItemList = OrderItemManager.getOrderItems(context, GlobalVariable.userId);
                if (!orderItemList.isEmpty()){
                    for (OrderItem x : orderItemList) {
                        if (x.getProductId() == items.get(position).getId()) {
                            //System.out.println("Food:  " + x.getProductId());
                            int quantity = x.getQuantity() + 1;
                            double total = quantity * items.get(position).getPrice();
                            x.setQuantity(quantity);
                            x.setPrice(total);
                            OrderItemManager.editOrderItems(context, GlobalVariable.userId, orderItemList);
                            Toast.makeText(context,"Thêm vào giỏ hàng thành công",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }
                //System.out.println("Food111:  " + object.getId());
                OrderItem item1 = new OrderItem(items.get(position).getId(),1,items.get(position).getPrice());
                orderItemList.add(item1);
                OrderItemManager.addNewOrderItem(context,GlobalVariable.userId,orderItemList);
                Toast.makeText(context,"Thêm vào giỏ hàng thành công ",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{
        TextView titleTxt,priceTxt,starTxt,timeTxt,plus;
        ImageView pic;


        public viewholder(@NonNull View itemView) {
            super(itemView);
            titleTxt=itemView.findViewById(R.id.cartTxt);
            priceTxt=itemView.findViewById(R.id.priceTxt);
            starTxt=itemView.findViewById(R.id.starTxt);
            timeTxt=itemView.findViewById(R.id.timeTxt);
            pic=itemView.findViewById(R.id.pic);
            plus = itemView.findViewById(R.id.plusBtn);
        }
    }
    private int getImageResource(String imageName) {
        return context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
    }
}

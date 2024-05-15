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
import com.example.btlapi.Domain.OrderItem;
import com.example.btlapi.Domain.OrderItemDisplay;
import com.example.btlapi.R;

import java.util.ArrayList;

public class ListOrderDetailAdapter extends RecyclerView.Adapter<ListOrderDetailAdapter.Viewholder> {

    ArrayList<OrderItemDisplay> items;
    Context context;

    public ListOrderDetailAdapter(ArrayList<OrderItemDisplay> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ListOrderDetailAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View inflate= LayoutInflater.from(context).inflate(R.layout.viewholder_order_detail,parent,false);

        return new Viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ListOrderDetailAdapter.Viewholder holder, int position) {
        int quantity=items.get(position).getQuantity();
        double price=items.get(position).getPrice();
        holder.titleTxt.setText(String.valueOf(items.get(position).getTitle()));
        holder.siglePriceTxt.setText(String.valueOf(price));
        holder.quantityTxt.setText(String.valueOf(quantity));
        holder.totalPriceTxt.setText(String.valueOf(String.valueOf(items.get(position).getTotalPrice())));
        int imageResourceId = getImageResource(items.get(position).getImagePath());
        if (imageResourceId != 0) {
            Glide.with(context)
                    .load(imageResourceId)
                    .transform(new CenterCrop(), new RoundedCorners(30))
                    .into(holder.pic);
        } else {
            holder.pic.setImageResource(R.drawable.google);
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView titleTxt,siglePriceTxt,quantityTxt,totalPriceTxt;
        ImageView pic;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            titleTxt=itemView.findViewById(R.id.titleTxt);
            siglePriceTxt=itemView.findViewById(R.id.singlePriceTxt);
            quantityTxt=itemView.findViewById(R.id.quantityTxt);
            totalPriceTxt=itemView.findViewById(R.id.totalPriceTxt);
            pic=itemView.findViewById(R.id.pic);
        }
    }
    private int getImageResource(String imageName) {
        return context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
    }
}

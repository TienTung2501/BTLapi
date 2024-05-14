package com.example.btlapi.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btlapi.Activity.OrderDetailActivity;
import com.example.btlapi.Domain.Order;
import com.example.btlapi.R;

import java.util.ArrayList;

public class ListOrderAdapter extends RecyclerView.Adapter<ListOrderAdapter.ViewHolder> {
    ArrayList<Order> items;
    Context context;

    public ListOrderAdapter(ArrayList<Order> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ListOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View inflate= LayoutInflater.from(context).inflate(R.layout.viewholder_order,parent,false);

        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ListOrderAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.orderIdTxt.setText(items.get(position).getId());
        holder.orderDateTxt.setText(String.valueOf(items.get(position).getOrderDate()));
        holder.totalPriceTxt.setText(String.valueOf(items.get(position).getTotalPrice()));
        holder.paymentTxt.setText(items.get(position).getPaymentStatus());
        holder.detalTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, OrderDetailActivity.class);
                intent.putExtra("orderid",items.get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView orderIdTxt,orderDateTxt,totalPriceTxt,paymentTxt,detalTxt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderIdTxt=itemView.findViewById(R.id.orderIdTxt);
            orderDateTxt=itemView.findViewById(R.id.orderDateTxt);
            totalPriceTxt=itemView.findViewById(R.id.priceTxt);
            paymentTxt=itemView.findViewById(R.id.paymentTxt);
            detalTxt=itemView.findViewById(R.id.detail);
        }
    }
}

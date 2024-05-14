package com.example.btlapi.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btlapi.Activity.CartActivity;
import com.example.btlapi.Domain.Food;
import com.example.btlapi.Domain.OrderItem;
import com.example.btlapi.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

import com.example.btlapi.OrderItemManager;
public class CartAdapter extends RecyclerView.Adapter<CartAdapter.viewholder> {

    ArrayList<OrderItem> orderItems;
    Food food;
    ArrayList<Food>listFood;
    Context context;
    double total ;

    public CartAdapter(Context context,ArrayList<OrderItem> orderItems,ArrayList<Food> food) {
        this.context=context;
        this.orderItems = orderItems;
        this.listFood =food;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CartAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        System.out.println("Hello+ " + parent.getContext());
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cart,parent,false);
        return new viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.viewholder holder, int position) {

        Food foodhientai = findfoodbyid(orderItems.get(position).getProductId());
        if (foodhientai==null) Toast.makeText(context,"Không có food",Toast.LENGTH_SHORT);

        System.out.println("Hello+ "+ orderItems.get(position).getProductId() +" "+ foodhientai.getTitle());
        holder.title.setText(foodhientai.getTitle());
        OrderItem itemhientai = orderItems.get(position);
        int quantity = itemhientai.getQuantity();
        double cal = quantity*foodhientai.getPrice();
        holder.feeEachItem.setText("$"+cal);
        holder.totalEachItem.setText("$"+foodhientai.getPrice());
        holder.num.setText(String.valueOf(quantity));
        total = getTotal();
        System.out.println("Total: " + total);
        DecimalFormat df = new DecimalFormat("#.##");
        holder.subTotal.setText("$"+ String.valueOf(total));
        if(total > 20 ) {
            String num1 = df.format(total * 10 / 100);
            String num2 = df.format(total * 3 / 100);
            double num3 = Double.parseDouble(num1)+Double.parseDouble(num2)+total;
            holder.delivery.setText("$" + num1);
            holder.tax.setText("$" + num2);
            holder.Total.setText("$"+df.format(num3));
        }
        else {
            String num1 = df.format(total * 10 / 100);
            String num2 = "0";
            double num3 = Double.parseDouble(num1)+Double.parseDouble(num2)+total;
            holder.delivery.setText("$" + num1);
            holder.tax.setText("$" + num2);
            holder.Total.setText("$"+df.format(num3));
        }
        holder.plusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tăng số lượng sản phẩm khi người dùng nhấn nút plusItem
                itemhientai.setQuantity(quantity + 1);
                total+=foodhientai.getPrice();
                holder.num.setText(String.valueOf(quantity+1));
                // Cập nhật dữ liệu tạm thời
                OrderItemManager.editOrderItems(context, 1, orderItems);
                if(total > 20 ) {
                    String num1 = df.format(total * 10 / 100);
                    String num2 = df.format(total * 3 / 100);
                    double num3 = Double.parseDouble(num1)+Double.parseDouble(num2)+total;
                    holder.delivery.setText("$" + num1);
                    holder.tax.setText("$" + num2);
                    holder.Total.setText("$"+df.format(num3));
                }
                else {
                    String num1 = df.format(total * 10 / 100);
                    String num2 = "0";
                    double num3 = Double.parseDouble(num1)+Double.parseDouble(num2)+total;
                    holder.delivery.setText("$" + num1);
                    holder.tax.setText("$" + num2);
                    holder.Total.setText("$"+df.format(num3));
                }
                // Cập nhật lại giao diện
                notifyDataSetChanged();
            }
        });
        holder.minusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    total-=foodhientai.getPrice();
                    holder.subTotal.setText("$"+ String.valueOf(total));
                    if (quantity-1 == 0) {
                        orderItems.remove(itemhientai);
                        OrderItemManager.removeOrderItem(context, 1, foodhientai.getId());
                    } else {
                        holder.num.setText(String.valueOf(quantity-1));
                        // Cập nhật dữ liệu tạm thời
                        itemhientai.setQuantity(quantity - 1);
                        OrderItemManager.editOrderItems(context, 1, orderItems);
                    }
                if(total > 20 ) {
                    String num1 = df.format(total * 10 / 100);
                    String num2 = df.format(total * 3 / 100);
                    double num3 = Double.parseDouble(num1)+Double.parseDouble(num2)+total;
                    holder.delivery.setText("$" + num1);
                    holder.tax.setText("$" + num2);
                    holder.Total.setText("$"+df.format(num3));
                }
                else {
                    String num1 = df.format(total * 10 / 100);
                    String num2 = "0";
                    double num3 = Double.parseDouble(num1)+Double.parseDouble(num2)+total;
                    holder.delivery.setText("$" + num1);
                    holder.tax.setText("$" + num2);
                    holder.Total.setText("$"+df.format(num3));
                }
                    // Cập nhật lại giao diện
                    notifyDataSetChanged();
                }

        });
    }

    private Food findfoodbyid(int id) {
        for (Food foodz : listFood) {
            if(foodz.getId()==id)
            return foodz;
        }
        return null;
    }
    private double getTotal(){
        double sum1=0;
        for (OrderItem itemz : orderItems) {
            sum1+=findfoodbyid(itemz.getProductId()).getPrice()*itemz.getQuantity();
        }
        return sum1;
    }


    @Override
    public int getItemCount() {
        return orderItems.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView title, feeEachItem, plusItem, minusItem;
        ImageView pic;
        TextView totalEachItem, num;
        TextView subTotal,delivery,tax,Total;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.cartTxt);
            pic = itemView.findViewById(R.id.pic);
            feeEachItem = itemView.findViewById(R.id.totalPriceTxt);
            plusItem = itemView.findViewById(R.id.plusCartBtn);
            minusItem = itemView.findViewById(R.id.minusCartBtn);
            totalEachItem = itemView.findViewById(R.id.singlePriceTxt);
            num = itemView.findViewById(R.id.numberItemTxt);
            // Lấy resources từ context
            Resources resources = context.getResources();

            // Sử dụng resources để truy cập các tài nguyên trong R
            int textViewId1 = resources.getIdentifier("textViewSubTotal", "id", context.getPackageName());
            int textViewId2 = resources.getIdentifier("textViewDelivery", "id", context.getPackageName());
            int textViewId3 = resources.getIdentifier("textViewTax", "id", context.getPackageName());
            int textViewId4 = resources.getIdentifier("textViewTotal", "id", context.getPackageName());
            subTotal = ((CartActivity) context).findViewById(textViewId1);
            delivery = ((CartActivity) context).findViewById(textViewId2);
            tax = ((CartActivity) context).findViewById(textViewId3);
            Total = ((CartActivity) context).findViewById(textViewId4);

        }
    }

}


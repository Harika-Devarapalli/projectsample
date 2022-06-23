package com.example.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SetItemsFromOrderAdapter extends RecyclerView.Adapter<SetItemsFromOrderAdapter.MyAdapter> {
    Context context;
    List<CartData> cartDataList;


    public SetItemsFromOrderAdapter(Context context, List<CartData> cartDataList) {
        this.context = context;
        this.cartDataList = cartDataList;
    }

    @NonNull
    @Override
    public MyAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_in,parent,false);
        return new MyAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter holder, int position) {

        CartData cartData = cartDataList.get(position);
        holder.name.setText(cartData.getItemName());
        holder.price.setText(cartData.getPrice()+" "+"\u20B9");
    }

    @Override
    public int getItemCount() {
        return cartDataList.size();
    }


    public class MyAdapter extends RecyclerView.ViewHolder{

        TextView name,price;
        public MyAdapter(@NonNull View itemView) {
            super(itemView);


            name = itemView.findViewById(R.id.order_row_food_name);
            price = itemView.findViewById(R.id.orders_row_item_price);

        }
    }
}

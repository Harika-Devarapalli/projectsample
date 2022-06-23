package com.example.project;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OrdersRecyclerAdapter extends RecyclerView.Adapter<OrdersRecyclerAdapter.MyViewHolder> {

    Context context;
    List<OrderRetreiveHelper> orders;
    List<String> OrderIds;

    public OrdersRecyclerAdapter(Context context, List<OrderRetreiveHelper> orders,List<String> OrderIds) {
        this.context = context;
        this.orders = orders;
        this.OrderIds = OrderIds;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.order_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        OrderRetreiveHelper order = orders.get(position);

        //Log.e("Status",order.getOrderStatus()+"ok");
        String date = getTimeDate(order.getDate_time());


        holder.hotelName.setText(order.getHotelName());
        holder.item_count.setText(order.getCartData().size()+"");
        holder.time.setText(date);
        holder.address.setText(order.getAddress());
        holder.status.setText(order.getOrderStatus());

        ArrayList<CartData> cart = (ArrayList<CartData>) order.getCartData();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,OrderDetails.class);
                intent.putExtra("Order",order);
                intent.putExtra("orderList",cart);
                intent.putExtra("orderID",OrderIds.get(position));

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }


    static class MyViewHolder extends RecyclerView.ViewHolder{


        TextView hotelName, item_count, time, address,status;

        public MyViewHolder(@NonNull View root) {
            super(root);
            hotelName = root.findViewById(R.id.Hotel_Name_orders);
            item_count = root.findViewById(R.id.orders_total_count);
            time = root.findViewById(R.id.delivered_date_orders);
            address = root.findViewById(R.id.delivered_address_orders);
            status = root.findViewById(R.id.orders_status);
        }
    }


    public static String getTimeDate(long timestamp){
        try{
            Date netDate = (new Date(timestamp));
            SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
            return sfd.format(netDate);
        } catch(Exception e) {
            return "date";
        }
    }

}

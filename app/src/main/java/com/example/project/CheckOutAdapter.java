package com.example.project;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.List;

public class CheckOutAdapter extends ArrayAdapter<CartData> {
    Context context;
    List<CartData> cartDataList;

    public CheckOutAdapter(@NonNull Context context, int resource, List<CartData> cartDataList) {
        super(context, R.layout.items_checkout_row,cartDataList);
        this.context = context;
        this.cartDataList = cartDataList;
        //Log.e("error",""+cartDataList.toString());
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(R.layout.items_checkout_row,parent,false);

        TextView itemName,itemCost;
        itemName = v.findViewById(R.id.item_checkout_name);
        itemCost = v.findViewById(R.id.item_checkout_cost);

        CartData cartData = cartDataList.get(position);
        itemName.setText(cartData.itemName);
        itemCost.setText(cartData.price+"");
        Log.e("error",position+"");

        return v;
    }
}

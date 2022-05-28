package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CheckoutActivity extends AppCompatActivity {

    TextView resName;
    EditText address;
    ListView orderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        resName = findViewById(R.id.restaurant_title);
        address = findViewById(R.id.checkout_address);
        orderList = findViewById(R.id.checkout_item_list);

        List<CartData> cartDataList = MyApplication.cartData;
        for(CartData cart:cartDataList){
            Toast.makeText(this, ""+cart.itemName, Toast.LENGTH_SHORT).show();
        }
        CheckOutAdapter checkOutAdapter = new CheckOutAdapter(getApplicationContext(),R.layout.items_checkout_row,cartDataList);
        orderList.setAdapter(checkOutAdapter);
        checkOutAdapter.notifyDataSetChanged();


    }
}
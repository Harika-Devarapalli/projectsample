package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OrderDetails extends AppCompatActivity {

    TextView name,address,phone,time,HotelName,status,otp;
    RecyclerView recyclerView;
    OrderRetreiveHelper order;

    String final_order_id = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);


        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        order = getIntent().getParcelableExtra("Order");
        final_order_id = getIntent().getStringExtra("orderID");

        ArrayList<CartData> cart = getIntent().getParcelableArrayListExtra("orderList");

        setVals();

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        SetItemsFromOrderAdapter adapter = new SetItemsFromOrderAdapter(getApplicationContext(),cart);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        getOtp();
    }

    private void setVals() {

        HotelName = findViewById(R.id.Hotel_Name_orders);
        name = findViewById(R.id.order_name);
        phone = findViewById(R.id.order_phone);
        address = findViewById(R.id.delivered_address_orders);
        time = findViewById(R.id.delivered_date_orders);
        recyclerView = findViewById(R.id.items_in_order);
       status = findViewById(R.id.order_details_status);
       otp = findViewById(R.id.order_details_otp);




        char ch[]  = getTimeDate(order.getDate_time()).toCharArray();
        String setTime = new String(ch,ch.length-8,5);
        int TimeFlag = Integer.parseInt(new String(ch,ch.length-8,2));



        HotelName.setText(order.getHotelName());
        name.setText(order.getName());
        phone.setText(order.getPhone());
        address.setText(order.getAddress());
        status.setText(order.getOrderStatus());

        if(TimeFlag<12)
            time.setText(" "+setTime+" AM");
        else{
            TimeFlag -= 12;
            String mins  = new String(ch,ch.length-5,2);
            time.setText(" "+TimeFlag+":"+mins+" PM");
        }
    }

    public void getOtp(){
        Log.e("Order ID",final_order_id);
        FirebaseDatabase.getInstance().getReference().child("OTP").child(order.getOrder_key())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Log.e("OTP",snapshot.toString());
                        if(snapshot.exists()){
                            otp.setText(snapshot.getValue(String.class));
                        }else{
                            otp.setText("Error");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
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
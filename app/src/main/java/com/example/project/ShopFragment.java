package com.example.project;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ShopFragment extends Fragment {


    List<OrderRetreiveHelper> orders;
    RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_shop, container, false);
        orders = new ArrayList<>();
        recyclerView = root.findViewById(R.id.orders_recycler_view);

       DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Orders").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        Query query = reference.orderByChild("date_time");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot child:snapshot.getChildren()){
                        OrderRetreiveHelper order = child.getValue(OrderRetreiveHelper.class);
                        orders.add(order);
                        Log.e("order",child.toString());
                        Log.e("Date is ",getTimeDate(order.getDate_time()));
                    }
                    setRecyclerAndData();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });









        return root;
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

    public void setRecyclerAndData(){
        Collections.reverse(orders);
        OrdersRecyclerAdapter ordersAdapter = new OrdersRecyclerAdapter(getContext(),orders);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(ordersAdapter);
        ordersAdapter.notifyDataSetChanged();

    }

    /*
    *
    * .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            for(DataSnapshot child:snapshot.getChildren()){
                                OrderRetreiveHelper order = child.getValue(OrderRetreiveHelper.class);
                                Log.e("order",child.toString());
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    *
    * */
}
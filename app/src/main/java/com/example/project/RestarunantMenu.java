package com.example.project;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RestarunantMenu extends AppCompatActivity implements MenuListAdapter.MenuListClickListener{
    private List<Menu>menuList;
    private List<MenuRead>menuListSend;
    private MenuListAdapter menuListAdapter;
    String name="";
    HashMap<String, String> imageList;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restarunant_menu);
        RestaurantModel restaurantData=getIntent().getParcelableExtra("RestaurantModel");

//        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
//        toolbar.setTitle(restaurantData.getName());
        name = restaurantData.getName();


        imageList = new HashMap<>();
        menuList = new ArrayList<>();
        menuListSend = new ArrayList<>();
        initRecyclerview();

        TextView submit=findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }
    public void initRecyclerview()
      {
        recyclerView=findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Photo").child("Item");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot child:snapshot.getChildren()){
                    String key = child.getKey();
                    String value = child.getValue(String.class);
                    imageList.put(key,value);
                }
                Log.e("response",snapshot.toString());
                getDataForMenu();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public void onAddToCartButton(Menu menu) {

    }

    public void getDataForMenu(){
        menuListAdapter=new MenuListAdapter(getApplicationContext(),menuListSend,this,imageList);
        recyclerView.setAdapter(menuListAdapter);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(name);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot child:snapshot.getChildren()){
                    Menu menu = child.getValue(Menu.class);
                    menuListSend.add(new MenuRead(menu.getItem_name(),menu.getItem_price(),child.getKey()));
                }
                menuListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
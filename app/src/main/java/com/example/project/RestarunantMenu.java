package com.example.project;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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


    private static Context context;
    private List<Menu>menuList;
    private List<MenuRead>menuListSend;
    private MenuListAdapter menuListAdapter;
    String name="";
    HashMap<String, String> imageList;
    RecyclerView recyclerView;
    TextView title,address,rating,item_count,items_cost_total;
    static int countOfItems  = 0 ;
    com.google.android.material.card.MaterialCardView submit;
    boolean runResume;
    float deliveryCharge = 10;


    LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restarunant_menu);
        RestaurantModel restaurantData=getIntent().getParcelableExtra("RestaurantModel");

//        Toast.makeText(this, "Activity Start aindi", Toast.LENGTH_SHORT).show();
//        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
//        toolbar.setTitle(restaurantData.getName());


        name = restaurantData.getName();
        deliveryCharge = restaurantData.getDelivery_charge();
        title = findViewById(R.id.restaurant_title);
        address  = findViewById(R.id.restaurantAddress);
        rating = findViewById(R.id.Restaurant_Rating);
        item_count = findViewById(R.id.item_count_update);
        items_cost_total = findViewById(R.id.items_total_cost);

        loadingDialog = new LoadingDialog(this);
        loadingDialog.load2();


        context = this;

        runResume = false;
        items_cost_total.setText(0+"");
        item_count.setText("0 ITEMS");
        title.setText(restaurantData.getName());
        address.setText(restaurantData.getAddress());
        rating.setText(restaurantData.getRating());


        if(MyApplication.cartData.size()!=0){
            showWarning();
        }

        imageList = new HashMap<>();
        menuList = new ArrayList<>();
        menuListSend = new ArrayList<>();

        initRecyclerview();

        submit =findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),CheckoutActivity.class);
                intent.putExtra("hotelname",name);
                intent.putExtra("delivery_charge",deliveryCharge);

                List<CartData> cartData =  MyApplication.cartData;
                int currentCostFinal = 0;
                int count = 0;
                for(CartData cart:cartData){
                    currentCostFinal += cart.price;
                    count++;
                }


                intent.putExtra("items_cost",currentCostFinal);
                intent.putExtra("item_count",count);


                if(count == 0){

                    AlertDialog.Builder builder = new AlertDialog.Builder(RestarunantMenu.this);

                    builder.setTitle("Error");
                    builder.setMessage("Please Add Items To Cart First");

                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing but close the dialog

                            dialog.dismiss();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();



                }else{

                MyApplication.MenuLastAddedName = name;
                startActivity(intent);
                }


            }
        });
    }
    public void initRecyclerview()
      {
        recyclerView=findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //MyApplication.cartData = null;
          items_cost_total.setText(0+"");
          item_count.setText("0 ITEMS");

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


    public static void showWarning(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("There are Items Existing in Cart");
        builder.setMessage("Remove and Add New Item?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog

            MyApplication.clearData(builder.getContext());
            dialog.dismiss();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                Context context_current = builder.getContext();
                Intent intent = new Intent(context_current,CheckoutActivity.class);
                context_current.startActivity(intent);
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();


        alert.show();

    }

    @Override
    public void onAddToCartButton() {
        List<CartData> cartData =  MyApplication.cartData;
        int currentCostFinal = 0;
        int count = 0;
        for(CartData cart:cartData){
            currentCostFinal += cart.price;
            count++;
        }
        if(count == 1){
            item_count.setText("1 ITEM");
        }else if(count>1){
            item_count.setText(count+" ITEMS");
        }

        items_cost_total.setText(currentCostFinal+"");

        if(cartData.isEmpty()){
            items_cost_total.setText(0+"");
            item_count.setText("0 ITEMS");
        }

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
                loadingDialog.dismisss();
                menuListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
        if(runResume){
            onAddToCartButton();
        }
        else{

        runResume = true;
        }
    }
}
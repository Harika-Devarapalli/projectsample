package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CheckoutActivity extends AppCompatActivity implements RemoveItemsFromCart{

    TextView resName,checkOutPrice,restaurant_title;
    EditText address;
    ListView orderList;
    List<CartData> cartDataList;
    CheckOutAdapter checkOutAdapter;
    com.google.android.material.card.MaterialCardView checkOutButton;
    int TotalPrice = 0;
    UserDetails userDetails;
    int deliveryCost = 0,itemCount=0;
    float deliveryCharge = 10;
    LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);


        getUserDetails();

        /*
        * intent.putExtra("items_cost",currentCostFinal);
                intent.putExtra("item_count",count);
        * */

        deliveryCost = getIntent().getIntExtra("items_cost",0);
        itemCount = getIntent().getIntExtra("item_count",0);

        deliveryCharge = getIntent().getFloatExtra("delivery_charge",10);


        loadingDialog = new LoadingDialog(CheckoutActivity.this);
        loadingDialog.load();

        resName = findViewById(R.id.restaurant_title);
        address = findViewById(R.id.checkout_address);
        orderList = findViewById(R.id.checkout_item_list);
        checkOutPrice = findViewById(R.id.final_checkout_price);
        restaurant_title = findViewById(R.id.restaurant_title);
        checkOutButton = findViewById(R.id.checkout_final_order);

        String ht_name = getIntent().getStringExtra("hotelname");


        restaurant_title.setText(MyApplication.MenuLastAddedName);
        cartDataList = MyApplication.cartData;
        checkOutPrice.setText("0");
        setValues();

        checkOutAdapter = new CheckOutAdapter(this,R.layout.items_checkout_row,cartDataList);
        orderList.setAdapter(checkOutAdapter);
        checkOutAdapter.notifyDataSetChanged();


        checkOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //userdetails
                String id = userDetails.getRegId();
                String name = userDetails.getName();
                String phone = userDetails.getPhone();

                String order_address = address.getText().toString();
                if (order_address.equals("")) {
                    address.requestFocus();
                    address.setError("Please Fill In Address");
                    return;
                }

                List<CartData> cartData = MyApplication.cartData;
                int currentCostFinal = 0;
                int count = 0;
                for (CartData cart : cartData) {
                    currentCostFinal += cart.price;
                    count++;
                }

                if (count == 0) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(CheckoutActivity.this);

                    builder.setTitle("Error");
                    builder.setMessage("Please Go Back & Add Items To Cart First");

                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing but close the dialog

                            dialog.dismiss();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();


                }else{


                String hotel_name = MyApplication.MenuLastAddedName;
                List<CartData> FinalcartData = cartDataList;

                String key = FirebaseDatabase.getInstance().getReference().child("Orders").push().getKey();
                OrderHelper orderHelper = new OrderHelper(
                        key, id, FirebaseAuth.getInstance().getCurrentUser().getUid(), name, phone, order_address, hotel_name, "Placed", FinalcartData
                );

                FirebaseDatabase.getInstance().getReference().child("Orders").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .child(key).setValue(orderHelper)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    addToCurrentOrdersList(key, orderHelper);
                                } else {
                                    Toast.makeText(CheckoutActivity.this, "Error Placing Order ", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }



            }
        });
    }

    private void addToCurrentOrdersList(String key,OrderHelper orderHelper) {

        FirebaseDatabase.getInstance().getReference().child("CurrentOrders").child(key).setValue(orderHelper)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                MyApplication.clearData(getApplicationContext());
                                sendOtp(key);

                            }
                        });
    }

    public void sendOtp(String key){
        String getRandomOtp = getRandomNumberString();
        FirebaseDatabase.getInstance().getReference().child("OTP").child(key).setValue(getRandomOtp)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {


                        Toast.makeText(CheckoutActivity.this, "Order Placed Successfully !!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        intent.putExtra("frag",2);
                        startActivity(intent);
                        finish();

                    }
                });
    }

    @Override
    public void removeItemFromList(int position) {

        CartData currentItem = cartDataList.get(position);
        TotalPrice -= currentItem.price;
        checkOutPrice.setText(TotalPrice+(TotalPrice *0.05)+deliveryCharge +           "");

        cartDataList.remove(position);
        //MyApplication.cartData.remove(position);
        MyApplication.cartData = cartDataList;
        checkOutAdapter.notifyDataSetChanged();
        //Toast.makeText(getApplicationContext(), ""+position, Toast.LENGTH_SHORT).show();
    }

    public void setValues(){
        for(CartData cart:cartDataList){
            TotalPrice += cart.price;
        }
        checkOutPrice.setText(TotalPrice+(TotalPrice *0.1)+deliveryCharge +"");
    }

    public void getUserDetails(){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    userDetails = snapshot.getValue(UserDetails.class);
                    loadingDialog.dismisss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public static String getRandomNumberString() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }

}
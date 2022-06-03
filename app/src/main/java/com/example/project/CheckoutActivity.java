package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
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

public class CheckoutActivity extends AppCompatActivity implements RemoveItemsFromCart{

    TextView resName,checkOutPrice,restaurant_title;
    EditText address;
    ListView orderList;
    List<CartData> cartDataList;
    CheckOutAdapter checkOutAdapter;
    com.google.android.material.card.MaterialCardView checkOutButton;
    int TotalPrice = 0;
    UserDetails userDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        getUserDetails();

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
                if(order_address.equals(null)){
                    address.requestFocus();
                    address.setError("Please Fill In Address");
                    return;
                }

                String hotel_name = MyApplication.MenuLastAddedName;
                List<CartData> FinalcartData = cartDataList;

                OrderHelper orderHelper = new OrderHelper(
                        id,name,phone,order_address,hotel_name,FinalcartData
                );
                String key = FirebaseDatabase.getInstance().getReference().child("Orders").push().getKey();
                FirebaseDatabase.getInstance().getReference().child("Orders").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .child(key).setValue(orderHelper)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(CheckoutActivity.this, "Order Placed Successfully !!", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(CheckoutActivity.this, "Error Placing Order ", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });



            }
        });
    }

    @Override
    public void removeItemFromList(int position) {

        CartData currentItem = cartDataList.get(position);
        TotalPrice -= currentItem.price;
        checkOutPrice.setText(TotalPrice+"");

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
        checkOutPrice.setText(TotalPrice+"");
    }

    public void getUserDetails(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    userDetails = snapshot.getValue(UserDetails.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
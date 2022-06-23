package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.appcheck.FirebaseAppCheck;
import com.google.firebase.appcheck.safetynet.SafetyNetAppCheckProviderFactory;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    com.google.android.material.bottomnavigation.BottomNavigationView bottomNavigationView;
    ActionBar actionBar;
    FirebaseAuth firebaseAuth;
    LoadingDialog loadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getSupportActionBar().hide();

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);



        /*loadingDialog = new LoadingDialog(this);
        loadingDialog.load();*/


        MyApplication.addHotelData();
        FirebaseApp.initializeApp(/*context=*/ this);
        FirebaseAppCheck firebaseAppCheck = FirebaseAppCheck.getInstance();
        firebaseAppCheck.installAppCheckProviderFactory(
                SafetyNetAppCheckProviderFactory.getInstance());


        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()==null){
            startActivity(new Intent(getApplicationContext(),  LoginActivity.class        ));
            finish();
        }else{
            FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        }


        //actionBar.setTitle("Home");

        int[][] states = new int[][] {
                new int[] {android.R.attr.state_checked},
                new int[] {-android.R.attr.state_checked},// unchecked
                new int[] { android.R.attr.state_pressed}  // pressed
        };

        int[] colorsCyan = new int[] {

                Color.parseColor("#5AFAE2"),
                Color.parseColor("#A7FFF2"),
                Color.parseColor("#5AFAE2"),
        };

        int[] colorsRed = new int[] {
                Color.parseColor("#FB8080"),
                Color.parseColor("#FFA7A7"),
                Color.parseColor("#FA5A5A"),
        };

        int[] colorsPurple = new int[] {
                Color.parseColor("#FF01F2"),
                Color.parseColor("#EC8CF6"),
                Color.parseColor("#FC04C1"),
        };

        int[] colorsGreen = new int[] {
                Color.parseColor("#80FB8C"),
                Color.parseColor("#A7FFA8"),
                Color.parseColor("#5AFA6F"),
        };

        ColorStateList myList = new ColorStateList(states, colorsCyan);



        int flag = getIntent().getIntExtra("frag",-1);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.fragment_recomended);

        if(flag==-1) {
            bottomNavigationView = findViewById(R.id.bottom_navigation);
            bottomNavigationView.setSelectedItemId(R.id.fragment_recomended);
            getSupportFragmentManager().beginTransaction().replace(R.id.body, new RecommendedFragments()).commit();
        }
        else{
            Fragment fragment = new SearchFragment();
            fragment = new ShopFragment();
            bottomNavigationView.setSelectedItemId(R.id.item_cart);
            bottomNavigationView.setItemIconTintList( new ColorStateList(states, colorsPurple));
            getSupportFragmentManager().beginTransaction().replace(R.id.body,fragment).commit();
        }








        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = new SearchFragment();
                //ActionBar actionBar = getSupportActionBar();
                switch (item.getItemId()) {
                    case R.id.item_cart:
                        bottomNavigationView.setItemIconTintList( new ColorStateList(states, colorsRed));
                        fragment = new ShopFragment();

                        //actionBar.setTitle("Cart");
                        break;
                    case R.id.item_home:
                        bottomNavigationView.setItemIconTintList( new ColorStateList(states, colorsRed));
                        fragment = new RecommendedFragments();
                        //actionBar.setTitle("Restaurnt List");
                        break;
                    case R.id.item_profile:
                        bottomNavigationView.setItemIconTintList( new ColorStateList(states, colorsGreen));
                        fragment = new ProfileFragment();
                        //actionBar.setTitle("User Profile");
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.body,fragment)
                        .commit();



                return true;
            }
        });
    }
}
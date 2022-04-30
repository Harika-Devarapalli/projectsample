package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    com.google.android.material.bottomnavigation.BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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





        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.fragment_recomended);
        getSupportFragmentManager().beginTransaction().replace(R.id.body,new RecommendedFragments()).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = new SearchFragment();
                switch (item.getItemId()) {
                    case R.id.item_cart:
                        bottomNavigationView.setItemIconTintList( new ColorStateList(states, colorsRed));
                        fragment = new ShopFragment();
                        break;
                    case R.id.item_home:
                        bottomNavigationView.setItemIconTintList( new ColorStateList(states, colorsPurple));
                        fragment = new RecommendedFragments();
                        break;
                    case R.id.item_search:
                        bottomNavigationView.setItemIconTintList( new ColorStateList(states, colorsCyan));
                        fragment = new SearchFragment();
                        break;
                    case R.id.item_profile:
                        bottomNavigationView.setItemIconTintList( new ColorStateList(states, colorsGreen));
                        fragment = new ProfileFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.body,fragment)
                        .commit();



                return true;
            }
        });
    }
}
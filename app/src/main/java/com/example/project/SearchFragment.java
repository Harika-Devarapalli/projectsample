package com.example.project;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SearchFragment extends Fragment {

    Button button;
    ImageView imageView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_search, container, false);

        button = root.findViewById(R.id.firebase_image_retrieve);
        imageView = root.findViewById(R.id.firebase_image);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("click","clickef");
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Photos").child("BrownieHeaven");
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Log.e("response",snapshot.toString());
                        if(snapshot.exists()){
                            ImageModel image= snapshot.getValue(ImageModel.class);
                            Glide.with(getContext()).load(image.imageUrl).into(imageView);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("response",error.toString());
                    }
                });
            }
        });

        return root;
    }
}
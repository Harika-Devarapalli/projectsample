package com.example.project;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class RecommendedFragments extends Fragment  implements RestaurantListAdapter.RestaurantClickListener {

    View root;
    List<RestaurantModel> restaurantModelList;
    RestaurantListAdapter adapter;
    LoadingDialog loadingDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_recommended_fragments, container, false);



        loadingDialog = new LoadingDialog(getActivity());
        loadingDialog.load();

        restaurantModelList = new ArrayList<>();
        adapter=new RestaurantListAdapter(restaurantModelList,this);
        initRecyclerView(restaurantModelList);
        return root;
    }

    private void initRecyclerView(List<RestaurantModel> restaurantModelList)
    {
        RecyclerView recyclerview=root.findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerview.setAdapter(adapter);
        getRestaurantData();
    }
    private void getRestaurantData(){
        /*InputStream is=getResources().openRawResource(R.raw.res);
        Writer writer=new StringWriter();
        char[] buffer=new char[1024];
        try
        {
            Reader reader=new BufferedReader(new InputStreamReader(is,"UTF-8"));
            int n;
            while((n=reader.read(buffer))!=-1)
            {
                writer.write(buffer,0,n);
            }
        }
        catch (Exception e)
        {

        }
        String jsonStr=writer.toString();
        Gson gson=new Gson();
        RestaurantModel[] restaurantModels=gson.fromJson(jsonStr,RestaurantModel[].class);
        List<RestaurantModel> restList= Arrays.asList(restaurantModels);*/

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("restruantInfo");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.e("response",snapshot.toString());
                for(DataSnapshot child_snap:snapshot.getChildren()){
                    RestaurantModel child = child_snap.getValue(RestaurantModel.class);
                    //Log.e("child is",child.getName());
                    restaurantModelList.add(child);
                }
                loadingDialog.dismisss();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }

    @Override
    public void onItemClick(RestaurantModel restaurantModel) {
        Log.e("Start",restaurantModel.getName());
        Intent intent=new Intent(getContext(),RestarunantMenu.class);
        intent.putExtra("RestaurantModel",restaurantModel);
        startActivity(intent);
    }
}
package com.example.project;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;


import java.util.HashMap;
import java.util.List;

public class MenuListAdapter extends RecyclerView.Adapter<MenuListAdapter.MyViewHolder> {
    private List<MenuRead> menuList;
    private MenuListClickListener ClickListener;
    private HashMap<String, String> imagesList;
    Context context;
    public MenuListAdapter(Context context,List<MenuRead> menuList, MenuListClickListener clickListener, HashMap<String, String> imagesList)
    {
        this.context = context;
         this.menuList=menuList;
         this.ClickListener=clickListener;
         this.imagesList = imagesList;
    }
    public void updateData(List<MenuRead> menuList)
    {
        this.menuList=menuList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_recycler_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.menuName.setText(menuList.get(position).getItem_name());
        holder.menuprice.setText("Price : " +menuList.get(position).getItem_price());
        holder.addtocartbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        char[] id = menuList.get(position).getItem_id().toCharArray();
        String id_str = ""+id[2]+id[3];
        Log.e("id",id_str);
        if(imagesList.containsKey(id_str))
            Glide.with(context).load(imagesList.get(id_str)).into(holder.image);
        else
            Log.e("Photo Error",id_str);
       //holder.image.setImageBitmap(bi);
        holder.addtocartbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                    char[] id = menuList.get(position).getItem_id().toCharArray();
                    String id_str = ""+id[0]+id[1];
                    String last_added = MyApplication.hotelName;
                    if(id_str.equals(last_added)){
                        CartData cartData = new CartData(menuList.get(position).getItem_name(),
                                MyApplication.hotels.get("id_str")
                                ,Integer.parseInt(menuList.get(position).getItem_price()));

                        MyApplication.cartData.add(cartData);
                        /*for(CartData cartData1:MyApplication.cartData)
                            Toast.makeText(context, cartData1.itemName, Toast.LENGTH_SHORT).show();*/

                    }else{
                        //Toast.makeText(context, id_str+" "+last_added, Toast.LENGTH_SHORT).show();
                        //int flag = RestarunantMenu.showWarning();
                        MyApplication.clearData(context);
                        MyApplication.hotelName = id_str;
                        CartData cartData = new CartData(menuList.get(position).getItem_name(),
                                MyApplication.hotels.get("id_str")
                                ,Integer.parseInt(menuList.get(position).getItem_price()));

                        MyApplication.cartData.add(cartData);
                        //Toast.makeText(context, ""+MyApplication.cartData.toString(), Toast.LENGTH_SHORT).show();
                }
                ClickListener.onAddToCartButton();
            }
        });


       /* holder.addtocartbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
        });
*/
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }
    static class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView menuName;
        TextView menuprice;
        TextView addtocartbutton;
        ImageView image;


        public MyViewHolder(View view)
        {
            super(view);
            menuName=view.findViewById(R.id.menuName);
            menuprice=view.findViewById(R.id.menuprice);
            addtocartbutton=view.findViewById(R.id.addtocartbutton);
            image=view.findViewById(R.id.image);
        }
    }
    public interface MenuListClickListener
    {
        public void onAddToCartButton();

    }
}

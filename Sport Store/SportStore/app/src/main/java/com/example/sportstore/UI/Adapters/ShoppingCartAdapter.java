package com.example.sportstore.UI.Adapters;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportstore.R;
import com.example.sportstore.UI.Data.ItemInStore;

import java.util.List;

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.MyViewHolder>  {

     Context context;
     List<ItemInStore> listItemsInMyCart;



    public ShoppingCartAdapter(Context context, List<ItemInStore> listItemsInMyCart) {
        this.context = context;
        this.listItemsInMyCart = listItemsInMyCart.subList(1,listItemsInMyCart.size());
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageItem_Im;
        TextView itemName_Tv , itemDescription_Tv, itemPrice_Tv,itemCount_Tv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageItem_Im = itemView.findViewById(R.id.item_image);
            itemName_Tv = itemView.findViewById(R.id.item_name);
            itemDescription_Tv = itemView.findViewById(R.id.item_description);
            itemPrice_Tv = itemView.findViewById(R.id.item_price);
            itemCount_Tv = itemView.findViewById(R.id.item_count_cart);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_in_cart_layout,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ItemInStore item = listItemsInMyCart.get(position);
        holder.imageItem_Im.setImageResource(item.getItemImage());
        holder.itemName_Tv.setText(item.getItemName());
        holder.itemDescription_Tv.setText(item.getItemDescription());
        holder.itemPrice_Tv.setText(item.getPrice());
        holder.itemCount_Tv.setText(item.getCount());

    }

    @Override
    public int getItemCount() {
        return listItemsInMyCart.size();
    }


}

package com.example.sportstore.UI.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportstore.R;
import com.example.sportstore.UI.Data.ItemInStore;

import java.util.List;

public class OrdersHistoryAdapter extends RecyclerView.Adapter<OrdersHistoryAdapter.MyViewHolder>  {

    Context context;
    List<ItemInStore> orderHistoryList;



    public OrdersHistoryAdapter(Context context, List<ItemInStore> listItemsInMyCart) {
        this.context = context;
        this.orderHistoryList = listItemsInMyCart.subList(1,listItemsInMyCart.size());
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageItem_Im;
        TextView itemName_Tv , itemPrice_Tv,itemCount_Tv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageItem_Im = itemView.findViewById(R.id.image);
            itemName_Tv = itemView.findViewById(R.id.title);
            itemPrice_Tv = itemView.findViewById(R.id.price);
            itemCount_Tv = itemView.findViewById(R.id.count);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_has_order_layout,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ItemInStore item = orderHistoryList.get(position);
        holder.imageItem_Im.setImageResource(item.getItemImage());
        holder.itemName_Tv.setText(item.getItemName());
        holder.itemPrice_Tv.setText(item.getPrice());
        holder.itemCount_Tv.setText(item.getCount());

    }

    @Override
    public int getItemCount() {
        return orderHistoryList.size();
    }


}


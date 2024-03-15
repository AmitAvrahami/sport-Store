package com.example.sportstore.UI.Adapters;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportstore.R;
import com.example.sportstore.UI.Data.ItemInStore;

import java.util.ArrayList;

public class StoreDetailsAdapter extends RecyclerView.Adapter<StoreDetailsAdapter.StoreDetailViewHolder> {

    Context context;
    ArrayList<ItemInStore> itemsList;

    private  OnItemClickedListener listener;

    public StoreDetailsAdapter(Context context, ArrayList<ItemInStore> itemsList ,OnItemClickedListener listener) {
        this.context = context;
        this.itemsList = itemsList;
        this.listener = listener;
    }

    public static class StoreDetailViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageItem_Im;
        TextView itemName_Tv , itemDescription_Tv, itemPrice_Tv,itemCount_Tv;
        Button increaseCount_Btn,decreaseCount_Btn;

        public StoreDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            imageItem_Im = itemView.findViewById(R.id.item_image);
            itemName_Tv = itemView.findViewById(R.id.item_name);
            itemDescription_Tv = itemView.findViewById(R.id.item_description);
            itemPrice_Tv = itemView.findViewById(R.id.item_price);
            itemCount_Tv = itemView.findViewById(R.id.item_count);
            increaseCount_Btn = itemView.findViewById(R.id.button_plus);
            decreaseCount_Btn = itemView.findViewById(R.id.button_minus);



        }



    }
    @NonNull
    @Override
    public StoreDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_layout,parent,false);

        return new StoreDetailViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreDetailViewHolder holder, int position) {
        ItemInStore item = itemsList.get(position);
        holder.imageItem_Im.setImageResource(item.getItemImage());
        holder.itemName_Tv.setText(item.getItemName());
        holder.itemDescription_Tv.setText(item.getItemDescription());
        holder.itemPrice_Tv.setText(item.getPrice());
        holder.itemCount_Tv.setText(item.getCount());
        holder.increaseCount_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer currentCountValue = Integer.parseInt(holder.itemCount_Tv.getText().toString());
                currentCountValue++;
                holder.itemCount_Tv.setText(currentCountValue.toString());
                itemsList.get(position).setCount(currentCountValue.toString());
                listener.increaseCount(itemsList.get(position));

            }
        });

        holder.decreaseCount_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer currentCountValue = Integer.parseInt(holder.itemCount_Tv.getText().toString());
                if(currentCountValue > 0){
                    currentCountValue--;
                    holder.itemCount_Tv.setText(currentCountValue.toString());
                    itemsList.get(position).setCount(currentCountValue.toString());
                    listener.decreaseCount(itemsList.get(position));
                }
            }
        });




    }

    @Override
    public int getItemCount() {
        return this.itemsList.size();
    }


}

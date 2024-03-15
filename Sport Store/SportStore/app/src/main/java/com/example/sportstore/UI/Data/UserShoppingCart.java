package com.example.sportstore.UI.Data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class UserShoppingCart {
    String user;
    ArrayList<ItemInStore> itemsInCart;
    ArrayList<ItemInStore> myOrders;


    public UserShoppingCart() {

    }

    public UserShoppingCart(String user, ArrayList<ItemInStore> itemsInCart, ArrayList<ItemInStore> myOrders) {
        this.user = user;
        this.itemsInCart = itemsInCart;
        this.myOrders = myOrders;
    }




    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public ArrayList<ItemInStore> getItemsInCart() {
        return itemsInCart;
    }

    public void setItemsInCart(ArrayList<ItemInStore> itemsInCart) {
        this.itemsInCart = itemsInCart;
    }

    public ArrayList<ItemInStore> getMyOrders() {
        return myOrders;
    }

    public void setMyOrders(ArrayList<ItemInStore> myOrders) {
        this.myOrders = myOrders;
    }


}

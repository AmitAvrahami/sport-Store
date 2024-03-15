package com.example.sportstore.UI.Data;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataBaseOperations {
    private FirebaseAuth mAuth;
    private static FirebaseUser user;
    public interface ShoppingCartCallback {
        void onCartRead(UserShoppingCart userShoppingCart);
        void onCancelled(DatabaseError error);
    }

    public static void readData(String uid, ShoppingCartCallback callback) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserShoppingCart userShoppingCart = snapshot.getValue(UserShoppingCart.class);
                    callback.onCartRead(userShoppingCart);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                if (callback != null) {
                    callback.onCancelled(error);
                }
            }
        });
    }


    public static void writeData(UserShoppingCart updateUser, String uid) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users").child(uid);
        Map<String, Object> updateUserMap = new HashMap<>();
        updateUserMap.put("user", updateUser.getUser());
        updateUserMap.put("itemsInCart", updateUser.getItemsInCart());
        updateUserMap.put("myOrders", updateUser.getMyOrders());
        myRef.updateChildren(updateUserMap);
    }
}

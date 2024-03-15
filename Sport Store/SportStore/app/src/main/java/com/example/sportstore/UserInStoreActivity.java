package com.example.sportstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.sportstore.UI.Data.ItemInStore;
import com.example.sportstore.UI.Data.UserShoppingCart;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

public class UserInStoreActivity extends AppCompatActivity {
    private NavigationBarView navigationBarView;

    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_in_store);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container_view_2);
        navController = navHostFragment.getNavController();

        navigationBarView = findViewById(R.id.bottomAppBar);
        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.shopping_cart_icon) {
                    navController.navigate(R.id.shopingCart);
                    return true;
                }
                else if (item.getItemId() == R.id.store_icon) {
                    navController.navigate(R.id.storeDetails);
                    return true;
                }
                else if (item.getItemId() == R.id.orders_icon){
                    navController.navigate(R.id.userOrderss);
                    return true;
                }
                return false;
            }
        });

    }
}
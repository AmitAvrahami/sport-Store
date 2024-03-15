package com.example.sportstore.UI.Fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportstore.R;
import com.example.sportstore.UI.Adapters.ShoppingCartAdapter;
import com.example.sportstore.UI.Data.DataBaseOperations;
import com.example.sportstore.UI.Data.ItemInStore;
import com.example.sportstore.UI.Data.UserShoppingCart;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShopingCart#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShopingCart extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private TextView totalItemsCount_Tv, totalPrice_Tv;

    private ProgressBar progressBar;
    private Button makeOrderBtn;
    private ShoppingCartAdapter myAdapter;
    private List<ItemInStore> userItemsList;
    private MaterialCardView materialCardView;

    private UserShoppingCart currentUser;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();

    public ShopingCart() {
        // Required empty public constructor
    }

    public static ShopingCart newInstance(String param1, String param2) {
        ShopingCart fragment = new ShopingCart();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shoping_cart, container, false);
        progressBar = view.findViewById(R.id.progress_circular_cart);
        materialCardView = view.findViewById(R.id.cart_details);
        makeOrderBtn = view.findViewById(R.id.make_order_btn);
        DataBaseOperations.readData(user.getUid(), new DataBaseOperations.ShoppingCartCallback() {
            @Override
            public void onCartRead(UserShoppingCart userShoppingCart) {
                currentUser = userShoppingCart;
                recyclerView = view.findViewById(R.id.recycle_view_cart);
                totalItemsCount_Tv = view.findViewById(R.id.total_item_count);
                totalPrice_Tv = view.findViewById(R.id.total_price);
                myAdapter = new ShoppingCartAdapter(getContext(), currentUser.getItemsInCart());
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(myAdapter);
                totalItemsCount_Tv.setText("Total count of items : " + totalCountItemsCalculator(currentUser.getItemsInCart()));
                totalPrice_Tv.setText("Total price : " + totalPriceCalculator(currentUser.getItemsInCart()));
                if (recyclerView.getVisibility() == View.VISIBLE && materialCardView.getVisibility() == View.VISIBLE) {
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        makeOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                alertDialogBuilder.setTitle("Verify order");
                alertDialogBuilder.setMessage("Are you sure you want to make an order?");
                alertDialogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (ItemInStore item : currentUser.getItemsInCart().subList(1, currentUser.getItemsInCart().size())) {
                            currentUser.getMyOrders().add(item);
                        }
                        currentUser.getItemsInCart().clear();
                        currentUser.getItemsInCart().add(new ItemInStore());
                        DataBaseOperations.writeData(currentUser, user.getUid());
                        dialog.dismiss();
                    }
                });
                alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialogBuilder.show();


            }
        });
    }

    private String totalPriceCalculator(List<ItemInStore> userItemsInCart) {
        Float totalPrice = 0f;
        userItemsInCart = userItemsInCart.subList(1, userItemsInCart.size());
        for (ItemInStore item : userItemsInCart) {
            String[] wordsInThePrice = item.getPrice().split(":");
            totalPrice += (Float.parseFloat(wordsInThePrice[1])) * (Integer.parseInt(item.getCount()));
        }
        return String.valueOf(totalPrice);
    }

    private String totalCountItemsCalculator(List<ItemInStore> userItemsInCart) {
        Integer totalCount = 0;

        userItemsInCart = userItemsInCart.subList(1, userItemsInCart.size());
        for (ItemInStore item : userItemsInCart) {
            totalCount += Integer.parseInt(item.getCount());
        }
        return String.valueOf(totalCount);
    }
}
package com.example.sportstore.UI.Fragments;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportstore.R;
import com.example.sportstore.UI.Adapters.OnItemClickedListener;
import com.example.sportstore.UI.Adapters.StoreDetailsAdapter;
import com.example.sportstore.UI.Data.DataBaseOperations;
import com.example.sportstore.UI.Data.ItemInStore;
import com.example.sportstore.UI.Data.UserShoppingCart;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;


public class StoreDetails extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private ArrayList<ItemInStore> itemsList;
    private String[] names;
    private String[] descriptions;
    private String[] prices;
    private TypedArray images;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private StoreDetailsAdapter storeDetailsAdapter;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    public UserShoppingCart currentUser;

    public StoreDetails() {

    }

    public static StoreDetails newInstance(String param1, String param2) {
        StoreDetails fragment = new StoreDetails();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_store_details, container, false);
        itemsListInitial();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        progressBar = view.findViewById(R.id.progress_circular);
        DataBaseOperations.readData(user.getUid(), new DataBaseOperations.ShoppingCartCallback() {
            @Override
            public void onCartRead(UserShoppingCart userShoppingCart) {
                currentUser = userShoppingCart;
                for (int i = 0; i < itemsList.size(); i++) {
                    for (int j = 0; j < currentUser.getItemsInCart().size(); j++) {
                        if (itemsList.get(i).getItemName().equals(currentUser.getItemsInCart().get(j).getItemName())) {
                            itemsList.set(i, currentUser.getItemsInCart().get(j));
                            break;
                        }
                    }
                }
                storeDetailsAdapter = new StoreDetailsAdapter(getContext(), itemsList, new OnItemClickedListener() {
                    @Override
                    public void increaseCount(ItemInStore itemInStore) {
                        Integer countOfItem = Integer.parseInt(itemInStore.getCount());
                        if (countOfItem == 1) {
                            currentUser.getItemsInCart().add(itemInStore);
                            DataBaseOperations.writeData(currentUser, user.getUid());
                        } else if (countOfItem > 1) {
                            for (int i = 0; i < currentUser.getItemsInCart().size(); i++) {
                                if (currentUser.getItemsInCart().get(i).getItemName() == itemInStore.getItemName()) {
                                    currentUser.getItemsInCart().get(i).setCount(String.valueOf(countOfItem));
                                    DataBaseOperations.writeData(currentUser, user.getUid());
                                    break;
                                }
                            }
                        }


                    }

                    @Override
                    public void decreaseCount(ItemInStore itemInStore) {
                        Integer countOfItem = Integer.parseInt(itemInStore.getCount());
                        if (countOfItem == 0) {
                            currentUser.getItemsInCart().remove(itemInStore);
                            DataBaseOperations.writeData(currentUser, user.getUid());
                        }
                    }
                });
                recyclerView.setAdapter(storeDetailsAdapter);
                if(recyclerView.getVisibility() == View.VISIBLE){
                    progressBar.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);


        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public UserShoppingCart getCurrentUser() {
        return currentUser;
    }

    private void itemsListInitial() {
        names = getResources().getStringArray(R.array.items_names);
        descriptions = getResources().getStringArray(R.array.items_descriptions);
        prices = getResources().getStringArray(R.array.items_prices);
        images = getResources().obtainTypedArray(R.array.images_List);
        itemsList = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            ItemInStore item = new ItemInStore(
                    images.getResourceId(i, -1),
                    "price : " + prices[i],
                    "0",
                    names[i],
                    descriptions[i]
            );
            itemsList.add(item);
        }

    }


}
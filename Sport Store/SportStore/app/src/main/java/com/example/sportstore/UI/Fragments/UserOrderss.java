package com.example.sportstore.UI.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sportstore.R;
import com.example.sportstore.UI.Adapters.OrdersHistoryAdapter;
import com.example.sportstore.UI.Data.DataBaseOperations;
import com.example.sportstore.UI.Data.ItemInStore;
import com.example.sportstore.UI.Data.UserShoppingCart;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserOrderss#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserOrderss extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<ItemInStore> ordersList;
    private UserShoppingCart currentUser;
    private OrdersHistoryAdapter ordersHistoryAdapter;
    private RecyclerView recyclerView;
    FirebaseAuth mauth = FirebaseAuth.getInstance();
    FirebaseUser user = mauth.getCurrentUser();

    public UserOrderss() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserOrderss.
     */
    // TODO: Rename and change types and number of parameters
    public static UserOrderss newInstance(String param1, String param2) {
        UserOrderss fragment = new UserOrderss();
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
        View view =  inflater.inflate(R.layout.fragment_user_orderss, container, false);
        DataBaseOperations.readData(user.getUid(), new DataBaseOperations.ShoppingCartCallback() {
            @Override
            public void onCartRead(UserShoppingCart userShoppingCart) {
                currentUser = userShoppingCart;
                recyclerView = view.findViewById(R.id.rec_v);
                ordersHistoryAdapter = new OrdersHistoryAdapter(getContext(), currentUser.getMyOrders());
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
                recyclerView.setAdapter(ordersHistoryAdapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }

        });



        return view;
    }
}
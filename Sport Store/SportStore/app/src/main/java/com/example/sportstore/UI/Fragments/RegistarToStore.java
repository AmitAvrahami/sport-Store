package com.example.sportstore.UI.Fragments;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.sportstore.R;
import com.example.sportstore.UI.Data.DataBaseOperations;
import com.example.sportstore.UI.Data.ItemInStore;
import com.example.sportstore.UI.Data.UserShoppingCart;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegistarToStore#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistarToStore extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private boolean passwordVisible = false;

    public RegistarToStore() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegistarToStore.
     */
    // TODO: Rename and change types and number of parameters
    public static RegistarToStore newInstance(String param1, String param2) {
        RegistarToStore fragment = new RegistarToStore();
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

        View view = inflater.inflate(R.layout.fragment_registar_to_store, container, false);
        Button registerBtn;
        FirebaseAuth mAuth;
        TextInputEditText emailTextInput, passwordTextInput, verifyPasswordTextInput;
        TextInputLayout emailInputLayout, passwordInputLayout;


        mAuth = FirebaseAuth.getInstance();
        registerBtn = view.findViewById(R.id.register_btn);
        emailTextInput = view.findViewById(R.id.email_input);
        passwordTextInput = view.findViewById(R.id.password_input);
        verifyPasswordTextInput = view.findViewById(R.id.verify_password);
        emailInputLayout = view.findViewById(R.id.email_container);
        passwordInputLayout = view.findViewById(R.id.password_container);

        emailInputLayout.setHelperTextEnabled(false);
        emailInputLayout.setHelperTextColor(ColorStateList.valueOf(Color.RED));
        passwordInputLayout.setHelperTextEnabled(false);
        passwordInputLayout.setHelperTextColor(ColorStateList.valueOf(Color.RED));
        passwordInputLayout.setStartIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (passwordVisible) {
                    passwordTextInput.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    passwordTextInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                passwordVisible = !passwordVisible;
            }

        });


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password, verifyPassword;
                email = Objects.requireNonNull(emailTextInput.getText()).toString();
                password = Objects.requireNonNull(passwordTextInput.getText()).toString();
                verifyPassword = Objects.requireNonNull(verifyPasswordTextInput.getText()).toString();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getContext(), "enter email and password", Toast.LENGTH_LONG).show();
                } else if (!password.equals(verifyPassword)) {
                    Toast.makeText(getContext(), "passwords not equals", Toast.LENGTH_LONG).show();
                } else if (password.length() > 8 || password.length() < 6) {
                    Toast.makeText(getContext(), "password is to short or to long", Toast.LENGTH_LONG).show();
                    passwordInputLayout.setHelperText("enter 6-8 characters without ' ' ,'_' ,'@' ");
                    passwordInputLayout.setHelperTextEnabled(true);
                } else {
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Toast.makeText(getContext(), "Registration succeeded", Toast.LENGTH_LONG).show();
                                        UserShoppingCart newUser = new UserShoppingCart(email, new ArrayList<>(), new ArrayList<>());
                                        newUser.getItemsInCart().add(new ItemInStore());
                                        newUser.getMyOrders().add(new ItemInStore());
                                        DataBaseOperations.writeData(newUser,user.getUid());
                                        Navigation.findNavController(view).navigate(R.id.action_registarToStore2_to_loginToStore);
                                    } else {
                                        emailInputLayout.setHelperText("enter your email in this format example@example.example");
                                        emailInputLayout.setHelperTextEnabled(true);
                                        passwordInputLayout.setHelperText("enter 6-8 characters without ' ' ,'_' ,'@' ");
                                        passwordInputLayout.setHelperTextEnabled(true);

                                        Log.d("TAG", String.valueOf(task.getException()));
                                        Toast.makeText(getContext(), "Registration failed , worng mail or password", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });
        return view;
    }


}
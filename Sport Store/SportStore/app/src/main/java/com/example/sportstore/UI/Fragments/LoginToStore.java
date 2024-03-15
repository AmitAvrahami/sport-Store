package com.example.sportstore.UI.Fragments;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.sportstore.R;
import com.example.sportstore.UI.Data.UserShoppingCart;
import com.example.sportstore.UserInStoreActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginToStore#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginToStore extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginToStore() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginToStore.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginToStore newInstance(String param1, String param2) {
        LoginToStore fragment = new LoginToStore();
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
        View view =  inflater.inflate(R.layout.fragment_login_to_store, container, false);
        TextInputEditText emailTextInput,passwordTextInput;
        Button loginBtn , registerBtn;
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        String email,password;
        emailTextInput = view.findViewById(R.id.email_input);
        passwordTextInput = view.findViewById(R.id.password_input);
        registerBtn = view.findViewById(R.id.register_btn);
        loginBtn = view.findViewById(R.id.login_btn);
        TextInputLayout emailContainer = view.findViewById(R.id.email_container_login);
        TextInputLayout passwordContainer = view.findViewById(R.id.password_container_login);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String email = emailTextInput.getText().toString().trim();
                String password = passwordTextInput.getText().toString().trim();
                loginBtn.setEnabled(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password));
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        emailTextInput.addTextChangedListener(textWatcher);
        passwordTextInput.addTextChangedListener(textWatcher);






        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailTextInput.getText().toString().trim();
                String password = passwordTextInput.getText().toString().trim();

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(getContext(), "Authentication sucsses.", Toast.LENGTH_SHORT).show();
                                   // Navigation.findNavController(view).navigate(R.id.action_loginToStore_to_userInStoreActivity);
                                    Intent intent = new Intent(getContext(), UserInStoreActivity.class);
                                    startActivity(intent);

                                } else {
                                    // If sign in fails, display a message to the user.
                                    passwordContainer.setHelperText("incorrect email or password try again");
                                    passwordContainer.setHelperTextColor(ColorStateList.valueOf(Color.RED));
                                    emailContainer.setHelperTextEnabled(true);
                                    Toast.makeText(getContext(), "user login failed", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_loginToStore_to_registarToStore2);
            }
        });


        return view;
    }

}
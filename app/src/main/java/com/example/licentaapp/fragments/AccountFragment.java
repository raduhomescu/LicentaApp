package com.example.licentaapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.example.licentaapp.LoginActivity;
import com.example.licentaapp.MainActivity;
import com.example.licentaapp.R;
import com.example.licentaapp.RegisterActivity;
import com.google.firebase.auth.FirebaseAuth;

public class AccountFragment extends Fragment {
    Button btn_spre_login;
    Button btn_spre_register;
    FirebaseAuth fAuth;

    public AccountFragment() {
        // Required empty public constructor
    }

    public static AccountFragment getInstance() {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //aici pui datele pe care le aduci
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        initComponents(view);
        return view;
    }

    private void initComponents(View view) {
        if (getContext() != null) {
            fAuth = FirebaseAuth.getInstance();
            btn_spre_login=view.findViewById(R.id.btn_login_page);
            btn_spre_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(view.getContext().getApplicationContext(), LoginActivity.class));
                }
            });

            btn_spre_register=view.findViewById(R.id.btn_register_page);
            btn_spre_register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(view.getContext().getApplicationContext(), RegisterActivity.class));
                }
            });
        }
    }
}
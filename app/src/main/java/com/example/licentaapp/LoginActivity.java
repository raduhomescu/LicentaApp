package com.example.licentaapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.licentaapp.utils.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText tietLoginEmail;
    TextInputEditText tietLoginPassword;
    Button btnLogin;
    TextView tvNotRegistered;
    ProgressBar progressBar;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initComponents();
    }

    private void initComponents() {
        tietLoginEmail = findViewById(R.id.login_tiet_email);
        tietLoginPassword = findViewById(R.id.login_tiet_password);
        btnLogin = findViewById(R.id.btn_login);
        tvNotRegistered = findViewById(R.id.tv_not_registered);
        progressBar = findViewById(R.id.progressBar_login);
        fAuth = FirebaseAuth.getInstance();
        Intent intent = new Intent(this, MainActivity.class);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=tietLoginEmail.getText().toString().trim();
                String password= tietLoginPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    tietLoginEmail.setError("Email is required.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    tietLoginEmail.setError("Pasword is required.");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //authentificate the user
                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Logged in succesfully", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });
            }
        });

        tvNotRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });
    }
}
package com.example.licentaapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    ActionBar actionBar;
    TextInputEditText tietName;
    TextInputEditText tietEmail;
    TextInputEditText tietPassword;
    TextInputEditText tietRepeatPassword;
    TextInputEditText tietPhone;
    Button btnRegister;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    FirebaseFirestore fStore;
    String userID;
    private Intent intent;
    private User userRegistered = new User();
    ArrayList<String> phonesCodes = new ArrayList<>();
    public static final String USER_KEY = "User key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initComponents();
        intent = getIntent();
    }

    private void initComponents() {
        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.search_gadget_30dp);
        actionBar.setDisplayHomeAsUpEnabled(true);

        tietName = findViewById(R.id.register_tiet_name);
        tietEmail = findViewById(R.id.register_tiet_email);
        tietPassword = findViewById(R.id.register_tiet_password);
        tietRepeatPassword = findViewById(R.id.register_tiet_repeat_password);
        tietPhone = findViewById(R.id.register_tiet_phone);

        btnRegister = findViewById(R.id.btn_register);
        progressBar = findViewById(R.id.progressBar);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = tietName.getText().toString().trim();
                String email = tietEmail.getText().toString().trim();
                String password = tietPassword.getText().toString().trim();
                String repeatPassword = tietRepeatPassword.getText().toString().trim();
                String phone = tietPhone.getText().toString().trim();

                if (TextUtils.isEmpty(fullName)) {
                    tietName.setError("Full name is required.");
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    tietEmail.setError("Email is required.");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    tietPassword.setError("Password is required.");
                    return;
                }

                boolean allLowerCase = true;

                for (int i = 0; i < password.length(); i++) {
                    if (!Character.isLowerCase(password.charAt(i))) {
                        allLowerCase = false;
                        break;
                    }
                }
                boolean containsNumber = password.matches(".*\\d.*");
                if (allLowerCase || !containsNumber || password.length() < 8) {
                    tietPassword.setError("Password must contain at least 8 characters, one upper letter and a number.");
                    return;
                }

                if (TextUtils.isEmpty(repeatPassword)) {
                    tietRepeatPassword.setError("Password confirmation is required.");
                    return;
                }

                if (!repeatPassword.equals(password)) {
                    tietRepeatPassword.setError("The passwords do not match.");
                    return;
                }

                if (TextUtils.isEmpty(phone)) {
                    tietPhone.setError("Phone Number is required.");
                    return;
                }

                if(phone.length() != 10){
                    tietPhone.setError("Phone Number must be romanian type.");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //register the user in firebase
                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "User created.", Toast.LENGTH_SHORT).show();
                            userID= fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("clients").document(userID);
                            Map<String, Object> user= new HashMap<>();
                            user.put("fName", fullName);
                            user.put("email", email);
                            user.put("phone", phone);
                            user.put("favourites", new ArrayList<String>());
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d(TAG, "onSuccess: user Profile is created for "+ userID);
                                    setResult(RESULT_OK, intent);
                                    finish();
                                    progressBar.setVisibility(View.INVISIBLE);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: " + e.toString());
                                }
                            });
                        //fAuth.signOut();
                        progressBar.setVisibility(View.INVISIBLE);

                        } else {
                            Toast.makeText(RegisterActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });

            }
        });
    }
}
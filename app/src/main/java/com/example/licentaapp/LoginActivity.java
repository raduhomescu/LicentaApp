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
import com.google.firebase.auth.FirebaseAuthSettings;
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
    Button btnResetPassword;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    private Intent intent;
    private User user = new User();
    ArrayList<String> phonesCodes = new ArrayList<>();
    String userID;
    FirebaseFirestore fStore;
    public static final String USER_KEY = "User key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initComponents();
        intent = getIntent();
    }

    private void initComponents() {
        tietLoginEmail = findViewById(R.id.login_tiet_email);
        tietLoginPassword = findViewById(R.id.login_tiet_password);
        btnLogin = findViewById(R.id.btn_login);
        btnResetPassword = findViewById(R.id.btn_reset_password);
        progressBar = findViewById(R.id.progressBar_login);
        fAuth = FirebaseAuth.getInstance();
        //Intent intent = new Intent(this, MainActivity.class);
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
                fStore = FirebaseFirestore.getInstance();

                //authentificate the user
                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Logged in succesfully", Toast.LENGTH_SHORT).show();
                            userID = fAuth.getCurrentUser().getUid();
                            user.setUserId(userID);
                            DocumentReference documentReference = fStore.collection("clients").document(userID);
                            documentReference.get().addOnSuccessListener(document -> {
                                if (document.exists()) {
                                    user.setfName(document.getData().get("fName").toString());
                                    user.setEmail(document.getData().get("email").toString());
                                    user.setPhoneNumber(document.getData().get("phone").toString());
                                    Log.d(TAG, "Utilizator: " + document.getData().get("favourites"));
                                    if(document.getData().get("favourites") != null) {
                                        //phonesCodes = new ArrayList<String> (Arrays.asList(document.getData().get("favourites").toString().split(",")));
                                        phonesCodes = (ArrayList<String>) document.getData().get("favourites");
                                        Log.d(TAG, "Coduri login: " + phonesCodes);
                                        user.setFavouritePhones(phonesCodes);
                                        Log.d(TAG, "Utilizator login: " + user.toString());
                                    }
                                    intent.putExtra(USER_KEY, user);
                                    setResult(RESULT_OK, intent);
                                    finish();
                                } else {
                                    Log.d(TAG, "No such document");
                                }
                            }).addOnFailureListener(e -> {
                                Log.d(TAG, "Error getting document: ", e);
                            });
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });
            }
        });

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=tietLoginEmail.getText().toString().trim();
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(), "Please insert an email above", Toast.LENGTH_SHORT).show();
                } else {
                    fAuth.sendPasswordResetEmail(email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "Password reset email sent", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Failed to send password reset email", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }
}
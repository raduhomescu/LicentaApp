package com.example.licentaapp.fragments;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.licentaapp.MainActivity;
import com.example.licentaapp.R;
import com.example.licentaapp.utils.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {

    private TextView tvFullName;
    private TextView tvEmail;
    private TextView tvPhoneNumber;
    private Button btnProfileLogOut;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private String userID;
    private User user;
    private Button btnDialogEditData;
    private AlertDialog dialog;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment getInstance(User user) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putParcelable("User key", user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user = getArguments().getParcelable("User key");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        initComponents(view);
        return view;
    }
    private void initComponents(View view) {
        if (getContext() != null) {
            tvFullName = view.findViewById(R.id.tv_profile_name);
            tvEmail = view.findViewById(R.id.tv_profile_email);
            tvPhoneNumber = view.findViewById(R.id.tv_profile_phone);
            btnDialogEditData = view.findViewById(R.id.btn_edit_date);

            fAuth = FirebaseAuth.getInstance();
            fStore = FirebaseFirestore.getInstance();
            userID = fAuth.getCurrentUser().getUid();

            DocumentReference documentReference = fStore.collection("clients").document(userID);
            documentReference.get().addOnSuccessListener(document -> {
                if (document.exists()) {
                    tvFullName.setText(document.getData().get("fName").toString());
                    tvEmail.setText(document.getData().get("email").toString());
                    tvPhoneNumber.setText(document.getData().get("phone").toString());
                } else {
                    Log.d(TAG, "No such document");
                }
            }).addOnFailureListener(e -> {
                Log.d(TAG, "Error getting document: ", e);
            });

            ImageView imageView = view.findViewById(R.id.image_view_proflie);

            imageView.setImageResource(R.drawable.ic_baseline_person_2192);

            btnProfileLogOut = view.findViewById(R.id.btn_profile_log_out);
            btnProfileLogOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Map<String, Object> updates = new HashMap<>();
                    if(!user.getFavouritePhones().isEmpty()) {
                        updates.put("favourites", user.getFavouritePhones());
                    } else {
                        updates.put("favourites", new ArrayList<String>());
                    }
                    fStore.collection("clients").document(user.getUserId())
                            .update(updates)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "Favourite phones saved successfully.");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error saving favourite phones.", e);
                                }
                            });

                    fAuth.signOut();
                    Toast.makeText(getContext(), "Logged out", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(view.getContext().getApplicationContext(), MainActivity.class));
                }
            });

            btnDialogEditData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    View dialogEditLayout = getLayoutInflater().inflate(R.layout.edit_account_data_dialog_layout, null);
                    builder.setView(dialogEditLayout);
                    TextInputEditText tietName = dialogEditLayout.findViewById(R.id.tiet_name_dialog_edit);
                    tietName.setText(tvFullName.getText());
                    TextInputEditText tietPhone = dialogEditLayout.findViewById(R.id.tiet_phone_dialog_edit);
                    tietPhone.setText(tvPhoneNumber.getText());
                    Button saveBtn = dialogEditLayout.findViewById(R.id.btn_save_dialog_edit);
                    saveBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (TextUtils.isEmpty(tietName.getText().toString())) {
                                tietName.setError("Full name is required.");
                                return;
                            }
                            if (TextUtils.isEmpty(tietPhone.getText().toString())) {
                                tietPhone.setError("Phone Number is required.");
                                return;
                            }
                            if(tietPhone.getText().toString().length() != 10){
                                tietPhone.setError("Phone Number must be romanian type.");
                                return;
                            }
                            tvFullName.setText(tietName.getText());
                            tvPhoneNumber.setText(tietPhone.getText());

                            DocumentReference documentReference = fStore.collection("clients").document(userID);
                            Map<String, Object> user= new HashMap<>();
                            user.put("email", tvEmail.getText().toString());
                            user.put("fName", tietName.getText().toString());
                            user.put("phone", tietPhone.getText().toString());
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d(TAG, "onSuccess: user Profile is created for "+ userID);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: " + e.toString());
                                }
                            });
                            dialog.dismiss();
                        }
                    });
                    dialog = builder.create();
                    dialog.show();
                }
            });

        }
    }
}
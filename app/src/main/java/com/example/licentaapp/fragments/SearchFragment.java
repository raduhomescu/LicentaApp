package com.example.licentaapp.fragments;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.licentaapp.R;
import com.example.licentaapp.utils.Phone;
import com.example.licentaapp.utils.PhoneAdapter;
import com.example.licentaapp.utils.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchFragment extends Fragment implements PhoneAdapter.OnFavoriteButtonClickListener {
    private ArrayList<Phone> phonesList = new ArrayList<>();
    private ArrayList<Phone> filteredPhoneList = new ArrayList<>();
    private ListView lvPhones;
    private ImageButton imageButton;
    private User user;
    private TextInputEditText tietSearch;
    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment getInstance(ArrayList<Phone> phonesList, User user) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("Phone List Key", phonesList);
        Log.d(TAG, "Phones map sf: " + phonesList);
        args.putParcelable("User key", user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            phonesList= getArguments().getParcelableArrayList("Phone List Key");
            user= getArguments().getParcelable("User key");
            Log.d(TAG, "Phones map sf: " + user);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        initComponents(view);
        return view;
    }

    private void initComponents(View view) {
        lvPhones = view.findViewById(R.id.lv_phones);
        PhoneAdapter adapter = new PhoneAdapter(view.getContext().getApplicationContext(),R.layout.lv_row_item, filteredPhoneList, getLayoutInflater(), user, getActivity());
        adapter.setOnFavoriteButtonClickListener(this);
        lvPhones.setAdapter(adapter);
        tietSearch = view.findViewById(R.id.tiet_search);
        tietSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Filter the allPhones list based on the search query
                filteredPhoneList.clear();
                for (Phone phone : phonesList) {
                    if (s.toString().toLowerCase().contains(phone.getBrand().toLowerCase()) ||
                        phone.getModel().toLowerCase().contains(s.toString().toLowerCase()) ||
                        s.toString().toLowerCase().contains(phone.getPlatform().toLowerCase()) ||
                        s.toString().toLowerCase().contains(String.valueOf(phone.getRam()).toLowerCase()) ||
                        s.toString().toLowerCase().contains(String.valueOf(phone.getStorage()).toLowerCase())
                    ) {
                        filteredPhoneList.add(phone);
                    }
                }

                // Update the adapter with the filtered list
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Do nothing
            }
        });

        imageButton = view.findViewById(R.id.btn_search_sf);
    }

    @Override
    public void onFavoriteButtonClicked(Phone phone) {
        String phoneId = String.valueOf(phone.getuId());
        if (user.getFavouritePhones().contains(phoneId)) {
            user.removeFavoritePhoneId(phoneId);
        } else {
            user.addFavoritePhoneId(phoneId);
        }
    }
}
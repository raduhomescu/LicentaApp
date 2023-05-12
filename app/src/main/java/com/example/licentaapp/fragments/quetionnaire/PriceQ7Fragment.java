package com.example.licentaapp.fragments.quetionnaire;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.licentaapp.R;
import com.example.licentaapp.utils.User;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class PriceQ7Fragment extends Fragment {

    private ArrayList<String> filterList;
    private Fragment currentFragment;
    public static final String USER_KEY = "User key";
    private User user;
    private TextInputEditText minPrice;
    private TextInputEditText maxPrice;
    private String priceRange = null;
    private Button btnFindPhone;

    public static final String FILTER_LIST_KEY = "filter list";
    public PriceQ7Fragment() {
        // Required empty public constructor
    }


    public static PriceQ7Fragment newInstance(ArrayList<String> filterList, User user) {
        PriceQ7Fragment fragment = new PriceQ7Fragment();
        Bundle args = new Bundle();
        args.putStringArrayList(FILTER_LIST_KEY,filterList);
        args.putParcelable(USER_KEY, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            filterList = getArguments().getStringArrayList(FILTER_LIST_KEY);
            user = getArguments().getParcelable(USER_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_price_q7, container, false);
        initComponents(view);
        return view;
    }

    private void initComponents(View view) {
        minPrice = view.findViewById(R.id.tiet_min_price_q7);
        maxPrice = view.findViewById(R.id.tiet_max_price_q7);
        btnFindPhone = view.findViewById(R.id.btn_find_phone);
        btnFindPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String min_text = minPrice.getText().toString();
                String max_text = maxPrice.getText().toString();
                if(!minPrice.getText().toString().isEmpty() && !maxPrice.getText().toString().isEmpty()) {
                    priceRange = minPrice.getText().toString() + "/" + maxPrice.getText().toString();
                    filterList.add(priceRange);
                    Log.d("filter final listtt:", filterList.toString());
                } else {
                    Toast.makeText(view.getContext(), "Please insert minimum and maximum price", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void openFragment(Fragment fragment){
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_main, fragment)
                .commit();
    }
}
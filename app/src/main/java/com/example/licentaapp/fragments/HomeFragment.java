package com.example.licentaapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.example.licentaapp.R;
import com.example.licentaapp.RegisterActivity;
import com.example.licentaapp.fragments.quetionnaire.PreferredPhoneQ1Fragment;
import com.example.licentaapp.utils.Phone;
import com.example.licentaapp.utils.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
    private Button btnFindYourDevice;
    private ArrayList<Phone> phones;
    private ArrayList<String> filterList = new ArrayList<>();
    public static final String FILTER_LIST_KEY = "filter list";
    public static final String USER_KEY = "User key";
    private User user;
    private static final String FILTER_LIST_ITEM = "new filters";
    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment getInstance(ArrayList<Phone> phones, ArrayList<String> filterList, User user) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("phones", phones);
        args.putStringArrayList(FILTER_LIST_KEY,filterList);
        args.putParcelable(USER_KEY, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            phones = getArguments().getParcelableArrayList("phones");
            filterList = getArguments().getStringArrayList(FILTER_LIST_KEY);
            user = getArguments().getParcelable(USER_KEY);
            Log.d("Phones list in home: ", phones.toString());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initComponents(view);
        return view;
    }
    private void initComponents(View view) {
        if (getContext() != null) {
            btnFindYourDevice = view.findViewById(R.id.btn_find_your_device);
            btnFindYourDevice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    filterList.add(FILTER_LIST_ITEM);
                    Log.d("home fragment listtt: ", filterList.toString());
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container_main, PreferredPhoneQ1Fragment.newInstance(filterList, user))
                            .commit();
                }
            });
        }
    }
}
package com.example.licentaapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.licentaapp.R;
import com.example.licentaapp.utils.Phone;
import com.example.licentaapp.utils.PhoneAdapter;
import com.example.licentaapp.utils.User;

import java.util.ArrayList;

public class FavouritesFragment extends Fragment implements PhoneAdapter.OnFavoriteButtonClickListener{
    private ArrayList<Phone> phonesList = new ArrayList<>();
    private ArrayList<Phone> comparePhonesList = new ArrayList<>();
    private ListView lvPhones;
    private User user;
    public FavouritesFragment() {
        // Required empty public constructor
    }

    public static FavouritesFragment getInstance(ArrayList<Phone> phonesList, User user, ArrayList<Phone> comparePhonesList) {
        FavouritesFragment fragment = new FavouritesFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("Phone List Key", phonesList);
        args.putParcelable("User key", user);
        fragment.setArguments(args);
        args.putParcelableArrayList("compare phone list key", comparePhonesList);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            phonesList= getArguments().getParcelableArrayList("Phone List Key");
            user= getArguments().getParcelable("User key");
            comparePhonesList= getArguments().getParcelableArrayList("compare phone list key");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favourites, container, false);
        initComponents(view);
        return view;
    }

    private void initComponents(View view) {
        lvPhones = view.findViewById(R.id.lv_phones_favourites);
        ArrayList<Phone> filteredList = new ArrayList<>();
        for (Phone phone : phonesList) {
            if (user.getFavouritePhones().contains(String.valueOf(phone.getuId()))) {
                filteredList.add(phone);
            }
        }
        PhoneAdapter adapter = new PhoneAdapter(view.getContext().getApplicationContext(),R.layout.lv_row_item, filteredList, getLayoutInflater(), user, getActivity(), comparePhonesList);
        adapter.setOnFavoriteButtonClickListener(this);
        lvPhones.setAdapter(adapter);
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
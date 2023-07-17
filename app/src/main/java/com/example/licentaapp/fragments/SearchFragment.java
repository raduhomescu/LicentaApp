package com.example.licentaapp.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.licentaapp.R;
import com.example.licentaapp.utils.Phone;
import com.example.licentaapp.utils.PhoneAdapter;
import com.example.licentaapp.utils.User;

import java.util.ArrayList;

public class SearchFragment extends Fragment implements PhoneAdapter.OnFavoriteButtonClickListener {
    private ArrayList<Phone> phonesList = new ArrayList<>();
    private ArrayList<Phone> comparePhonesList = new ArrayList<>();
    private ArrayList<Phone> filteredPhoneList = new ArrayList<>();
    private ArrayList<Phone> filteredPhoneList2 = new ArrayList<>();
    private ListView lvPhones;
    private ImageButton imageButton;
    private User user;
    private AutoCompleteTextView aCTVSearch;
    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment getInstance(ArrayList<Phone> phonesList, User user, ArrayList<Phone> comparePhonesList) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("Phone List Key", phonesList);
        args.putParcelable("User key", user);
        args.putParcelableArrayList("compare phone list key", comparePhonesList);
        fragment.setArguments(args);
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
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        initComponents(view);
        return view;
    }

    private void initComponents(View view) {
        lvPhones = view.findViewById(R.id.lv_phones);
        PhoneAdapter adapter = new PhoneAdapter(view.getContext().getApplicationContext(),R.layout.lv_row_item, filteredPhoneList, getLayoutInflater(), user, getActivity(), comparePhonesList);
        adapter.setOnFavoriteButtonClickListener(this);
        lvPhones.setAdapter(adapter);
        aCTVSearch = view.findViewById(R.id.a_c_t_v_search);
        ArrayList<String> suggestions = new ArrayList<>();
        for (Phone phone : phonesList) {
            if (phone.getBrand().equals("Apple")) {
                if(!suggestions.contains(phone.getModel())) {
                    suggestions.add(phone.getModel());
                }
            } else {
                if(!suggestions.contains(phone.getBrand() + " " + phone.getModel())) {
                    suggestions.add(phone.getBrand() + " " + phone.getModel());
                }
            }
        }

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getContext().getApplicationContext(), R.layout.lw_row_item_specs, R.id.row_item_specs_tv_titlu, suggestions);
        aCTVSearch.setAdapter(adapter1);
        aCTVSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                filteredPhoneList.clear();
                filteredPhoneList2.clear();
                for(Phone phone : phonesList) {
                    if (phone.getBrand().equals("Apple")) {
                        if (aCTVSearch.getText().toString().equals(phone.getModel())) {
                            filteredPhoneList.add(phone);
                        } else if (phone.getModel().contains(aCTVSearch.getText().toString().split("\\s+", 2)[0])){
                            filteredPhoneList2.add(phone);
                        }
                    } else {
                        if(aCTVSearch.getText().toString().equals(phone.getBrand() + " " + phone.getModel())) {
                            filteredPhoneList.add(phone);
                        } else if (phone.getBrand().equals(aCTVSearch.getText().toString().split("\\s+", 2)[0])){
                            filteredPhoneList2.add(phone);
                        }
                    }
                }
                filteredPhoneList.addAll(filteredPhoneList2);
                adapter.notifyDataSetChanged();
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
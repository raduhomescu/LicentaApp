package com.example.licentaapp.fragments.quetionnaire;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.licentaapp.R;

import java.util.ArrayList;

public class PriceQ7Fragment extends Fragment {

    private ArrayList<String> filterList;
    private Fragment currentFragment;
    public static final String FILTER_LIST_KEY = "filter list";
    public PriceQ7Fragment() {
        // Required empty public constructor
    }


    public static PriceQ7Fragment newInstance(ArrayList<String> filterList) {
        PriceQ7Fragment fragment = new PriceQ7Fragment();
        Bundle args = new Bundle();
        args.putStringArrayList(FILTER_LIST_KEY,filterList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            filterList = getArguments().getStringArrayList(FILTER_LIST_KEY);
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
    }

    private void openFragment(Fragment fragment){
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_main, fragment)
                .commit();
    }
}
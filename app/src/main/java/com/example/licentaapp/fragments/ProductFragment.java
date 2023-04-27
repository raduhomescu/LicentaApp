package com.example.licentaapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.licentaapp.R;

public class ProductFragment extends Fragment {

    public ProductFragment() {
        // Required empty public constructor
    }

    public static ProductFragment getInstance() {
        ProductFragment fragment = new ProductFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_product, container, false);
        initComponents(view);
        return view;
    }

    private void initComponents(View view) {
    }
}
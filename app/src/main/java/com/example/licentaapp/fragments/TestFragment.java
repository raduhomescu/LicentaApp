package com.example.licentaapp.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.licentaapp.R;
import com.squareup.picasso.Picasso;

public class TestFragment extends Fragment {
    private ImageView imageView;

    public TestFragment() {
        // Required empty public constructor
    }


    public static TestFragment newInstance() {
        TestFragment fragment = new TestFragment();
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
        View view = inflater.inflate(R.layout.fragment_test, container, false);
        String imageUrl = "https://www.flanco.ro/media/catalog/product/cache/e53d4628cd85067723e6ea040af871ec/t/e/telefon_xiaomi_redmi_9a_32gb_dual_sim.jpg";
        imageView = view.findViewById(R.id.imageView);
        Picasso.get()
                .load(imageUrl)
                .into(imageView);
        return view;
    }
}
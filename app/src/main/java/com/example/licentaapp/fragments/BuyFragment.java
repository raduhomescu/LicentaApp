package com.example.licentaapp.fragments;

import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.licentaapp.R;
import com.example.licentaapp.utils.Phone;

import com.google.android.libraries.places.api.Places;

public class BuyFragment extends Fragment {
    public static final String PHONE_KEY = "Phone key";
    private Phone phone;
    ImageButton btnLogoAltex;
    ImageButton btnLogoEmag;
    ImageButton btnLogoFlanco;
    ImageButton btnMapsAltex;
    ImageButton btnMapsEmag;
    ImageButton btnMapsFlanco;
    private Fragment currentFragment;

    public BuyFragment() {
        // Required empty public constructor
    }

    public static BuyFragment newInstance(Phone phone) {
        BuyFragment fragment = new BuyFragment();
        Bundle args = new Bundle();
        args.putParcelable(PHONE_KEY, phone);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Places.initialize(getContext().getApplicationContext(), "AIzaSyCaqUWylYAOuNcuO8DKhrWuwdJrZpx6llc");
        if (getArguments() != null) {
            phone = getArguments().getParcelable("Phone key");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_buy, container, false);
        initComponents(view);
        return view;
    }

    private void initComponents(View view) {
        btnLogoAltex = view.findViewById(R.id.btn_logo_altex);
        btnLogoEmag = view.findViewById(R.id.btn_logo_emag);
        btnLogoFlanco = view.findViewById(R.id.btn_logo_flanco);
        btnMapsAltex = view.findViewById(R.id.btn_maps_altex);
        btnMapsEmag = view.findViewById(R.id.btn_maps_emag);
        btnMapsFlanco = view.findViewById(R.id.btn_maps_flanco);
        btnLogoAltex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = phone.getLinkAltex(); // Replace with your desired URL

                // Create an intent with ACTION_VIEW and the URL
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));

                // Check if there is a web browser available to handle the intent
                if (intent.resolveActivity(requireActivity().getPackageManager()) != null) {
                    // Start the activity with the intent
                    startActivity(intent);
                } else {
                    // Handle the case where no web browser is available
                    Toast.makeText(view.getContext().getApplicationContext(), "No web browser found", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnLogoEmag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = phone.getLinkEmag(); // Replace with your desired URL

                // Create an intent with ACTION_VIEW and the URL
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));

                // Check if there is a web browser available to handle the intent
                if (intent.resolveActivity(requireActivity().getPackageManager()) != null) {
                    // Start the activity with the intent
                    startActivity(intent);
                } else {
                    // Handle the case where no web browser is available
                    Toast.makeText(view.getContext().getApplicationContext(), "No web browser found", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnLogoFlanco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = phone.getLinkFlanco(); // Replace with your desired URL

                // Create an intent with ACTION_VIEW and the URL
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));

                // Check if there is a web browser available to handle the intent
                if (intent.resolveActivity(requireActivity().getPackageManager()) != null) {
                    // Start the activity with the intent
                    startActivity(intent);
                } else {
                    // Handle the case where no web browser is available
                    Toast.makeText(view.getContext().getApplicationContext(), "No web browser found", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnMapsAltex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentFragment = MapFragment.newInstance("Altex,Media Galaxy");
                openFragment(currentFragment);
            }
        });

        btnMapsEmag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentFragment = MapFragment.newInstance("eMAG");
                openFragment(currentFragment);
            }
        });

        btnMapsFlanco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentFragment = MapFragment.newInstance("Flanco");
                openFragment(currentFragment);
            }
        });

    }
    private void openFragment(Fragment fragment){
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_main, fragment)
                .addToBackStack(null)
                .commit();
    }
}
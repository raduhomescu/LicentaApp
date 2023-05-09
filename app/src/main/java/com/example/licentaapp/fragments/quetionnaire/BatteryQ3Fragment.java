package com.example.licentaapp.fragments.quetionnaire;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.licentaapp.R;

import java.util.ArrayList;

public class BatteryQ3Fragment extends Fragment {
    private ArrayList<String> filterList;
    private Fragment currentFragment;
    public BatteryQ3Fragment() {
        // Required empty public constructor
    }


    public static BatteryQ3Fragment newInstance(ArrayList<String> filterList) {
        BatteryQ3Fragment fragment = new BatteryQ3Fragment();
        Bundle args = new Bundle();
        args.putStringArrayList("filter list",filterList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            filterList = getArguments().getStringArrayList("filter list");
            Log.d("filter listtt:", filterList.toString());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_battery_q3, container, false);
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
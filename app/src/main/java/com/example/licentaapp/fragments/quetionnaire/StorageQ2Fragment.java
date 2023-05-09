package com.example.licentaapp.fragments.quetionnaire;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.licentaapp.R;

import java.util.ArrayList;

public class StorageQ2Fragment extends Fragment {

    private ArrayList<String> filterList;
    private ImageButton btnInfoQ2;
    public StorageQ2Fragment() {
        // Required empty public constructor
    }
    
    public static StorageQ2Fragment newInstance(ArrayList<String> filterList) {
        StorageQ2Fragment fragment = new StorageQ2Fragment();
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_storage_q2, container, false);
        initComponents(view);
        return view;
    }

    private void initComponents(View view) {
        btnInfoQ2 = view.findViewById(R.id.btn_info_q2);
        btnInfoQ2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage(R.string.info_message_q1)
                        .setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
    }
}
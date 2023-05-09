package com.example.licentaapp.fragments.quetionnaire;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.licentaapp.R;

import java.util.ArrayList;

public class StorageQ2Fragment extends Fragment {

    private ArrayList<String> filterList;
    private Fragment currentFragment;
    private ImageButton btnInfoQ2;
    private Button btn64GB;
    private Button btn128GB;
    private Button btn256GB;
    private Button btn512GB;
    private Button btn1024GB;

    private static final String VAL_64 = "64";
    private static final String VAL_128 = "128";
    private static final String VAL_256 = "256";
    private static final String VAL_512 = "512";
    private static final String VAL_1024 = "1024";


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
            Log.d("filter listtt:", filterList.toString());
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
        btn64GB = view.findViewById(R.id.btn_64_gb);
        btn128GB = view.findViewById(R.id.btn_128_gb);
        btn256GB = view.findViewById(R.id.btn_256_gb);
        btn512GB = view.findViewById(R.id.btn_512_gb);
        btn1024GB = view.findViewById(R.id.btn_1024_gb);
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

        btn64GB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterList.add(VAL_64);
                currentFragment = BatteryQ3Fragment.newInstance(filterList);
                openFragment(currentFragment);
            }
        });

        btn128GB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterList.add(VAL_128);
                currentFragment = BatteryQ3Fragment.newInstance(filterList);
                openFragment(currentFragment);
            }
        });

        btn256GB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterList.add(VAL_256);
                currentFragment = BatteryQ3Fragment.newInstance(filterList);
                openFragment(currentFragment);
            }
        });

        btn512GB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterList.add(VAL_512);
                currentFragment = BatteryQ3Fragment.newInstance(filterList);
                openFragment(currentFragment);
            }
        });

        btn1024GB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterList.add(VAL_1024);
                currentFragment = BatteryQ3Fragment.newInstance(filterList);
                openFragment(currentFragment);
            }
        });
    }

    private void openFragment(Fragment fragment){
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_main, fragment)
                .commit();
    }
}
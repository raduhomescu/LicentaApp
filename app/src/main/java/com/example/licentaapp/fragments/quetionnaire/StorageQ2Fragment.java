package com.example.licentaapp.fragments.quetionnaire;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.licentaapp.R;
import com.example.licentaapp.utils.Phone;
import com.example.licentaapp.utils.User;

import java.util.ArrayList;

public class StorageQ2Fragment extends Fragment {
    AlertDialog dialog;
    private ArrayList<String> filterList;
    private Fragment currentFragment;
    private ImageButton btnInfoQ2;
    private Button btn64GB;
    private Button btn128GB;
    private Button btn256GB;
    private Button btn512GB;
    private Button btn1024GB;
    private ArrayList<Phone> comparePhones = new ArrayList<>();
    private static final String VAL_64 = "64";
    private static final String VAL_128 = "128";
    private static final String VAL_256 = "256";
    private static final String VAL_512 = "512";
    private static final String VAL_1024 = "1024";
    public static final String FILTER_LIST_KEY = "filter list";
    public static final String USER_KEY = "User key";
    private User user;


    public StorageQ2Fragment() {
        // Required empty public constructor
    }
    
    public static StorageQ2Fragment newInstance(ArrayList<String> filterList, User user, ArrayList<Phone> comparePhonesList) {
        StorageQ2Fragment fragment = new StorageQ2Fragment();
        Bundle args = new Bundle();
        args.putStringArrayList(FILTER_LIST_KEY,filterList);
        args.putParcelable(USER_KEY, user);
        args.putParcelableArrayList("compare phones", comparePhonesList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            filterList = getArguments().getStringArrayList(FILTER_LIST_KEY);
            user = getArguments().getParcelable(USER_KEY);
            comparePhones = getArguments().getParcelableArrayList("compare phones");
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
        btn64GB = view.findViewById(R.id.btn_64_gb);
        btn128GB = view.findViewById(R.id.btn_128_gb);
        btn256GB = view.findViewById(R.id.btn_256_gb);
        btn512GB = view.findViewById(R.id.btn_512_gb);
        btn1024GB = view.findViewById(R.id.btn_1024_gb);
        btnInfoQ2 = view.findViewById(R.id.btn_info_q2);
        btnInfoQ2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View customLayout = getLayoutInflater().inflate(R.layout.custom_dialog_layout, null);
                builder.setView(customLayout);
                TextView messageTextView = customLayout.findViewById(R.id.alert_message);
                messageTextView.setText(R.string.info_message_q2);
                Button okButton = customLayout.findViewById(R.id.alert_ok_button);
                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog = builder.create();
                dialog.show();

            }
        });

        btn64GB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterList.add(VAL_64);
                currentFragment = BatteryQ3Fragment.newInstance(filterList, user, comparePhones);
                openFragment(currentFragment);
            }
        });

        btn128GB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterList.add(VAL_128);
                currentFragment = BatteryQ3Fragment.newInstance(filterList, user, comparePhones);
                openFragment(currentFragment);
            }
        });

        btn256GB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterList.add(VAL_256);
                currentFragment = BatteryQ3Fragment.newInstance(filterList, user, comparePhones);
                openFragment(currentFragment);
            }
        });

        btn512GB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterList.add(VAL_512);
                currentFragment = BatteryQ3Fragment.newInstance(filterList, user, comparePhones);
                openFragment(currentFragment);
            }
        });

        btn1024GB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterList.add(VAL_1024);
                currentFragment = BatteryQ3Fragment.newInstance(filterList, user, comparePhones);
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
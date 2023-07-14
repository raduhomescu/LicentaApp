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

public class PrimaryCameraQ6Fragment extends Fragment {

    private ArrayList<String> filterList;
    private Fragment currentFragment;
    AlertDialog dialog;
    private ImageButton btnInfoQ6;
    public static final String FILTER_LIST_KEY = "filter list";
    public static final String USER_KEY = "User key";
    private User user;
    private Button btnQ6Irrelevant;
    private Button btnQ6MediumQuality;
    private Button btnQ6HighQuality;
    private Button btnQ6Professional;
    private ArrayList<Phone> comparePhones = new ArrayList<>();

    public PrimaryCameraQ6Fragment() {
        // Required empty public constructor
    }

    public static PrimaryCameraQ6Fragment newInstance(ArrayList<String> filterList, User user, ArrayList<Phone> comparePhonesList) {
        PrimaryCameraQ6Fragment fragment = new PrimaryCameraQ6Fragment();
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
        View view = inflater.inflate(R.layout.fragment_primary_camera_q6, container, false);
        initComponents(view);
        return view;
    }

    private void initComponents(View view) {
        btnQ6Irrelevant = view.findViewById(R.id.btn_q6_irrelevant);
        btnQ6MediumQuality = view.findViewById(R.id.btn_q6_medium_quality);
        btnQ6HighQuality = view.findViewById(R.id.btn_q6_high_quality);
        btnQ6Professional = view.findViewById(R.id.btn_q6_professional);
        btnInfoQ6 = view.findViewById(R.id.btn_info_q6);
        btnInfoQ6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View customLayout = getLayoutInflater().inflate(R.layout.custom_dialog_layout, null);
                builder.setView(customLayout);
                TextView messageTextView = customLayout.findViewById(R.id.alert_message);
                messageTextView.setText(R.string.info_message_q6);
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

        btnQ6Irrelevant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterList.add(getString(R.string.irrelevant));
                currentFragment = PriceQ7Fragment.newInstance(filterList, user, comparePhones);
                openFragment(currentFragment);
            }
        });

        btnQ6MediumQuality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterList.add(getString(R.string.medium_quality));
                currentFragment = PriceQ7Fragment.newInstance(filterList, user, comparePhones);
                openFragment(currentFragment);
            }
        });

        btnQ6HighQuality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterList.add(getString(R.string.high_quality));
                currentFragment = PriceQ7Fragment.newInstance(filterList, user, comparePhones);
                openFragment(currentFragment);
            }
        });
        btnQ6Professional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterList.add(getString(R.string.professional));
                currentFragment = PriceQ7Fragment.newInstance(filterList, user, comparePhones);
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
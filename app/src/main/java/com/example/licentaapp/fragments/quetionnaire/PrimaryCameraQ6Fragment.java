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
    private Button btn0_50Mpx;
    private Button btn50Mpx;
    private Button btn64Mpx;
    private Button btn64moreMpx;

    private static final String VAL_0_50_MPX = "30";
    private static final String VAL_50_MPX = "50";
    private static final String VAL_64_MPX = "64";
    private static final String VAL_64_MORE_MPX = "100";
    public PrimaryCameraQ6Fragment() {
        // Required empty public constructor
    }

    public static PrimaryCameraQ6Fragment newInstance(ArrayList<String> filterList, User user) {
        PrimaryCameraQ6Fragment fragment = new PrimaryCameraQ6Fragment();
        Bundle args = new Bundle();
        args.putStringArrayList(FILTER_LIST_KEY,filterList);
        args.putParcelable(USER_KEY, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            filterList = getArguments().getStringArrayList(FILTER_LIST_KEY);
            user = getArguments().getParcelable(USER_KEY);
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
        btn0_50Mpx = view.findViewById(R.id.btn_0_50_mpx);
        btn50Mpx = view.findViewById(R.id.btn_50_mpx);
        btn64Mpx = view.findViewById(R.id.btn_64_mpx);
        btn64moreMpx = view.findViewById(R.id.btn_64_more_mpx);
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

        btn0_50Mpx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterList.add(VAL_0_50_MPX);
                currentFragment = PriceQ7Fragment.newInstance(filterList, user);
                openFragment(currentFragment);
            }
        });

        btn50Mpx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterList.add(VAL_50_MPX);
                currentFragment = PriceQ7Fragment.newInstance(filterList, user);
                openFragment(currentFragment);
            }
        });

        btn64Mpx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterList.add(VAL_64_MPX);
                currentFragment = PriceQ7Fragment.newInstance(filterList, user);
                openFragment(currentFragment);
            }
        });
        btn64moreMpx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterList.add(VAL_64_MORE_MPX);
                currentFragment = PriceQ7Fragment.newInstance(filterList, user);
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
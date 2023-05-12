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


public class RamQ5Fragment extends Fragment {

    private ArrayList<String> filterList;
    private Fragment currentFragment;
    AlertDialog dialog;
    private ImageButton btnInfoQ5;
    public static final String FILTER_LIST_KEY = "filter list";
    public static final String USER_KEY = "User key";
    private User user;
    private Button btn4GB;
    private Button btn6GB;
    private Button btn8GB;
    private Button btn12GB;
    private static final String VAL_4 = "4";
    private static final String VAL_6 = "6";
    private static final String VAL_8 = "8";
    private static final String VAL_12 = "12";

    public RamQ5Fragment() {
        // Required empty public constructor
    }

    public static RamQ5Fragment newInstance(ArrayList<String> filterList, User user) {
        RamQ5Fragment fragment = new RamQ5Fragment();
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
        View view = inflater.inflate(R.layout.fragment_ram_q5, container, false);
        initComponents(view);
        return view;
    }

    private void initComponents(View view) {
        btn4GB = view.findViewById(R.id.btn_ram_4gb);
        btn6GB = view.findViewById(R.id.btn_ram_6gb);
        btn8GB = view.findViewById(R.id.btn_ram_8gb);
        btn12GB = view.findViewById(R.id.btn_ram_12gb);
        btnInfoQ5 = view.findViewById(R.id.btn_info_q5);
        btnInfoQ5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View customLayout = getLayoutInflater().inflate(R.layout.custom_dialog_layout, null);
                builder.setView(customLayout);
                TextView messageTextView = customLayout.findViewById(R.id.alert_message);
                messageTextView.setText(R.string.info_message_q5);
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

        btn4GB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterList.add(VAL_4);
                currentFragment = PrimaryCameraQ6Fragment.newInstance(filterList, user);
                openFragment(currentFragment);
            }
        });

        btn6GB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterList.add(VAL_6);
                currentFragment = PrimaryCameraQ6Fragment.newInstance(filterList, user);
                openFragment(currentFragment);
            }
        });
        btn8GB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterList.add(VAL_8);
                currentFragment = PrimaryCameraQ6Fragment.newInstance(filterList, user);
                openFragment(currentFragment);
            }
        });

        btn12GB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterList.add(VAL_12);
                currentFragment = PrimaryCameraQ6Fragment.newInstance(filterList, user);
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
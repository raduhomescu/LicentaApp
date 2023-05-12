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

public class ResolutionQ4Fragment extends Fragment {

    private ArrayList<String> filterList;
    private Fragment currentFragment;
    AlertDialog dialog;
    private ImageButton btnInfoQ4;
    private Button btnHD;
    private Button btnFHD;
    private Button btnQHD;
    private Button btn4K;
    public static final String FILTER_LIST_KEY = "filter list";
    public static final String USER_KEY = "User key";
    private User user;
    private static final String VAL_HD = "720";
    private static final String VAL_FHD = "1080";
    private static final String VAL_QHD = "1440";
    private static final String VAL_4K = "2160";

    public ResolutionQ4Fragment() {
        // Required empty public constructor
    }

    public static ResolutionQ4Fragment newInstance(ArrayList<String> filterList, User user) {
        ResolutionQ4Fragment fragment = new ResolutionQ4Fragment();
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
        View view = inflater.inflate(R.layout.fragment_resolution_q4, container, false);
        initComponents(view);
        return view;
    }

    private void initComponents(View view) {
        btnHD = view.findViewById(R.id.btn_hd);
        btnFHD = view.findViewById(R.id.btn_fhd);
        btnQHD = view.findViewById(R.id.btn_qhd);
        btn4K = view.findViewById(R.id.btn_4k);
        btnInfoQ4 = view.findViewById(R.id.btn_info_q4);
        btnInfoQ4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View customLayout = getLayoutInflater().inflate(R.layout.custom_dialog_layout, null);
                builder.setView(customLayout);
                TextView messageTextView = customLayout.findViewById(R.id.alert_message);
                messageTextView.setText(R.string.info_message_q4);
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
        btnHD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterList.add(VAL_HD);
                currentFragment = RamQ5Fragment.newInstance(filterList, user);
                openFragment(currentFragment);
            }
        });
        btnFHD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterList.add(VAL_FHD);
                currentFragment = RamQ5Fragment.newInstance(filterList, user);
                openFragment(currentFragment);
            }
        });
        btnQHD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterList.add(VAL_QHD);
                currentFragment = RamQ5Fragment.newInstance(filterList, user);
                openFragment(currentFragment);
            }
        });
        btn4K.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterList.add(VAL_4K);
                currentFragment = RamQ5Fragment.newInstance(filterList, user);
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
package com.example.licentaapp.fragments.quetionnaire;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.licentaapp.R;
import com.example.licentaapp.utils.User;

import java.util.ArrayList;

public class BatteryQ3Fragment extends Fragment {
    private ArrayList<String> filterList;
    private Fragment currentFragment;
    AlertDialog dialog;
    private ImageButton btnInfoQ3;
    private Button btn3000mAh;
    private Button btn4000mAh;
    private Button btn5000mAh;
    private Button btn6000mAh;
    public static final String USER_KEY = "User key";
    private User user;
    private static final String VAL_3000 = "3000";
    private static final String VAL_4000 = "4000";
    private static final String VAL_5000 = "5000";
    private static final String VAL_6000 = "6000";


    public static final String FILTER_LIST_KEY = "filter list";
    public BatteryQ3Fragment() {
        // Required empty public constructor
    }


    public static BatteryQ3Fragment newInstance(ArrayList<String> filterList, User user) {
        BatteryQ3Fragment fragment = new BatteryQ3Fragment();
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
        btn3000mAh = view.findViewById(R.id.btn_3000_mah);
        btn4000mAh = view.findViewById(R.id.btn_4000_mah);
        btn5000mAh = view.findViewById(R.id.btn_5000_mah);
        btn6000mAh = view.findViewById(R.id.btn_6000_mah);
        btnInfoQ3 = view.findViewById(R.id.btn_info_q3);
        btnInfoQ3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View customLayout = getLayoutInflater().inflate(R.layout.custom_dialog_layout, null);
                builder.setView(customLayout);
                TextView messageTextView = customLayout.findViewById(R.id.alert_message);
                messageTextView.setText(R.string.info_message_q3);
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

        btn3000mAh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterList.add(VAL_3000);
                currentFragment = ResolutionQ4Fragment.newInstance(filterList, user);
                openFragment(currentFragment);
            }
        });

        btn4000mAh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterList.add(VAL_4000);
                currentFragment = ResolutionQ4Fragment.newInstance(filterList, user);
                openFragment(currentFragment);
            }
        });

        btn5000mAh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterList.add(VAL_5000);
                currentFragment = ResolutionQ4Fragment.newInstance(filterList, user);
                openFragment(currentFragment);
            }
        });

        btn6000mAh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterList.add(VAL_6000);
                currentFragment = ResolutionQ4Fragment.newInstance(filterList, user);
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
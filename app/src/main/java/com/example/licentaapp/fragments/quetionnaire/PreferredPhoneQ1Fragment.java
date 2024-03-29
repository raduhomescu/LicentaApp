package com.example.licentaapp.fragments.quetionnaire;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.licentaapp.R;
import com.example.licentaapp.utils.Phone;
import com.example.licentaapp.utils.User;

import java.util.ArrayList;

public class PreferredPhoneQ1Fragment extends Fragment {
    private ArrayList<String> filterList = new ArrayList<>();
    private ImageButton btn_apple_logo;
    private ImageButton btn_samsung_logo;
    private ImageButton btn_xiaomi_logo;
    private ImageButton btn_google_logo;
    private ImageButton btn_huawei_logo;
    private ImageButton btn_onePlus_logo;
    private Fragment currentFragment;
    public static final String FILTER_LIST_KEY = "filter list";
    public static final String USER_KEY = "User key";
    private ArrayList<Phone> comparePhones = new ArrayList<>();
    private User user;

    public PreferredPhoneQ1Fragment() {
        // Required empty public constructor
    }

    public static PreferredPhoneQ1Fragment newInstance(ArrayList<String> filterList, User user, ArrayList<Phone> comparePhonesList) {
        PreferredPhoneQ1Fragment fragment = new PreferredPhoneQ1Fragment();
        Bundle args = new Bundle();
        args.putStringArrayList(FILTER_LIST_KEY, filterList);
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
        View view = inflater.inflate(R.layout.fragment_preferred_phone_q1, container, false);
        initComponents(view);
        return view;
    }

    private void initComponents(View view) {
        btn_apple_logo = view.findViewById(R.id.apple_logo);
        btn_samsung_logo = view.findViewById(R.id.samsung_logo);
        btn_xiaomi_logo = view.findViewById(R.id.xiaomi_logo);
        btn_google_logo = view.findViewById(R.id.google_logo);
        btn_huawei_logo = view.findViewById(R.id.huawei_logo);
        btn_onePlus_logo = view.findViewById(R.id.onePlus_logo);

        btn_apple_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterList.add(getString(R.string.ios));
                currentFragment = StorageQ2Fragment.newInstance(filterList, user, comparePhones);
                openFragment(currentFragment);
            }
        });
        btn_samsung_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterList.add(getString(R.string.android));
                currentFragment = StorageQ2Fragment.newInstance(filterList, user, comparePhones);
                openFragment(currentFragment);
            }
        });
        btn_xiaomi_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterList.add(getString(R.string.android));
                currentFragment = StorageQ2Fragment.newInstance(filterList, user, comparePhones);
                openFragment(currentFragment);
            }
        });
        btn_google_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterList.add(getString(R.string.android));
                currentFragment = StorageQ2Fragment.newInstance(filterList, user, comparePhones);
                openFragment(currentFragment);
            }
        });
        btn_huawei_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterList.add(getString(R.string.emui_huawei));
                currentFragment = StorageQ2Fragment.newInstance(filterList, user, comparePhones);
                openFragment(currentFragment);
            }
        });
        btn_onePlus_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterList.add(getString(R.string.oneplus));
                currentFragment = StorageQ2Fragment.newInstance(filterList, user, comparePhones);
                openFragment(currentFragment);
            }
        });
    }

    private void openFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_main, fragment)
                .commit();
    }
}
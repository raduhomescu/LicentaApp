package com.example.licentaapp.fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.licentaapp.LoginActivity;
import com.example.licentaapp.MainActivity;
import com.example.licentaapp.R;
import com.example.licentaapp.RegisterActivity;
import com.example.licentaapp.utils.Phone;
import com.example.licentaapp.utils.User;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class AccountFragment extends Fragment {
    private Button btn_spre_login;
    private Button btn_spre_register;
    private FirebaseAuth fAuth;
    private User user;

    private ArrayList<Phone> phones;
    private ArrayList<String> filterList = new ArrayList<>();
    public static final String FILTER_LIST_KEY = "filter list";
    public static final String USER_KEY = "User key";
    private ActivityResultLauncher<Intent> loginLauncher;
    private ActivityResultLauncher<Intent> registerLauncher;

    public AccountFragment() {
        // Required empty public constructor
    }

    public static AccountFragment getInstance(ArrayList<Phone> phones, ArrayList<String> filterList, User user) {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("phones", phones);
        args.putStringArrayList(FILTER_LIST_KEY,filterList);
        args.putParcelable(USER_KEY, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            phones = getArguments().getParcelableArrayList("phones");
            filterList = getArguments().getStringArrayList(FILTER_LIST_KEY);
            user= getArguments().getParcelable("User key");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        initComponents(view);
        loginLauncher = registerLoginLauncher();
        registerLauncher = registerRegisterLauncher();
        return view;
    }

    private void initComponents(View view) {
        if (getContext() != null) {
            //navigationView = view.findViewById(R.id.bottom_navigation);
            fAuth = FirebaseAuth.getInstance();
            btn_spre_login=view.findViewById(R.id.btn_login_page);
            btn_spre_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(view.getContext().getApplicationContext(), LoginActivity.class);
                    loginLauncher.launch(intent);

                }
            });

            btn_spre_register=view.findViewById(R.id.btn_register_page);
            btn_spre_register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(view.getContext().getApplicationContext(), RegisterActivity.class);
                    registerLauncher.launch(intent);
                }
            });
        }
    }

    private ActivityResultLauncher<Intent> registerLoginLauncher() {
        ActivityResultCallback<ActivityResult> callback =
                getLoginCallback();
        return registerForActivityResult(
                new ActivityResultContracts
                        .StartActivityForResult(),
                callback);
    }

    private ActivityResultCallback<ActivityResult> getLoginCallback() {
        return new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {

                if (result.getResultCode() == RESULT_OK &&
                        result.getData() != null) {
                    //user = result.getData().getParcelableExtra(LoginActivity.USER_KEY);
                    MainActivity mainActivity = (MainActivity) getActivity();
                    mainActivity.handleActivityResult(result.getData());
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container_main,ProfileFragment.getInstance(user))
                            .commit();

                }
            }
        };
    }
    private ActivityResultLauncher<Intent> registerRegisterLauncher() {
        ActivityResultCallback<ActivityResult> callback =
                getRegisterCallback();
        return registerForActivityResult(
                new ActivityResultContracts
                        .StartActivityForResult(),
                callback);
    }

    private ActivityResultCallback<ActivityResult> getRegisterCallback() {
        return new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {

                if (result.getResultCode() == RESULT_OK &&
                        result.getData() != null) {
                    //user = result.getData().getParcelableExtra(LoginActivity.USER_KEY);
                    MainActivity mainActivity = (MainActivity) getActivity();
                    mainActivity.handleRegister();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container_main,ProfileFragment.getInstance(user))
                            .commit();

                }
            }
        };
    }
}
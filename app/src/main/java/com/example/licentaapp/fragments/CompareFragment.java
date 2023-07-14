package com.example.licentaapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.licentaapp.R;
import com.example.licentaapp.utils.Phone;
import com.example.licentaapp.utils.SpecAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CompareFragment extends Fragment {
    private ArrayList<Phone> comparePhonesList = new ArrayList<>();
    private ImageView compFragImage;
    private ImageView compFragImage2;
    private TextView compFragTitle;
    private TextView compFragPrice;
    private TextView compFragSpec1, compFragSpec2, compFragSpec3, compFragSpec4, compFragSpec5, compFragSpec6,
            compFragSpec7,compFragSpec8, compFragSpec9, compFragSpec10, compFragSpec11, compFragSpec12;
    private TextView compFragSpec1_2, compFragSpec2_2, compFragSpec3_2, compFragSpec4_2, compFragSpec5_2, compFragSpec6_2,
            compFragSpec7_2, compFragSpec8_2, compFragSpec9_2, compFragSpec10_2, compFragSpec11_2, compFragSpec12_2;
    private TextView compFragTitle2;
    private TextView compFragPrice2;
    private Button btnDeletePhone1;
    private Button btnDeletePhone2;

    public CompareFragment() {
        // Required empty public constructor
    }

    public static CompareFragment newInstance(ArrayList<Phone> comparePhonesList) {
        CompareFragment fragment = new CompareFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("lista key", comparePhonesList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            comparePhonesList = getArguments().getParcelableArrayList("lista key");
            Log.d("lista de comparat", comparePhonesList.toString());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_compare, container, false);
        initComponents(view);
        return view;
    }

    private void initComponents(View view) {
        btnDeletePhone1 = view.findViewById(R.id.btn_delete_phone_1);
        compFragImage = view.findViewById(R.id.comp_frag_photo);
        compFragTitle = view.findViewById(R.id.comp_frag_title);
        compFragPrice = view.findViewById(R.id.comp_frag_price);
        Picasso.get()
                .load(comparePhonesList.get(0).getLink_imagine())
                .into(compFragImage);
        compFragTitle.setText(comparePhonesList.get(0).getBrand()+ " " + comparePhonesList.get(0).getModel());
        compFragPrice.setText(view.getContext().getString(R.string.ro_item_pret_completation,String.valueOf(comparePhonesList.get(0).getPrice())));
        addPhoneSpecs(comparePhonesList.get(0), view);

        btnDeletePhone2 = view.findViewById(R.id.btn_delete_phone_2);
        compFragTitle2 = view.findViewById(R.id.comp_frag_title_2);
        compFragPrice2 = view.findViewById(R.id.comp_frag_price_2);
        compFragImage2 = view.findViewById(R.id.comp_frag_photo_2);
        Picasso.get()
                .load(comparePhonesList.get(1).getLink_imagine())
                .into(compFragImage2);
        compFragTitle2.setText(comparePhonesList.get(1).getBrand()+ " " + comparePhonesList.get(1).getModel());
        compFragPrice2.setText(view.getContext().getString(R.string.ro_item_pret_completation,String.valueOf(comparePhonesList.get(1).getPrice())));
        addPhoneSpecs2(comparePhonesList.get(1), view);

        btnDeletePhone1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comparePhonesList.remove(0);
                Toast.makeText(view.getContext(), "Phone deleted.", Toast.LENGTH_SHORT).show();
            }
        });
        btnDeletePhone2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comparePhonesList.remove(1);
                Toast.makeText(view.getContext(), "Phone deleted.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addPhoneSpecs(Phone phone, View view) {
        compFragSpec1 = view.findViewById(R.id.comp_frag_spec_1);
        compFragSpec2 = view.findViewById(R.id.comp_frag_spec_2);
        compFragSpec3 = view.findViewById(R.id.comp_frag_spec_3);
        compFragSpec4 = view.findViewById(R.id.comp_frag_spec_4);
        compFragSpec5 = view.findViewById(R.id.comp_frag_spec_5);
        compFragSpec6 = view.findViewById(R.id.comp_frag_spec_6);
        compFragSpec7 = view.findViewById(R.id.comp_frag_spec_7);
        compFragSpec8 = view.findViewById(R.id.comp_frag_spec_8);
        compFragSpec9 = view.findViewById(R.id.comp_frag_spec_9);
        compFragSpec10 = view.findViewById(R.id.comp_frag_spec_10);
        compFragSpec11 = view.findViewById(R.id.comp_frag_spec_11);
        compFragSpec12 = view.findViewById(R.id.comp_frag_spec_12);
        compFragSpec1.setText(view.getContext().getString(R.string.spec_platform, phone.getPlatform()));
        compFragSpec2.setText(view.getContext().getString(R.string.spec_colour, phone.getColour()));
        compFragSpec3.setText(view.getContext().getString(R.string.spec_storage, String.valueOf(phone.getStorage())));
        compFragSpec4.setText(view.getContext().getString(R.string.spec_ram, String.valueOf(phone.getRam())));
        compFragSpec5.setText(view.getContext().getString(R.string.spec_battery, String.valueOf(phone.getBattery())));
        if(phone.isDualSim()) {
            compFragSpec6.setText(getString(R.string.dual_sim_yes));
        } else {
            compFragSpec6.setText(getString(R.string.dual_sim_no));
        }
        compFragSpec7.setText(view.getContext().getString(R.string.spec_resolution, phone.getResolution()));
        compFragSpec8.setText(view.getContext().getString(R.string.spec_dimensions, String.valueOf(phone.getHeight()), String.valueOf(phone.getWidth()), String.valueOf(phone.getDepth())));
        compFragSpec9.setText(view.getContext().getString(R.string.spec_Mass, String.valueOf(phone.getMass())));
        compFragSpec10.setText(view.getContext().getString(R.string.spec_primary_camera, String.valueOf(phone.getPrimaryCamera())));
        compFragSpec11.setText(view.getContext().getString(R.string.spec_front_camera, String.valueOf(phone.getFrontCamera())));
        compFragSpec12.setText(view.getContext().getString(R.string.spec_connector, phone.getConnector()));
    }

    private void addPhoneSpecs2(Phone phone, View view) {
        compFragSpec1_2 = view.findViewById(R.id.comp_frag_spec_1_2);
        compFragSpec2_2 = view.findViewById(R.id.comp_frag_spec_2_2);
        compFragSpec3_2 = view.findViewById(R.id.comp_frag_spec_3_2);
        compFragSpec4_2 = view.findViewById(R.id.comp_frag_spec_4_2);
        compFragSpec5_2 = view.findViewById(R.id.comp_frag_spec_5_2);
        compFragSpec6_2 = view.findViewById(R.id.comp_frag_spec_6_2);
        compFragSpec7_2 = view.findViewById(R.id.comp_frag_spec_7_2);
        compFragSpec8_2 = view.findViewById(R.id.comp_frag_spec_8_2);
        compFragSpec9_2 = view.findViewById(R.id.comp_frag_spec_9_2);
        compFragSpec10_2 = view.findViewById(R.id.comp_frag_spec_10_2);
        compFragSpec11_2 = view.findViewById(R.id.comp_frag_spec_11_2);
        compFragSpec12_2 = view.findViewById(R.id.comp_frag_spec_12_2);
        compFragSpec1_2.setText(view.getContext().getString(R.string.spec_platform, phone.getPlatform()));
        compFragSpec2_2.setText(view.getContext().getString(R.string.spec_colour, phone.getColour()));
        compFragSpec3_2.setText(view.getContext().getString(R.string.spec_storage, String.valueOf(phone.getStorage())));
        compFragSpec4_2.setText(view.getContext().getString(R.string.spec_ram, String.valueOf(phone.getRam())));
        compFragSpec5_2.setText(view.getContext().getString(R.string.spec_battery, String.valueOf(phone.getBattery())));
        if(phone.isDualSim()) {
            compFragSpec6_2.setText(getString(R.string.dual_sim_yes));
        } else {
            compFragSpec6_2.setText(getString(R.string.dual_sim_no));
        }
        compFragSpec7_2.setText(view.getContext().getString(R.string.spec_resolution, phone.getResolution()));
        compFragSpec8_2.setText(view.getContext().getString(R.string.spec_dimensions, String.valueOf(phone.getHeight()), String.valueOf(phone.getWidth()), String.valueOf(phone.getDepth())));
        compFragSpec9_2.setText(view.getContext().getString(R.string.spec_Mass, String.valueOf(phone.getMass())));
        compFragSpec10_2.setText(view.getContext().getString(R.string.spec_primary_camera, String.valueOf(phone.getPrimaryCamera())));
        compFragSpec11_2.setText(view.getContext().getString(R.string.spec_front_camera, String.valueOf(phone.getFrontCamera())));
        compFragSpec12_2.setText(view.getContext().getString(R.string.spec_connector, phone.getConnector()));
    }
}
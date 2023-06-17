package com.example.licentaapp.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.licentaapp.R;
import com.example.licentaapp.utils.Phone;
import com.example.licentaapp.utils.SpecAdapter;

import java.util.ArrayList;

public class ProductFragment extends Fragment {
    private Phone phone;
    private ImageView prodFragImage;
    private TextView prodFragTitle;
    private TextView prodFragPrice;
    private Button prodPageBtnShowSpecs;
    private Button prodPageBtnSiteBuy;
    private Button btnMemorie1;
    private Button btnMemorie2;
    private Button btnMemorie3;
    private Fragment currentFragment;
    public static final String PHONE_KEY = "Phone key";
    private ListView listView;
    private ArrayList<String> phoneSpecs = new ArrayList<>();
    //TODO back button care sa te duca inapoi pe lista.

    public ProductFragment() {
        // Required empty public constructor
    }

    public static ProductFragment getInstance(Phone phone) {
        ProductFragment fragment = new ProductFragment();
        Bundle args = new Bundle();
        args.putParcelable(PHONE_KEY, phone);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            phone= getArguments().getParcelable("Phone key");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        initComponents(view);
        return view;
    }

    private void initComponents(View view) {
        addPhoneSpecs(phone, view);
        prodFragImage = view.findViewById(R.id.prod_frag_photo);
        prodFragTitle = view.findViewById(R.id.prod_frag_title);
        prodFragPrice = view.findViewById(R.id.prod_frag_price);
        prodPageBtnShowSpecs = view.findViewById(R.id.prod_frag_btn_show_specs);
        prodPageBtnSiteBuy = view.findViewById(R.id.prod_frag_btn_send_to_buy);
        btnMemorie1 = view.findViewById(R.id.btn_memorie_1);
        btnMemorie1.setText(phone.getStorages().get(0) +" GB");
        btnMemorie2 = view.findViewById(R.id.btn_memorie_2);
        btnMemorie2.setText(phone.getStorages().get(1)+ " GB");
        btnMemorie3 = view.findViewById(R.id.btn_memorie_3);
        btnMemorie3.setText(phone.getStorages().get(2) + " GB");
        listView = view.findViewById(R.id.prod_frag_list_view);
        SpecAdapter adapter = new SpecAdapter(view.getContext().getApplicationContext(), R.layout.lw_row_item_specs, phoneSpecs, getLayoutInflater());


        Bitmap bitmap = BitmapFactory.decodeFile(phone.getLocalFile().getAbsolutePath());
        prodFragImage.setImageBitmap(bitmap);

        prodFragTitle.setText(phone.getBrand()+ " " + phone.getModel());
        prodFragPrice.setText(view.getContext().getString(R.string.ro_item_pret_completation,String.valueOf(phone.getPrices().get(0))));

        prodPageBtnShowSpecs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listView.setAdapter(adapter);
            }
        });

        // Inside your button click listener
        prodPageBtnSiteBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentFragment = BuyFragment.newInstance(phone);
                openFragment(currentFragment);
            }
        });
        btnMemorie1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prodFragPrice.setText(view.getContext().getString(R.string.ro_item_pret_completation,String.valueOf(phone.getPrices().get(0))));
            }
        });
        btnMemorie2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prodFragPrice.setText(view.getContext().getString(R.string.ro_item_pret_completation,String.valueOf(phone.getPrices().get(1))));
            }
        });
        btnMemorie3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prodFragPrice.setText(view.getContext().getString(R.string.ro_item_pret_completation,String.valueOf(phone.getPrices().get(2))));
            }
        });
    }

    private void addPhoneSpecs(Phone phone, View view) {
        phoneSpecs.add(view.getContext().getString(R.string.spec_platform, phone.getPlatform()));
        phoneSpecs.add(view.getContext().getString(R.string.spec_colour, phone.getColours()));
        phoneSpecs.add(view.getContext().getString(R.string.spec_storage, String.valueOf(phone.getStorages())));
        phoneSpecs.add(view.getContext().getString(R.string.spec_ram, String.valueOf(phone.getRam())));
        phoneSpecs.add(view.getContext().getString(R.string.spec_battery, String.valueOf(phone.getBattery())));
        if(phone.isDualSim()) {
            phoneSpecs.add(getString(R.string.dual_sim_yes));
        } else {
            phoneSpecs.add(getString(R.string.dual_sim_no));
        }
        phoneSpecs.add(view.getContext().getString(R.string.spec_resolution, phone.getResolution()));
        phoneSpecs.add(view.getContext().getString(R.string.spec_dimensions, String.valueOf(phone.getHeight()), String.valueOf(phone.getWidth()), String.valueOf(phone.getDepth())));
        phoneSpecs.add(view.getContext().getString(R.string.spec_Mass, String.valueOf(phone.getMass())));
        phoneSpecs.add(view.getContext().getString(R.string.spec_primary_camera, String.valueOf(phone.getPrimaryCamera())));
        phoneSpecs.add(view.getContext().getString(R.string.spec_front_camera, String.valueOf(phone.getFrontCamera())));
        phoneSpecs.add(view.getContext().getString(R.string.spec_connector, phone.getConnector()));
        phoneSpecs.add(view.getContext().getString(R.string.spec_year, String.valueOf(phone.getYear())));

    }

    private void openFragment(Fragment fragment){
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_main, fragment)
                .addToBackStack(null)
                .commit();
    }
}
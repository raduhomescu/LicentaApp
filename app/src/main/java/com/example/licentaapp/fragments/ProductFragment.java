package com.example.licentaapp.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.licentaapp.R;
import com.example.licentaapp.utils.Phone;
import com.example.licentaapp.utils.SpecAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductFragment extends Fragment {
    private Phone phone;
    private ImageView prodFragImage;
    private TextView prodFragTitle;
    private TextView prodFragPrice;
    private Button prodPageBtnShowSpecs;
    private Button prodPageBtnSiteBuy;
    private Fragment currentFragment;
    public static final String PHONE_KEY = "Phone key";
    private ListView listView;
    private ArrayList<String> phoneSpecs = new ArrayList<>();
    private ImageButton btnMapsFlanco;
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
        btnMapsFlanco = view.findViewById(R.id.btn_maps_flanco);

        listView = view.findViewById(R.id.prod_frag_list_view);
        SpecAdapter adapter = new SpecAdapter(view.getContext().getApplicationContext(), R.layout.lw_row_item_specs, phoneSpecs, getLayoutInflater());

        Picasso.get()
                .load(phone.getLink_imagine())
                .into(prodFragImage);

        prodFragTitle.setText(phone.getBrand()+ " " + phone.getModel());
        prodFragPrice.setText(view.getContext().getString(R.string.ro_item_pret_completation,String.valueOf(phone.getPrice())));

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
                String url = phone.getLinkFlanco();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        btnMapsFlanco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentFragment = MapFragment.newInstance("Flanco");
                openFragment(currentFragment);
            }
        });
    }

    private void addPhoneSpecs(Phone phone, View view) {
        phoneSpecs.add(view.getContext().getString(R.string.spec_platform, phone.getPlatform()));
        phoneSpecs.add(view.getContext().getString(R.string.spec_colour, phone.getColour()));
        phoneSpecs.add(view.getContext().getString(R.string.spec_storage, String.valueOf(phone.getStorage())));
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


    }

    private void openFragment(Fragment fragment){
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_main, fragment)
                .addToBackStack(null)
                .commit();
    }
}
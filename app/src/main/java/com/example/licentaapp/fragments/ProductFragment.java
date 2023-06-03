package com.example.licentaapp.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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

import java.util.ArrayList;

public class ProductFragment extends Fragment {
    private Phone phone;
    private ImageView prodFragImage;
    private TextView prodFragTitle;
    private TextView prodFragPrice;
    private Button prodPageBtnShowSpecs;
    private Button prodPageBtnSiteBuy;
    private ListView listView;
    private ArrayList<String> phoneSpecs = new ArrayList<>();

    public ProductFragment() {
        // Required empty public constructor
    }

    public static ProductFragment getInstance(Phone phone) {
        ProductFragment fragment = new ProductFragment();
        Bundle args = new Bundle();
        args.putParcelable("Phone key", phone);
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
        //TODO DE ADAUGAT TOATE SPECS IN LISTA SI DE FACUT UN ADAPTER
        prodFragImage = view.findViewById(R.id.prod_frag_photo);
        prodFragTitle = view.findViewById(R.id.prod_frag_title);
        prodFragPrice = view.findViewById(R.id.prod_frag_price);
        prodPageBtnShowSpecs = view.findViewById(R.id.prod_frag_btn_show_specs);
        prodPageBtnSiteBuy = view.findViewById(R.id.prod_frag_btn_send_to_buy);
        listView = view.findViewById(R.id.prod_frag_list_view);
        SpecAdapter adapter = new SpecAdapter(view.getContext().getApplicationContext(), R.layout.lw_row_item_specs, phoneSpecs, getLayoutInflater());


        Bitmap bitmap = BitmapFactory.decodeFile(phone.getLocalFile().getAbsolutePath());
        prodFragImage.setImageBitmap(bitmap);

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
                String url = phone.getLinkAltex(); // Replace with your desired URL

                // Create an intent with ACTION_VIEW and the URL
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));

                // Check if there is a web browser available to handle the intent
                if (intent.resolveActivity(requireActivity().getPackageManager()) != null) {
                    // Start the activity with the intent
                    startActivity(intent);
                } else {
                    // Handle the case where no web browser is available
                    Toast.makeText(view.getContext().getApplicationContext(), "No web browser found", Toast.LENGTH_SHORT).show();
                }
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
        phoneSpecs.add(view.getContext().getString(R.string.spec_year, String.valueOf(phone.getYear())));

    }
}
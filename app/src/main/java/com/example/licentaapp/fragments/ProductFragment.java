package com.example.licentaapp.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.constraintlayout.helper.widget.Carousel;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.licentaapp.R;
import com.example.licentaapp.utils.Phone;

import java.util.ArrayList;

public class ProductFragment extends Fragment {
    private Phone phone;
    private ImageView prodFragImage;
    private TextView prodFragTitle;
    private TextView prodFragPrice;
    private Button prodPageBtnShowSpecs;
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
        View view =  inflater.inflate(R.layout.fragment_product, container, false);
        initComponents(view);
        return view;
    }

    private void initComponents(View view) {
        phoneSpecs.add(phone.getPlatform());
        phoneSpecs.add(phone.getColour());
        //TODO DE ADAUGAT TOATE SPECS IN LISTA SI DE FACUT UN ADAPTER
        prodFragImage = view.findViewById(R.id.prod_frag_photo);
        prodFragTitle = view.findViewById(R.id.prod_frag_title);
        prodFragPrice = view.findViewById(R.id.prod_frag_price);
        prodPageBtnShowSpecs = view.findViewById(R.id.prod_frag_btn_show_specs);
        listView = view.findViewById(R.id.prod_frag_list_view);
        ArrayAdapter adapter = new ArrayAdapter<>(view.getContext().getApplicationContext(), android.R.layout.simple_list_item_1, phoneSpecs);


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

    }
}
package com.example.licentaapp.fragments;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.Manifest;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.licentaapp.R;
import com.example.licentaapp.utils.JsonParser;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapFragment extends Fragment {
    private static final String SHOP_NAME_KEY = "shop name key";
    private String shopName;
    private String shopNameSecond = "";
    private String shopNameFirst = "";
    SupportMapFragment supportMapFragment;
    GoogleMap map;
    FusedLocationProviderClient fusedLocationProviderClient;
    double currentLat = 0;
    double currentLong = 0;
    private String next_page_key = "Aaw_FcI2do1pLpTisjUXJy2YbxLXwyFW6k9X-cb0BO9S5sPRP1zX-HLcZoTGOJZaAQ5mfABAWNSXOp83PZxeWuSKTFx5Ro3SY_S5GDcO-GaTzF-FwB4mD-hMfnfFGmdFJcO3JivpvbAghP7yopMyl0kZuU4cdoRGm8KUhvL8r7SxnVV3O2JSDuoDQXNaWpCZbMDWgVOMrErDfAgNqsmMLub8qSOOIvXEz6YpG6MY2pOgU-qCL2PyJ2N4Y_zQDEXBNdqJVIwer4caf26ghf2WaJveG-MbGm2PuYZH5T6RDy5C3cQEVzZTpwbhxAn1d6wA_pDFw_CUCnqksVWW7AEe3qEEawwDPSEe-Mkj0lAZXSO_s12JzKsJoWyd_7uk9km0MvPn761dJKokAuWXzZ7LtK4iUmPAxw7stkXAwp233iUAO_1uu3zEQoLibZd7jvMjQyVrsXNp6A";

    public MapFragment() {
        // Required empty public constructor
    }

    public static MapFragment newInstance(String shopName) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString(SHOP_NAME_KEY, shopName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            shopName = getArguments().getString(SHOP_NAME_KEY);
            if (shopName.contains(",")) {
                String[] parts = shopName.split(",");
                shopNameFirst = parts[0];
                Log.d("nume magazin 1: ", shopNameFirst);
                shopNameSecond = parts[1];
                Log.d("nume magazin 2: ", shopNameSecond);
            } else {
                shopNameFirst = shopName;
                shopNameSecond = shopName;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        initComponents(view);
        return view;
    }

    private void initComponents(View view) {
        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);
        String placeTypeList = "electronics_store";

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext());

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Task<Location> task = fusedLocationProviderClient.getLastLocation();
            task.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        currentLat = 44.4490628;
                        currentLong = 26.1253156;

                        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                            @Override
                            public void onMapReady(GoogleMap googleMap) {
                                map = googleMap;
                                map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLat, currentLong), 10));
                                if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                                    map.setMyLocationEnabled(true);
                                }
                            }
                        });
                        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json" + "?location=" +
                                currentLat + "," + currentLong + "&radius=5000" + "&type=" +
                                placeTypeList + "&sensor=true" + "&key=" + getResources().getString(R.string.google_map_key) +
                                "&next_page_token=" + next_page_key;
                        Log.d("link locatie", url);

                        new PlaceTask().execute(url);
                    }
                }
            });
        } else {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }

    }

    private class PlaceTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            String data = null;
            try {
                data = downloadUrl(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            new ParserTask().execute(s);
        }
    }

    private String downloadUrl(String string) throws IOException {
        URL url = new URL(string);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.connect();
        InputStream stream = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder builder = new StringBuilder();
        String line = "";
        while ((line = reader.readLine()) != null){
            builder.append(line);
        }
        String data = builder.toString();
        reader.close();
        return data;
    }

    private class ParserTask extends AsyncTask<String, Integer, List<HashMap<String,String>>> {

        @Override
        protected List<HashMap<String, String>> doInBackground(String... strings) {
            JsonParser jsonParser = new JsonParser();
            List<HashMap<String, String>> mapList = null;
            List<HashMap<String, String>> filteredList = new ArrayList<>();
            JSONObject object = null;
            try {
                object = new JSONObject(strings[0]);
                String nextPageToken = object.getString("next_page_token");
                Log.d("token pt next page", nextPageToken);
                mapList = jsonParser.parseResult(object);
                for (HashMap<String, String> map : mapList) {
                    if (map.get("name").contains(shopName) || map.get("name").contains(shopNameSecond) || map.get("name").contains(shopNameFirst)) {
                        filteredList.add(map);
                    }
                }
                Log.d("obiect din json", mapList.toString());
                Log.d("obiect din json", filteredList.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return filteredList;
        }

        @Override
        protected void onPostExecute(List<HashMap<String, String>> hashMaps) {
            map.clear();
            for (int i = 0; i < hashMaps.size(); i++) {
                HashMap<String,String> hashMapList = hashMaps.get(i);
                double lat = Double.parseDouble(hashMapList.get("lat"));
                double lng = Double.parseDouble(hashMapList.get("lng"));
                String name = hashMapList.get("name");
                LatLng latLng = new LatLng(lat, lng);
                MarkerOptions options = new MarkerOptions();
                options.position(latLng);
                options.title(name);
                map.addMarker(options);
            }
        }
    }
}

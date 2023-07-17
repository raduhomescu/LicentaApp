package com.example.licentaapp.fragments;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.Manifest;


import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


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
    private SupportMapFragment supportMapFragment;
    private GoogleMap map;
    private FusedLocationProviderClient fusedLocationProviderClient;
    double currentLat = 0;
    double currentLong = 0;
    double lat = 0;
    double longi = 0;
    private String nextPageToken = "";
    private List<HashMap<String, String>> mapList = new ArrayList<>();
    private List<HashMap<String, String>> filteredList = new ArrayList<>();
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
                shopNameSecond = parts[1];
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
                        currentLat = location.getLatitude();
                        currentLong = location.getLongitude();
                        lat = 44.4494467;
                        longi = 26.1255262;

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
                                lat + "," + longi + "&radius=15000" + "&type=" +
                                placeTypeList + "&sensor=true" + "&key=" + getResources().getString(R.string.google_map_key) +
                                "&next_page_token=" + nextPageToken;

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
                JSONObject object = new JSONObject(data);
                if (object.has("next_page_token")) {
                    nextPageToken = object.optString("next_page_token", "");
                }
            } catch (IOException | JSONException e) {
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
            JSONObject object = null;
            try {
                object = new JSONObject(strings[0]);
                if (object.has("next_page_token")) {
                    nextPageToken = object.getString("next_page_token");
                }
                mapList = jsonParser.parseResult(object);
                while (!nextPageToken.isEmpty()) {
                    loadNextPage(nextPageToken);
                    Thread.sleep(2000); // Delay for 2 seconds between subsequent page requests

                    // Fetch results for the current page
                    String resultUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json" +
                            "?sensor=true" +
                            "&key=" + getResources().getString(R.string.google_map_key) +
                            "&pagetoken=" + nextPageToken+
                            "&radius=" + 15000;
                    String resultData = downloadUrl(resultUrl);
                    JSONObject resultObject = new JSONObject(resultData);
                    nextPageToken = resultObject.optString("next_page_token", "");
                    List<HashMap<String, String>> resultMapList = jsonParser.parseResult(resultObject);
                    mapList.addAll(resultMapList);
                }
            } catch (JSONException | InterruptedException | IOException e) {
                e.printStackTrace();
            }
            for (HashMap<String, String> map : mapList) {
                if (map.get("name").contains(shopName) || map.get("name").contains(shopNameSecond) || map.get("name").contains(shopNameFirst)) {
                    filteredList.add(map);
                }
            }
            return filteredList;
        }

        @Override
        protected void onPostExecute(List<HashMap<String, String>> hashMaps) {
            //map.clear();
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
    private void loadNextPage(String nextPageToken) {
        String nextUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json" +
                "?sensor=true" +
                "&key=" + getResources().getString(R.string.google_map_key) +
                "&pagetoken=" + nextPageToken +
                "&radius=" + 10000;

        new PlaceTask().execute(nextUrl);
    }
}

package com.example.licentaapp.fragments.quetionnaire;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.licentaapp.R;
import com.example.licentaapp.utils.Phone;
import com.example.licentaapp.utils.PhoneAdapter;
import com.example.licentaapp.utils.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SuggestionFragment extends Fragment implements PhoneAdapter.OnFavoriteButtonClickListener{
    private ArrayList<String> filterList;
    private Fragment currentFragment;
    public static final String USER_KEY = "User key";
    private User user;
    public static final String FILTER_LIST_KEY = "filter list";
    FirebaseFirestore fStore;
    CollectionReference phonesRef;
    private ListView lvPhones;
    private ArrayList <Phone> phonesList = new ArrayList<>();
    private ArrayList <Phone> comparePhonesList = new ArrayList<>();
    private ArrayList<String> documentIds = new ArrayList<>();
    ProgressBar progressBarSugg;
    private static final String PHONES_COLLECTION_KEY = "phones_from_flanco";
    private Button btn_start_over_survey;
    private static final String FILTER_LIST_ITEM = "new filters";

    public SuggestionFragment() {
        // Required empty public constructor
    }

    public static SuggestionFragment newInstance(ArrayList<String> filterList, User user, ArrayList<Phone> comparePhonesList) {
        SuggestionFragment fragment = new SuggestionFragment();
        Bundle args = new Bundle();
        args.putStringArrayList(FILTER_LIST_KEY,filterList);
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
            comparePhonesList = getArguments().getParcelableArrayList("compare phones");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_suggestion, container, false);
        initComponents(view);
        return view;
    }

    private void initComponents(View view) {
        lvPhones = view.findViewById(R.id.lv_suggestion_fragment);
        phonesList.clear();
        PhoneAdapter adapter = new PhoneAdapter(view.getContext().getApplicationContext(),R.layout.lv_row_item, phonesList, getLayoutInflater(), user, getActivity(), comparePhonesList);
        adapter.setOnFavoriteButtonClickListener(this);
        btn_start_over_survey = view.findViewById(R.id.btn_start_over_survey);
        btn_start_over_survey.setVisibility(View.INVISIBLE);
        progressBarSugg = view.findViewById(R.id.progressBar_sugg);
        progressBarSugg.setVisibility(View.VISIBLE);
        String[] parts =filterList.get(7).split("/");

        fStore = FirebaseFirestore.getInstance();
        phonesRef = fStore.collection(PHONES_COLLECTION_KEY);
        Query queryPhones = phonesRef.whereEqualTo("Storage", Integer.valueOf(filterList.get(2)))
                        .whereGreaterThanOrEqualTo("Price", Integer.valueOf(parts[0]))
                        .whereLessThanOrEqualTo("Price", Integer.valueOf(parts[1]));
        //TODO Filtrare cu if pt model
        //TODO Si clar regandit cu datele merge rpd pe tel
        //TODO de rezolvat frumos si partea cu maps

        queryPhones.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                Log.d(TAG, "Query complete");
                if (task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();
                    for (QueryDocumentSnapshot documentSnapshot : querySnapshot) {
                        String documentId = documentSnapshot.getId();
                        Log.d(TAG, "Document Id: " + documentId);
                        documentIds.add(documentId);
                        Phone phone = new Phone();
                        phone.setuId(documentId);
                        DocumentReference documentReference = fStore.collection(PHONES_COLLECTION_KEY).document(documentId);
                        documentReference.get().addOnCompleteListener(task2 -> {
                            if (task2.isSuccessful()) {
                                DocumentSnapshot documentR = task2.getResult();
                                if (documentR.exists()) {
                                    phone.setBrand(documentR.getData().get("Brand").toString());
                                    phone.setModel(documentR.getData().get("Model").toString());
                                    phone.setPlatform(documentR.getData().get("Platform").toString());
                                    phone.setRam(Integer.valueOf(documentR.getData().get("RAM").toString()));
                                    phone.setResolution(documentR.getData().get("Resolution").toString());
                                    phone.setBattery(Integer.valueOf(documentR.getData().get("Battery").toString()));
                                    phone.setWidth(Double.valueOf(documentR.getData().get("Width").toString()));
                                    phone.setHeight(Double.valueOf(documentR.getData().get("height_number").toString()));
                                    phone.setDepth(Double.valueOf(documentR.getData().get("Depth").toString()));
                                    phone.setMass(Double.valueOf(documentR.getData().get("Mass").toString()));
                                    phone.setDualSim(Boolean.getBoolean(documentR.getData().get("Dual Sim").toString()));
                                    phone.setPrimaryCamera(Double.valueOf(documentR.getData().get("Primary Camera").toString()));
                                    phone.setFrontCamera(Double.valueOf(documentR.getData().get("Front Camera").toString()));
                                    phone.setConnector(documentR.getData().get("Connector").toString());
                                    phone.setLinkFlanco(documentR.getData().get("Link Flanco").toString());
                                    phone.setPrice(Double.valueOf(documentR.getData().get("Price").toString()));
                                    phone.setStorage(Integer.valueOf(documentR.getData().get("Storage").toString()));
                                    phone.setColour(documentR.getData().get("Colour").toString());
                                    phone.setLink_imagine(documentR.getData().get("Link Imagine").toString());
                                    //TODO de scos stringurile de aici si pus frumos
                                    if(filterPhone(phone)) {
                                        phonesList.add(phone);
                                    }
                                    if(!phonesList.isEmpty()) {
                                        lvPhones.setAdapter(adapter);
                                    } else {
                                        btn_start_over_survey.setVisibility(View.VISIBLE);
                                    }
                                    Log.d(TAG, "Phones map: " + phonesList.toString());
                                } else {
                                    Log.d(TAG, "No such document");
                                }
                            } else {
                                Log.d(TAG, "Error getting document: ", task2.getException());
                            }
                        });
                    }
                    progressBarSugg.setVisibility(View.INVISIBLE);
                    btn_start_over_survey.setVisibility(View.VISIBLE);
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });

        btn_start_over_survey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterList.clear();
                filterList.add(FILTER_LIST_ITEM);
                currentFragment = PreferredPhoneQ1Fragment.newInstance(filterList, user, comparePhonesList);
                openFragment(currentFragment);
            }
        });
    }

    @Override
    public void onFavoriteButtonClicked(Phone phone) {
        String phoneId = String.valueOf(phone.getuId());
        if (user.getFavouritePhones().contains(phoneId)) {
            user.removeFavoritePhoneId(phoneId);
        } else {
            user.addFavoritePhoneId(phoneId);
        }
    }

    private void openFragment(Fragment fragment){
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_main, fragment)
                .commit();
    }

    private boolean filterPhone(Phone phone) {
        Boolean filterStorage = false;
        Boolean filterBattery = false;
        Boolean filterResolution = false;
        Boolean filterRam = false;
        Boolean filterCamera = false;

        if(Integer.valueOf(filterList.get(2)) >= phone.getStorage()/2 && Integer.valueOf(filterList.get(2)) <= phone.getStorage()*2) {
            filterStorage = true;
        }
        if(Integer.valueOf(filterList.get(3))>= phone.getBattery()-1000 && Integer.valueOf(filterList.get(3))<= phone.getBattery()+1000) {
            filterBattery = true;
        }
        int resolution1 = Integer.valueOf(phone.getResolution().substring(0, phone.getResolution().indexOf('x')).replaceAll("\\s+", ""));
        int resolution2 = Integer.valueOf(phone.getResolution().substring(1, phone.getResolution().indexOf('x')).replaceAll("\\s+", ""));
        if(resolution1 < resolution2) {
            if (Integer.valueOf(filterList.get(4)) >= resolution1 - 720
                    && Integer.valueOf(filterList.get(4)) <= resolution1 + 720) {
                filterResolution = true;
            }
        } else {
            if (Integer.valueOf(filterList.get(4)) >= resolution2 - 720
                    && Integer.valueOf(filterList.get(4)) <= resolution2 + 720) {
                filterResolution = true;
            }
        }
        if(Integer.valueOf(filterList.get(5)) >= phone.getRam()-4 && Integer.valueOf(filterList.get(5)) <= phone.getRam()+4) {
            filterRam = true;
        }
        if(filterList.get(6).equals(getString(R.string.irrelevant))) {
            filterCamera = true;
        } else if (filterList.get(6).equals(getString(R.string.medium_quality))) {
            if (phone.getPrimaryCamera() <= 20) {
                filterCamera = true;
            }
        } else if (filterList.get(6).equals(getString(R.string.high_quality))) {
            if (phone.getPrimaryCamera() >= 20 && phone.getPrimaryCamera() <= 40) {
                filterCamera = true;
            }
        } else if (filterList.get(6).equals(getString(R.string.high_quality))) {
            if (phone.getPrimaryCamera() >= 40) {
                filterCamera = true;
            }
        }
        return filterStorage && filterBattery && filterResolution && filterRam && filterCamera;
    }
}
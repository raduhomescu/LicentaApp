package com.example.licentaapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.licentaapp.fragments.AccountFragment;
import com.example.licentaapp.fragments.CompareFragment;
import com.example.licentaapp.fragments.FavouritesFragment;
import com.example.licentaapp.fragments.HomeFragment;
import com.example.licentaapp.fragments.ProfileFragment;
import com.example.licentaapp.fragments.SearchFragment;
import com.example.licentaapp.fragments.quetionnaire.BatteryQ3Fragment;
import com.example.licentaapp.fragments.quetionnaire.PreferredPhoneQ1Fragment;
import com.example.licentaapp.fragments.quetionnaire.PriceQ7Fragment;
import com.example.licentaapp.fragments.quetionnaire.PrimaryCameraQ6Fragment;
import com.example.licentaapp.fragments.quetionnaire.RamQ5Fragment;
import com.example.licentaapp.fragments.quetionnaire.ResolutionQ4Fragment;
import com.example.licentaapp.fragments.quetionnaire.StorageQ2Fragment;
import com.example.licentaapp.fragments.quetionnaire.SuggestionFragment;
import com.example.licentaapp.utils.Phone;
import com.example.licentaapp.utils.User;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    ActionBar actionBar;
    private Fragment currentFragment;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private CollectionReference phonesRef;
    private ArrayList <Phone> phonesList = new ArrayList<>();
    private ArrayList<String> documentIds = new ArrayList<>();
    private User user = new User();
    private ArrayList<String> phonesCodes = new ArrayList<>();
    private String userID;
    private ProgressBar progressBarMain;
    private ProgressBar progressBarMain2;
    private ArrayList<String> filterList = new ArrayList<>();
    private boolean shouldExitApp = false;
    private Timer timer;
    private ArrayList<Phone> comparePhonesList = new ArrayList<>();

    private static final String PHONES_COLLECTION_KEY = "phones_from_flanco";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadFromDataBase();
        initComponents();
    }

    private void loadFromDataBase() {
            progressBarMain = findViewById(R.id.progressBar_main);
            progressBarMain2 = findViewById(R.id.progressBar_main);
            progressBarMain.setVisibility(View.VISIBLE);
            progressBarMain2.setVisibility(View.VISIBLE);
            fStore = FirebaseFirestore.getInstance();
            phonesRef = fStore.collection(PHONES_COLLECTION_KEY);
        phonesRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                Log.d(TAG, "Query complete");
                if (task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();
                    for (QueryDocumentSnapshot documentSnapshot : querySnapshot) {
                        String documentId = documentSnapshot.getId();
                        documentIds.add(documentId);
                        Phone phone = new Phone();
                        phone.setuId(documentId);
                        progressBarMain.setVisibility(View.VISIBLE);
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
                                    phone.setDualSim((Boolean) (documentR.getData().get("Dual Sim")));
                                    phone.setPrimaryCamera(Double.valueOf(documentR.getData().get("Primary Camera").toString()));
                                    phone.setFrontCamera(Double.valueOf(documentR.getData().get("Front Camera").toString()));
                                    phone.setConnector(documentR.getData().get("Connector").toString());
                                    phone.setLinkFlanco(documentR.getData().get("Link Flanco").toString());
                                    phone.setPrice(Double.valueOf(documentR.getData().get("Price").toString()));
                                    phone.setStorage(Integer.valueOf(documentR.getData().get("Storage").toString()));
                                    phone.setColour(documentR.getData().get("Colour").toString());
                                    phone.setLink_imagine(documentR.getData().get("Link Imagine").toString());
                                    phonesList.add(phone);
                                } else {
                                    Log.d(TAG, "No such document");
                                }
                            } else {
                                Log.d(TAG, "Error getting document: ", task2.getException());
                            }
                        });
                    }
                    progressBarMain2.setVisibility(View.INVISIBLE);
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });

        fAuth = FirebaseAuth.getInstance();
        if(fAuth.getCurrentUser() != null){
            userID = fAuth.getCurrentUser().getUid();
            user.setUserId(userID);
            DocumentReference documentReference = fStore.collection("clients").document(userID);
            documentReference.get().addOnSuccessListener(document -> {
                if (document.exists()) {
                    user.setfName(document.getData().get("fName").toString());
                    user.setEmail(document.getData().get("email").toString());
                    user.setPhoneNumber(document.getData().get("phone").toString());
                    if(document.getData().get("favourites") != null) {
                        phonesCodes = (ArrayList<String>) document.getData().get("favourites");
                        user.setFavouritePhones(phonesCodes);
                        startDataSavingTimer();
                    }
                } else {
                    Log.d(TAG, "No such document");
                }
            }).addOnFailureListener(e -> {
                Log.d(TAG, "Error getting document: ", e);
            });
        } else {
            startDataSavingTimer();
        }
    }

    private void initComponents(){
        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.search_gadget_30dp);
        actionBar.setDisplayHomeAsUpEnabled(true);

        MapsInitializer.initialize(getApplicationContext());
        bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
        currentFragment=HomeFragment.getInstance(phonesList, filterList, user, comparePhonesList);
        openFragment(currentFragment);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            if(filterList.size() == 0) {
                                currentFragment = HomeFragment.getInstance(phonesList, filterList, user, comparePhonesList);
                            } else if (filterList.size() == 1) {
                                currentFragment = PreferredPhoneQ1Fragment.newInstance(filterList, user, comparePhonesList);
                            } else if (filterList.size() == 2) {
                                currentFragment = StorageQ2Fragment.newInstance(filterList, user, comparePhonesList);
                            } else if (filterList.size() == 3) {
                                currentFragment = BatteryQ3Fragment.newInstance(filterList, user, comparePhonesList);
                            } else if (filterList.size() == 4) {
                                currentFragment = ResolutionQ4Fragment.newInstance(filterList, user, comparePhonesList);
                            } else if (filterList.size() == 5) {
                                currentFragment = RamQ5Fragment.newInstance(filterList, user, comparePhonesList);
                            } else if (filterList.size() == 6) {
                                currentFragment = PrimaryCameraQ6Fragment.newInstance(filterList, user, comparePhonesList);
                            } else if (filterList.size() == 7) {
                                currentFragment = PriceQ7Fragment.newInstance(filterList, user, comparePhonesList);
                            } else if (filterList.size() == 8) {
                                currentFragment = SuggestionFragment.newInstance(filterList, user, comparePhonesList);
                            }
                            break;

                        case R.id.nav_account:
                            if(fAuth.getCurrentUser() != null) {
                                currentFragment = ProfileFragment.getInstance(user);
                            }
                            else {
                                currentFragment = AccountFragment.getInstance(phonesList, filterList, user);
                            }
                            break;

                        case R.id.nav_search:
                            currentFragment = SearchFragment.getInstance(phonesList, user, comparePhonesList);
                            break;

                        case R.id.nav_favorite:
                            if(fAuth.getCurrentUser() != null) {
                                currentFragment = FavouritesFragment.getInstance(phonesList, user, comparePhonesList);
                            } else {
                                currentFragment = AccountFragment.getInstance(phonesList, filterList, user);
                            }
                            break;
                    }
                    openFragment(currentFragment);
                    return true;
                }
        });
    }
    private void openFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_main, fragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
            if (shouldExitApp) {
                super.onBackPressed();
            } else {
                FragmentManager fragmentManager = getSupportFragmentManager();
                if (fragmentManager.getBackStackEntryCount() > 0) {
                    fragmentManager.popBackStack();
                } else {
                    moveTaskToBack(true);

                }
            }
    }

    @Override
    protected void onResume() {
        super.onResume();
        shouldExitApp = false;
    }
    @Override
    protected void onPause() {
        super.onPause();
        shouldExitApp = true;
    }


    @Override
    protected void onDestroy() {
        stopDataSavingTimer();
        super.onDestroy();
    }

    public void handleActivityResult(Intent data) {
        bottomNavigationView.setSelectedItemId(R.id.nav_account);
        user = data.getParcelableExtra(LoginActivity.USER_KEY);
    }

    public void handleRegister() {
        bottomNavigationView.setSelectedItemId(R.id.nav_account);
        userID = fAuth.getCurrentUser().getUid();
        user.setUserId(userID);
        DocumentReference documentReference = fStore.collection("clients").document(userID);
        documentReference.get().addOnSuccessListener(document -> {
            if (document.exists()) {
                user.setfName(document.getData().get("fName").toString());
                user.setEmail(document.getData().get("email").toString());
                user.setPhoneNumber(document.getData().get("phone").toString());
                if(document.getData().get("favourites") != null) {
                    phonesCodes = (ArrayList<String>) document.getData().get("favourites");
                    user.setFavouritePhones(phonesCodes);
                }
            } else {
                Log.d(TAG, "No such document");
            }
        }).addOnFailureListener(e -> {
            Log.d(TAG, "Error getting document: ", e);
        });
    }

    private void startDataSavingTimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                saveFavoritePhonesToFirestore();
            }
        }, 0, 10 * 60 * 10); // Execute the task every 10 minutes
    }

    private void stopDataSavingTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_compare, menu);
        MenuItem buttonItem = menu.findItem(R.id.action_button);
        buttonItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                if(comparePhonesList.size() == 2) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container_main, CompareFragment.newInstance(comparePhonesList))
                            .commit();
                } else {
                    Toast.makeText(getApplicationContext(), "Please add two phones for comparison.", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
        return true;
    }

    private void saveFavoritePhonesToFirestore() {
        if (fAuth.getCurrentUser() != null) {
            Map<String, Object> updates = new HashMap<>();
            if (!user.getFavouritePhones().isEmpty()) {
                updates.put("favourites", user.getFavouritePhones());
            } else {
                updates.put("favourites", new ArrayList<String>());
            }
            fStore.collection("clients").document(user.getUserId())
                    .update(updates)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "Favorite phones saved successfully.");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error saving favorite phones.", e);
                        }
                    });
        }
    }

}
package com.example.licentaapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.licentaapp.fragments.AccountFragment;
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
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    ActionBar actionBar;
    private Fragment currentFragment;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    CollectionReference phonesRef;
    private ArrayList <Phone> phonesList = new ArrayList<>();
    private ArrayList<String> documentIds = new ArrayList<>();
    private User user = new User();
    ArrayList<String> phonesCodes = new ArrayList<>();
    String userID;
    ProgressBar progressBarMain;
    ProgressBar progressBarMain2;
    private ArrayList<String> filterList = new ArrayList<>();

    //TODO de verificat valori null prin aplicatie
    private static final String PHONES_COLLECTION_KEY = "phones";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        for (int i = 0; i < 10; i++) {
//            loadFromDataBase();
//        }
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
                    // iterate over the documents in the query result
                    for (QueryDocumentSnapshot documentSnapshot : querySnapshot) {
                        String documentId = documentSnapshot.getId();
                        Log.d(TAG, "Document Id: " + documentId);
                        documentIds.add(documentId);
                        Phone phone = new Phone();
                        phone.setuId(documentId);
                        FirebaseStorage storage = FirebaseStorage.getInstance();
                        StorageReference storageRef = storage.getReference();
                        StorageReference imageRef = storageRef.child(documentId + ".jpg");
                        File localFile = null;
                        try {
                            localFile = File.createTempFile("images", ".jpg");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        File finalLocalFile = localFile;
                        imageRef.getFile(localFile)
                                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                        // Set the imageFile attribute of the Phone object to the downloaded file
                                        phone.setLocalFile(finalLocalFile);
                                        Log.d(TAG, "Phones map local file " + phone.getLocalFile());
                                        progressBarMain.setVisibility(View.INVISIBLE);
                                        // You can now use the Phone object with the downloaded image file
                                        // ...
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        onFailure(exception);
                                    }
                                });
                        progressBarMain.setVisibility(View.VISIBLE);
                        DocumentReference documentReference = fStore.collection("phones").document(documentId);
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
                                    phone.setStorage(Integer.valueOf(documentR.getData().get("Storage").toString()));
                                    phone.setColour(documentR.getData().get("Colour").toString());
                                    phone.setWidth(Double.valueOf(documentR.getData().get("Width").toString()));
                                    phone.setHeight(Double.valueOf(documentR.getData().get("Height").toString()));
                                    phone.setDepth(Double.valueOf(documentR.getData().get("Depth").toString()));
                                    phone.setMass(Double.valueOf(documentR.getData().get("Mass").toString()));
                                    phone.setDualSim(Boolean.getBoolean(documentR.getData().get("Dual Sim").toString()));
                                    phone.setPrimaryCamera(Double.valueOf(documentR.getData().get("Primary Camera").toString()));
                                    phone.setFrontCamera(Double.valueOf(documentR.getData().get("Front Camera").toString()));
                                    phone.setYear(Integer.valueOf(documentR.getData().get("Year").toString()));
                                    phone.setPrice(Double.valueOf(documentR.getData().get("Price").toString()));
                                    phone.setConnector(documentR.getData().get("Connector").toString());
                                    phonesList.add(phone);
                                    Log.d(TAG, "Phones map: " + phonesList.toString());
                                    progressBarMain2.setVisibility(View.INVISIBLE);
                                    // Put the code that needs to be executed after the data is processed here
                                } else {
                                    Log.d(TAG, "No such document");
                                }
                            } else {
                                Log.d(TAG, "Error getting document: ", task2.getException());
                            }
                        });

                    }
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
                    Log.d(TAG, "Utilizator: " + document.getData().get("favourites"));
                    if(document.getData().get("favourites") != null) {
                        phonesCodes = new ArrayList<String> (Arrays.asList(document.getData().get("favourites").toString().split(",")));
                        Log.d(TAG, "Coduri main: " + phonesCodes);
                        user.setFavouritePhones(phonesCodes);
                        Log.d(TAG, "Utilizator main: " + user.toString());
                    }
                } else {
                    Log.d(TAG, "No such document");
                }
            }).addOnFailureListener(e -> {
                Log.d(TAG, "Error getting document: ", e);
            });
        }
    }

    private void initComponents(){
        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.search_gadget_30dp);
        actionBar.setDisplayHomeAsUpEnabled(true);
        bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
        currentFragment=HomeFragment.getInstance(phonesList, filterList, user);
        openFragment(currentFragment);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            if(filterList.size() == 0) {
                                Log.d("main listtt: ", filterList.toString());
                                currentFragment = HomeFragment.getInstance(phonesList, filterList, user);
                            } else if (filterList.size() == 1) {
                                Log.d("main listtt: ", filterList.toString());
                                currentFragment = PreferredPhoneQ1Fragment.newInstance(filterList, user);
                            } else if (filterList.size() == 2) {
                                currentFragment = StorageQ2Fragment.newInstance(filterList, user);
                            } else if (filterList.size() == 3) {
                                currentFragment = BatteryQ3Fragment.newInstance(filterList, user);
                            } else if (filterList.size() == 4) {
                                currentFragment = ResolutionQ4Fragment.newInstance(filterList, user);
                            } else if (filterList.size() == 5) {
                                currentFragment = RamQ5Fragment.newInstance(filterList, user);
                            } else if (filterList.size() == 6) {
                                currentFragment = PrimaryCameraQ6Fragment.newInstance(filterList, user);
                            } else if (filterList.size() == 7) {
                                currentFragment = PriceQ7Fragment.newInstance(filterList, user);
                            } else if (filterList.size() == 8) {
                                currentFragment = SuggestionFragment.newInstance(filterList, user);
                            }
                            break;

                        case R.id.nav_account:
                            if(fAuth.getCurrentUser() != null) {
                                currentFragment = ProfileFragment.getInstance(user);
                                System.out.println(fAuth.getCurrentUser());
                            }
                            else {
                                currentFragment = AccountFragment.getInstance();
                            }
                            break;

                        case R.id.nav_search:
                            currentFragment = SearchFragment.getInstance(phonesList, user);
                            break;

                        case R.id.nav_favorite:
                            if(fAuth.getCurrentUser() != null) {
                                currentFragment = FavouritesFragment.getInstance(phonesList, user);
                                System.out.println(fAuth.getCurrentUser());
                            } else {
                                currentFragment = AccountFragment.getInstance();
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
}
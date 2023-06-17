package com.example.licentaapp.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.example.licentaapp.R;
import com.example.licentaapp.fragments.ProductFragment;

import java.io.File;
import java.util.ArrayList;

public class PhoneAdapter extends ArrayAdapter<Phone> {

    private Context context;
    private int resource;
    private ArrayList<Phone> phones;
    private LayoutInflater inflater;
    private OnFavoriteButtonClickListener mListener;
    private User user;
    private FragmentActivity activity;

    public PhoneAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Phone> objects, LayoutInflater inflater, User user, FragmentActivity activity) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.phones = objects;
        this.inflater = inflater;
        this.user = user;
        this.activity = activity;
    }

    public void setOnFavoriteButtonClickListener(OnFavoriteButtonClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(resource, parent, false);
        Phone phone = phones.get(position);
        if(phone.getLocalFile() != null) {
            addPhoto(phone.getLocalFile(), view);
        } else {
            ImageView imageView = view.findViewById(R.id.row_item_image);

            imageView.setImageResource(R.drawable.baseline_smartphone_24);
        }
        addName(phone.getBrand(), phone.getModel(), view);
        addStorage(phone.getStorages(), view);
        addColour(phone.getColours(), view);
        ImageButton btnFavortie = view.findViewById(R.id.row_item_favourites_button);
        if (user.getUserId()!=null) {
            User user1 = user;
            if(user1.getFavouritePhones().contains(phone.getuId())){
                btnFavortie.setImageResource(R.drawable.ic_baseline_favorite_red_24);
                btnFavortie.setTag(R.drawable.ic_baseline_favorite_red_24);
            } else {
                btnFavortie.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                btnFavortie.setTag(R.drawable.ic_baseline_favorite_border_24);
            }
        }
            btnFavortie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (user.getUserId()!=null) {
                        Log.d("Drawable", String.valueOf(R.drawable.ic_baseline_favorite_border_24));
                        if ((Integer) btnFavortie.getTag() == R.drawable.ic_baseline_favorite_border_24) {
                            btnFavortie.setImageResource(R.drawable.ic_baseline_favorite_red_24);
                        } else {
                            btnFavortie.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                        }
                        if (mListener != null) {
                            mListener.onFavoriteButtonClicked(phone);
                        }
                    } else{
                        Toast.makeText(getContext(), "Please Log In", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ino click adapter", "merge");
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container_main, ProductFragment.getInstance(phone))
                        .addToBackStack(null)
                        .commit();
            }
        });
        return view;
    }

    private void addPhoto(File imageFile, View view) {
        Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
        ImageView imageView = view.findViewById(R.id.row_item_image);
        imageView.setImageBitmap(bitmap);
    }

    private void addName(String brand, String model, View view) {
        TextView textView = view.findViewById(R.id.row_item_tv_titlu);
        textView.setText(calculateTextViewValue(brand + " " + model));
    }

    private void addStorage(ArrayList<Integer> storagesInt, View view) {
        TextView textView = view.findViewById(R.id.row_item_tv_memorie);
        ArrayList<String> storagesList = new ArrayList<>();
        for (Integer storageValue : storagesInt) {
            String convertedNumber = String.valueOf(storageValue);
            storagesList.add(convertedNumber);
        }
        String storages = String.join(", ", storagesList);
        textView.setText(context.getString(R.string.ro_item_memorie_completation, calculateTextViewValue(storages)));
    }

    private void addColour(ArrayList<String> colour, View view) {
        TextView textView = view.findViewById(R.id.row_item_tv_culoare);
        String colours = String.join(", ", colour);
        textView.setText(calculateTextViewValue(colours));
    }
    private String calculateTextViewValue(String value) {
        if (value == null || value.isEmpty()) {
            return context.getString(R.string.lv_row_item_default_value);
        }

        return value;
    }

    public interface OnFavoriteButtonClickListener {
        void onFavoriteButtonClicked(Phone phone);
    }

}

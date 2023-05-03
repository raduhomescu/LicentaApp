package com.example.licentaapp.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.licentaapp.R;

import java.util.ArrayList;

public class SpecAdapter extends ArrayAdapter<String> {
    private Context context;
    private int resource;
    private LayoutInflater inflater;
    private ArrayList<String> specs;

    public SpecAdapter(@NonNull Context context, int resource, @NonNull ArrayList<String> objects, LayoutInflater inflater) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.specs = objects;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(resource, parent, false);
        String spec = specs.get(position);
        TextView textView = view.findViewById(R.id.row_item_specs_tv_titlu);
        textView.setText(calculateTextViewValue(spec));
        return view;
    }

    private String calculateTextViewValue(String value) {
        if (value == null || value.isEmpty()) {
            return context.getString(R.string.lv_row_item_default_value);
        }

        return value;
    }
}

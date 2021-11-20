package com.lomig.secretsanta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CountryAdapter extends ArrayAdapter<CountryRegion> {

    public CountryAdapter(Context context, ArrayList<CountryRegion> countryList){
        super(context, 0, countryList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent){
        if (convertView == null){
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.spinner_row, parent, false);
        }
        ImageView imageView = convertView.findViewById(R.id.spinnerImage);
        TextView textView = convertView.findViewById(R.id.spinnerText);

        CountryRegion currentCountry = getItem(position);

        imageView.setImageResource(currentCountry.getFlagImage());
        textView.setText(currentCountry.getCountryName());

        return convertView;
    }
}

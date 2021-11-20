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

public class LanguageAdapter extends ArrayAdapter<LanguageChoice> {

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    public LanguageAdapter(Context context, ArrayList<LanguageChoice> languageList){
        super(context, 0, languageList);
    }

    private View initView(int position, View convertView, ViewGroup parent){
        if (convertView == null){
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.spinner_row, parent, false);
        }
        ImageView imageView = convertView.findViewById(R.id.spinnerImage);
        TextView textView = convertView.findViewById(R.id.spinnerText);

        LanguageChoice currentLanguage = getItem(position);

        imageView.setImageResource(currentLanguage.getFlagImage());
        textView.setText(currentLanguage.getLanguageName());

        return convertView;
    }
}

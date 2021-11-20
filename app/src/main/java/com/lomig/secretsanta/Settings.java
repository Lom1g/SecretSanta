package com.lomig.secretsanta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toolbar;

import java.util.ArrayList;

public class Settings extends AppCompatActivity {

    private TextView textLanguage;
    private Spinner spinnerLanguage;
    private Toolbar toolbar;
    private ArrayList<LanguageChoice> languageChoiceArrayList;

    private LanguageAdapter languageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            setContentView(R.layout.activity_settings_vertical);
        }else if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            setContentView(R.layout.activity_settings_horizontal);
        }
        instanciateVaribale();
        initListLanguage();
        buildSpinner();
    }

    private void instanciateVaribale() {
        textLanguage = findViewById(R.id.textLanguage);
        spinnerLanguage = findViewById(R.id.spinnerLanguage);
        toolbar = findViewById(R.id.toolbarSettings);
        toolbar.setNavigationOnClickListener(view -> finish());
    }

    private void initListLanguage() {
        languageChoiceArrayList = new ArrayList<>();
        languageChoiceArrayList.add(new LanguageChoice("Francais",R.drawable.france));
        languageChoiceArrayList.add(new LanguageChoice("English",R.drawable.uk));
        languageChoiceArrayList.add(new LanguageChoice("Español",R.drawable.espagne));
    }

    private void buildSpinner() {
        languageAdapter = new LanguageAdapter(this, languageChoiceArrayList);
        spinnerLanguage.setAdapter(languageAdapter);
    }
}
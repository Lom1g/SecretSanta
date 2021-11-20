package com.lomig.secretsanta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;


import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity{

    private static final int FIRST_ITEM = 0;
    private static final String  TAG = "MainActivity";

    private RecyclerView recyclerView;
    private PlayerAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private Button buttonSend, buttonAdd;
    private EditText editTextNumberPhone, editTextPseudo;
    private Spinner spinner;
    private Toolbar toolbar;

    private ArrayList<Player> playerArrayList = new ArrayList<>();
    private ArrayList<Player> tmpPlayerArrayList;

    private Phonenumber.PhoneNumber phoneNumber;

    private ArrayList<CountryRegion> countryRegionArrayList;
    private CountryAdapter countryAdapter;

    private SmsManager smsManager;

    private static final int PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            setContentView(R.layout.activity_main_vertical);
        }else if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            setContentView(R.layout.activity_main_horizontal);
        }
        if(savedInstanceState!=null){retieveInstanceState(savedInstanceState);}
        initListCountries();
        instanciateVariables();
        buildRecycler();
        buildSpinner();
    }

    private void retieveInstanceState(Bundle savedInstanceState) {
        playerArrayList = savedInstanceState.getParcelableArrayList("playerArrayList");
    }


    private void initListCountries() {
        countryRegionArrayList = new ArrayList<>();
        countryRegionArrayList.add(new CountryRegion("FR",R.drawable.france));
        countryRegionArrayList.add(new CountryRegion("DE",R.drawable.allemagne));
        countryRegionArrayList.add(new CountryRegion("GB",R.drawable.uk));
        countryRegionArrayList.add(new CountryRegion("US", R.drawable.usa));
        countryRegionArrayList.add(new CountryRegion("IT",R.drawable.italie));
        countryRegionArrayList.add(new CountryRegion("CA",R.drawable.canada));
        countryRegionArrayList.add(new CountryRegion("ES",R.drawable.espagne));
        countryRegionArrayList.add(new CountryRegion("PT",R.drawable.portugal));
        countryRegionArrayList.add(new CountryRegion("CH", R.drawable.chine));
        countryRegionArrayList.add(new CountryRegion("IE",R.drawable.irlande));
        countryRegionArrayList.add(new CountryRegion("KR",R.drawable.korea));
        countryRegionArrayList.add(new CountryRegion("CH",R.drawable.chine));
        countryRegionArrayList.add(new CountryRegion("JP",R.drawable.japon));
    }

    private void instanciateVariables() {
        smsManager = SmsManager.getDefault();

        editTextNumberPhone = findViewById(R.id.editTextNumberPhone);
        editTextPseudo = findViewById(R.id.editTextPseudo);

        buttonSend = findViewById(R.id.buttonSend);
        buttonSend.setOnClickListener(view -> sendSMS());

        buttonAdd = findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(view -> addPlayer());

        toolbar = findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            if(id == R.id.itemSettings){
                Intent intentSettings = new Intent(this, Settings.class);
                startActivity(intentSettings);
            }
            return true;
        });

        spinner = findViewById(R.id.spinnerCountries);
    }

    private void buildRecycler() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);

        adapter = new PlayerAdapter(playerArrayList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnMenuItemClickListener((position, item) -> {
            int id = item.getItemId();
            if(id == R.id.itemDeletePlayer){
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(R.string.titleDelete)
                        .setMessage(R.string.descriptionDelete)
                        .setPositiveButton(R.string.positiveDelete, (dialogInterface, i) -> removePlayer(position))
                        .setNegativeButton(R.string.negativeDelete, null)
                        .show();
            }else if(id == R.id.itemInfoPlayer){
                showInfoPlayer(position);
            }
        });
    }

    private void buildSpinner() {
        countryAdapter = new CountryAdapter(this, countryRegionArrayList);
        spinner.setAdapter(countryAdapter);
    }

    private void addPlayer() {
        if (checkDataPlayer()) {
            String number = "+" + phoneNumber.getCountryCode() + phoneNumber.getNationalNumber();
            Log.d(TAG,number);
            playerArrayList.add(FIRST_ITEM,new Player(editTextPseudo.getText().toString(),number));
            editTextPseudo.getText().clear();
            editTextNumberPhone.getText().clear();
            adapter.notifyItemInserted(FIRST_ITEM);
            recyclerView.scrollToPosition(FIRST_ITEM);
        }
    }

    private boolean checkDataPlayer() {
        if(editTextPseudo.getText().toString().isEmpty()){
            Toast.makeText(this,R.string.pseudoEmpty,Toast.LENGTH_LONG).show();
            return false;
        }else if(editTextNumberPhone.getText().toString().isEmpty()){
            Toast.makeText(this,R.string.phoneNumberEmpty,Toast.LENGTH_LONG).show();
            return false;
        }
        PhoneNumberUtil numberUtil = PhoneNumberUtil.getInstance();
        phoneNumber = null;
        try {
            CountryRegion currentRegion = (CountryRegion) spinner.getSelectedItem();
            phoneNumber = numberUtil.parse(editTextNumberPhone.getText().toString(),currentRegion.getCountryName());
        } catch (NumberParseException e) {
            e.printStackTrace();
        }
        boolean isValid = numberUtil.isValidNumber(phoneNumber);
        if(!isValid){
            Toast.makeText(this,R.string.phoneNumberUnvalid,Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void removePlayer(int position) {
        playerArrayList.remove(position);
        adapter.notifyItemRemoved(position);
    }


    private void showInfoPlayer(int position) {
        PlayerDialog playerDialog = new PlayerDialog(this);
        playerDialog.setPseudo(playerArrayList.get(position).getPseudo());
        playerDialog.setPhoneNumber(playerArrayList.get(position).getPhoneNumber());
        playerDialog.build();
    }

    private void sendSMS() {
        if(checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_DENIED){
            String[] permissions = {Manifest.permission.SEND_SMS};
            requestPermissions(permissions, PERMISSION_REQUEST_CODE);
        }
        else{
            tmpPlayerArrayList = (ArrayList<Player>) playerArrayList.clone();
            Collections.shuffle(tmpPlayerArrayList);
            for(int i = 0; i<tmpPlayerArrayList.size(); i++){
                if(i!=tmpPlayerArrayList.size()-1){
                    smsManager.sendTextMessage(playerArrayList.get(i).getPhoneNumber(), null,
                        "C'est à " + playerArrayList.get(i+1).getPseudo().toUpperCase() + " que tu offres un cadeau.",
                        null, null);
                }else{
                    smsManager.sendTextMessage(playerArrayList.get(i).getPhoneNumber(), null,
                        "C'est à " + playerArrayList.get(FIRST_ITEM).getPseudo().toUpperCase() + " que tu offres un cadeau.",
                        null, null);
                }

            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("playerArrayList", playerArrayList);
    }
}
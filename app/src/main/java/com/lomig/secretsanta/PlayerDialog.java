package com.lomig.secretsanta;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;


public class PlayerDialog extends Dialog {

    private TextView textPseudo, textPhoneNumber;
    private String pseudo, phoneNumber;

    public PlayerDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.dialog_player);
        this.textPseudo = findViewById(R.id.textDialogPseudo);
        this.textPhoneNumber = findViewById(R.id.textDialogPhoneNumber);
        this.pseudo = "pseudo";
        this.phoneNumber = "Phone number";
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void build() {
        textPseudo.setText(pseudo);
        textPhoneNumber.setText(phoneNumber);
        show();
    }
}

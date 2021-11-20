package com.lomig.secretsanta;


import android.os.Parcel;
import android.os.Parcelable;

public class Player implements Parcelable {
    private String pseudo, phoneNumber;

    public Player(String pseudo, String phoneNumber){
        this.pseudo = pseudo;
        this.phoneNumber = phoneNumber;
    }

    protected Player(Parcel in) {
        pseudo = in.readString();
        phoneNumber = in.readString();
    }

    public static final Creator<Player> CREATOR = new Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(pseudo);
        parcel.writeString(phoneNumber);
    }
}

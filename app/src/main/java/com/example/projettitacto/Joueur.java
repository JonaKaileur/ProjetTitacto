package com.example.projettitacto;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Joueur implements Parcelable {

    public static final Creator<Joueur> CREATOR = new Creator<Joueur>() {
        @Override
        public Joueur createFromParcel(Parcel in) {
            return new Joueur();
        }

        @Override
        public Joueur[] newArray(int size) {
            return new Joueur[size];
        }
    };

    public Joueur() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScrore() {
        return scrore;
    }

    public void setScrore(int scrore) {
        this.scrore = scrore;
    }

    private int id;
    private String name;
    private int  scrore;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {

    }
}

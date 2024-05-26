package com.example.projettitacto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ListJoueurAdapter extends ArrayAdapter<Joueur> {
    public ArrayList<Joueur> JoueurListArrayList;

    public ListJoueurAdapter(Context context,ArrayList<Joueur> joueurListArrayList){
        super(context,0,joueurListArrayList);
        this.JoueurListArrayList = joueurListArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        return initView(position,convertView,parent);
    }

    private View initView(int position, View convertView, ViewGroup parent){
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.activity_player_setup2,parent,false
            );
        }
        TextView textViewListNomJoueur = convertView.findViewById(R.id.Player1);
        Joueur currentListJoueur = getItem(position);

        if (currentListJoueur != null){
            textViewListNomJoueur.setText(currentListJoueur.getName());
        }
        return convertView;
    }

}

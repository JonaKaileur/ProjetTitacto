package com.example.projettitacto;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class JoueurActivity extends AppCompatActivity {
    private ListView listViewJoueur;
    private ArrayList<Joueur> listOfJoueur = new ArrayList<>();
    private DatabaseManager databaseManager;
    private ListJoueurAdapter listJoueurAdapter;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_joueur);
        databaseManager = new DatabaseManager(getApplicationContext());

        listViewJoueur = findViewById(R.id.ListViewJeueur);

        listOfJoueur = databaseManager.getAllJoueur();

        listViewJoueur.setAdapter(listJoueurAdapter);


    }
}
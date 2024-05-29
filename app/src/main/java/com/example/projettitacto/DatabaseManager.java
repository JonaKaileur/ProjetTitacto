package com.example.projettitacto;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseManager extends SQLiteOpenHelper{
    private static final  String DATABASE_NAME = "ListJoueur.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseManager(Context context){
        super(context,DATABASE_NAME, null , DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String strSql = "create table ListJoueur("
                +"id integer primary key autoincrement,"
                +"name text not null,"
                +"score integer"
                +")";
        db.execSQL(strSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public ArrayList<Joueur> getAllJoueur(){
        ArrayList<Joueur> listJoueur = new ArrayList<Joueur>();
        String strsql ="select * from ListJoueur";
        Cursor cursor = this.getWritableDatabase().rawQuery(strsql,null);
        if(cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String NomJoueur = cursor.getString(cursor.getColumnIndex("name"));
                int scroreJ = cursor.getInt(cursor.getColumnIndex("scrore"));

                Joueur ListJoueurObj = new Joueur();
                ListJoueurObj.setId(id);
                ListJoueurObj.setName(NomJoueur);
                ListJoueurObj.setScrore(scroreJ);

            }
        }
        return listJoueur;
    }

    public void insertJoueurList(String listJoueur){
        String name = listJoueur.replace("'","((%))");
        String strSql = "INSERT INTO ListJoueur"
                +"(name) VALUE(' "
                + name + "')";
        this.getWritableDatabase().execSQL(strSql);
    }
}

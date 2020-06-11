package com.medvapsi.medvapsi;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class nav_activity extends AppCompatActivity {


    final SellingFragment fragment_sell = new SellingFragment ();
    final Fragment_Profile fragment_profile = new Fragment_Profile ();
    final Fragment_about fragment_about = new Fragment_about ();
    final FragmentManager fm = getSupportFragmentManager ();
    Fragment active = fragment_sell;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_nav);
        BottomNavigationView navView = findViewById (R.id.nav_view);
        navView.setOnNavigationItemSelectedListener (itemSelectedListener);

        fm.beginTransaction ().add (R.id.fragment_container, fragment_sell).commit ();
        fm.beginTransaction ().add (R.id.fragment_container, fragment_profile).hide (fragment_profile).commit ();
        fm.beginTransaction ().add (R.id.fragment_container, fragment_about).hide (fragment_about).commit ();
    }



    private BottomNavigationView.OnNavigationItemSelectedListener itemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener () {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            switch (menuItem.getItemId ()) {
                case R.id.navigation_home:
                    fm.beginTransaction ().hide (active).show (fragment_sell).commit ();
                    active = fragment_sell;
                    return true;

                case R.id.navigation_about:
                    fm.beginTransaction ().hide (active).show (fragment_about).commit ();
                    active = fragment_about;
                    return true;

                case R.id.navigation_profile:
                    fm.beginTransaction ().hide (active).show (fragment_profile).commit ();
                    active = fragment_profile;
                    return true;


            }
            return false;
        }
    };

    public void dbupload(String med, String company, String datee , String type , String form){

        SQLiteDatabase db;
        db = openOrCreateDatabase ("data", MODE_PRIVATE, null);


        String query = "CREATE TABLE IF NOT EXISTS medicines(drug VARCHAR,company VARCHAR,data VARCHAR,type VARCHAR, form VARCHAR);";
        db.execSQL (query);

        String put = "INSERT INTO medicines VALUES(\'" + med + "\',\'" + company + "\',\'" + datee + "\',\'" + type + "\' , \'" + form + "\');";
        db.execSQL (put);

    }

    public String[] dbdownload(String email){
        SQLiteDatabase db;
        String[] info = new String[0];
        db = openOrCreateDatabase ("data", MODE_PRIVATE, null);
        String query = "CREATE TABLE IF NOT EXISTS info (name VARCHAR(15), mobile VARACHAR(11),email VARCHAR(50));";
        db.execSQL (query);
        String query2 = "SELECT * FROM info WHERE email = \'"+ email + "\';";
        Cursor cursor = db.rawQuery (query2, null);
        if(cursor.getCount ()>0){
            cursor.moveToFirst ();
            do{
                 info = new String[]{
                         cursor.getString (0),
                         cursor.getString (1),
                 };

            }while(cursor.moveToNext ());

        }
        return info;
    }

}

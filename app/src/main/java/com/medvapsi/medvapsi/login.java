package com.medvapsi.medvapsi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class login extends AppCompatActivity {
    EditText email, password;
    Button login;
    FloatingActionButton signup;
    SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_login);

        email = findViewById (R.id.email);
        password = findViewById (R.id.Password);
        login = findViewById (R.id.emailSignInButton);
        signup = findViewById (R.id.createaccount);


        signup.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (login.this, signup.class);
                startActivity (intent);
            }
        });


        login.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                loginnow();
            }
        });
    }

    private boolean validateform(String id, String pass) {
            boolean valid = true;

        if (TextUtils.isEmpty(id)) {
            email.setError("Required.");
            valid = false;
        } else {
            email.setError(null);
        }

        if (TextUtils.isEmpty(pass)) {
            password.setError("Required.");
            valid = false;
        } else if (password.length() < 8) {
            password.setError("Minimum: 8 Characters");
        } else {
            password.setError(null);
        }

        return valid;
    }

    private void loginnow(){

        String id = email.getText ().toString ().trim ();
        String pass = password.getText ().toString ().trim();
        SharedPreferences sharedPreferences = getSharedPreferences ("status",MODE_PRIVATE);
        SharedPreferences.Editor editor;


        if(validateform(id,pass)) {


            db = openOrCreateDatabase ("data", MODE_PRIVATE, null);
            String query = "CREATE TABLE IF NOT EXISTS users(userid VARCHAR(50),password VARCHAR(20));";
            db.execSQL (query);

            String selectString = "SELECT * FROM users WHERE userid = \'" + id + "\' AND password = \'" + pass + "\';";
            Cursor cursor = db.rawQuery (selectString, null);
            if (cursor.getCount () > 0) {
                Intent intent = new Intent (login.this, nav_activity.class);
                editor = sharedPreferences.edit ();
                editor.putString ("status", "in");
                editor.putString ("email", id);
                editor.apply ();
                intent.putExtra ("email", id);
                startActivity (intent);
                cursor.close ();
                finish ();
            }
            else{
                Toast.makeText (this, "Error: Try Again", Toast.LENGTH_SHORT).show ();
                cursor.close ();
            }
        }
    }
}

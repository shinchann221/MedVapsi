package com.medvapsi.medvapsi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class signup extends AppCompatActivity {
    private EditText fname,lname,mobile,email,password;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_signup);

        fname = findViewById ( R.id.fname);
        lname = findViewById (R.id.lname);
        mobile = findViewById (R.id.mobile);
        email = findViewById (R.id.email_register);
        password = findViewById (R.id.password_register);

        Button register = findViewById (R.id.submit);
        progressBar = findViewById (R.id.progressBar);

        register.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {

               boolean ans = createaccount ();
               if(ans){

                   Intent intent = new Intent (signup.this,nav_activity.class);
                   intent.putExtra ("email", email.getText ().toString ());
                   startActivity (intent);
                   finish ();

               }
            }
        });

    }


    private boolean createaccount(){

        boolean yes = false;
        String TAG = "SIGNUP";
        Log.d (TAG, "createaccount: CLICK HUA HAI");
        final SharedPreferences sharedPreferences = getSharedPreferences ("status",MODE_PRIVATE);

        SharedPreferences.Editor editor;

        editor = sharedPreferences.edit ();

        if(validateform()){


            Log.d (TAG, "createaccount: yahan tak bhi chal gya");
            String first = fname.getText ().toString ();
            String last = lname.getText ().toString ();
            String num =mobile.getText ().toString ();
            String id = email.getText ().toString ();
            String pass = password.getText ().toString ();

            SQLiteDatabase db = openOrCreateDatabase ("data", MODE_PRIVATE, null);
                    String query = "CREATE TABLE IF NOT EXISTS users(userid VARCHAR(50), password VARCHAR(20));";
                    db.execSQL (query);
            Log.d (TAG, "createaccount: query 1");
                    String put = "INSERT INTO users VALUES( \'"+id + "\',\'"+pass+"\');";
                    db.execSQL (put);
            Log.d (TAG, "createaccount: query 2");
                    String table2 = "CREATE TABLE IF NOT EXISTS info (name VARCHAR(15), mobile VARCHAR(11),email VARCHAR(50))";
                    db.execSQL (table2);
            Log.d (TAG, "createaccount: query 3");
                    String put2 = "INSERT INTO info VALUES(\'"+ first +" "+ last +"\',\'"+ num +"\',\'"+ id+"\');";
                    db.execSQL (put2);
            Log.d (TAG, "createaccount: query 4");
                    Toast.makeText (signup.this, "User Registered", Toast.LENGTH_SHORT).show ();

            editor.putString ("status", "in");
            editor.putString ("email", id);
            editor.apply ();
            yes = true;

        }

        return yes;
    }

    private boolean validateform(){
        boolean valid = true;


        //firstname
        String fn = fname.getText().toString().trim ();
        if (TextUtils.isEmpty(fn)){
            fname.setError("Required");
            valid = false;
        }else fname.setError(null);


        //lastname
        String ln = lname.getText().toString().trim ();
        if(TextUtils.isEmpty(ln)){
            lname.setError("Required");
            valid = false;
        }else lname.setError(null);


        //mobile number
        String mob = mobile.getText().toString().trim ();
        if(TextUtils.isEmpty(mob) || mob.length()<10 || mob.length()>10){
            mobile.setError("Required");
            valid = false;
        }else mobile.setError(null);


        String id = email.getText ().toString ().trim ();
        if (TextUtils.isEmpty (id)) {
            email.setError ("Required");
            valid = false;
        }else email.setError (null);

        String pass = password.getText ().toString ();
        if (TextUtils.isEmpty (pass)){
            password.setError ("Requried");
            valid = false;
        }else password.setError (null);


        return valid;
    }
}


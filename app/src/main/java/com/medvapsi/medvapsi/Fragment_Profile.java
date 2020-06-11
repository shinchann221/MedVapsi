package com.medvapsi.medvapsi;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class Fragment_Profile extends Fragment {
    TextView name,number;



    public Fragment_Profile() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate (R.layout.fragment_profile, container,false);
        Button btn = view.findViewById (R.id.btn_logout);
        name = view.findViewById (R.id.txt_user);
        number = view.findViewById (R.id.txt_mobile);
        SharedPreferences sharedPreferences = getActivity ().getSharedPreferences ("status", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString ("email", "");


        nav_activity activity = (nav_activity)getActivity ();
        String[] info = activity.dbdownload (email);
        if (info.length <= 1){
            Toast.makeText (activity, "error", Toast.LENGTH_SHORT).show ();
        }else{
            String name1 = info[0];
            String num1 = info[1];
            name.setText (name1);
            number.setText (num1);

        }
        btn.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = Objects.requireNonNull (getActivity ()).getSharedPreferences ("status", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor= sharedPreferences.edit ();
                editor.putString ("status", "out");
                editor.putString ("email", "null");
                editor.apply ();

                Intent intent = new Intent (getActivity (), first_screen.class);
                startActivity (intent);
            }
        });

        return view;

    }

}

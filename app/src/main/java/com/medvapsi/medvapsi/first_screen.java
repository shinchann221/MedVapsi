package com.medvapsi.medvapsi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class first_screen extends AppCompatActivity {
    Button btn;
    private ViewPager viewPager;
    private viewpageradapter myadapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_first_screen);

        final SharedPreferences sharedPreferences = getSharedPreferences ("status",MODE_PRIVATE);
        String status = sharedPreferences.getString ("status", "");
        if ( status.equals ("in")){
            String email = sharedPreferences.getString ("email", "");
            Intent intent = new Intent (first_screen.this, nav_activity.class);
            intent.putExtra ("email", email);
            startActivity (intent);
            finish ();
        }
        btn = findViewById (R.id.btn_continue);
        viewPager = findViewById (R.id.viewpager);
        myadapter = new viewpageradapter (this);
        viewPager.setAdapter (myadapter);
        viewPager.addOnPageChangeListener (new ViewPager.OnPageChangeListener () {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if(position == 2){
                    btn.setVisibility (View.VISIBLE);
                }else{
                    btn.setVisibility (View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        btn.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (first_screen.this, login.class);
                startActivity (intent);
                finish ();
            }
        });
    }
}

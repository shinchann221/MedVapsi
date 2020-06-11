package com.medvapsi.medvapsi;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;


public class viewpageradapter extends PagerAdapter {
    Context context;
    LayoutInflater inflater;

    viewpageradapter( Context context){
        this.context = context;
    }

    //List of images
    public int[] list_image = {
            R.drawable.med,
            R.drawable.set,
            R.drawable.go
    };

    //List of text
    public String[] list_text = {
            "MED",
            "SET",
            "GO"
    };

    // list of background colors
    public int[] bgcolor = {
            Color.rgb (252, 223, 131),
            Color.rgb (255, 192, 192),
            Color.rgb (255, 255, 255)
    };

    @Override
    public int getCount() {
        return bgcolor.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = (LayoutInflater) context.getSystemService (Context.LAYOUT_INFLATER_SERVICE);
         View view = inflater.inflate (R.layout.slideviewpager,container,false);
        LinearLayout layoutslide = view.findViewById (R.id.slidelinearlayout);
        ImageView imageslide = view.findViewById (R.id.image_view);
        TextView titletxt = view.findViewById (R.id.title_txt);
        layoutslide.setBackgroundColor (bgcolor[position]);
        imageslide.setImageResource (list_image[position]);
        titletxt.setText (list_text[position]);
        container.addView (view);
        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView ((LinearLayout) object);
    }
}

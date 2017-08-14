package com.crash.boozl.boozl.code.Alcohols;


import android.content.Context;
import android.graphics.drawable.Drawable;

import com.crash.boozl.boozl.code.Alcohol;
import com.crash.boozl.boozl.code.R;

public class Whisky extends Alcohol {

    // Irish Whiskey, Tennessee, etc
    private String type;
    private Context context;


    public Whisky(String alcohol_percentage, String brand, String description, String name, String type, Context context) {
        super(alcohol_percentage, brand, description, type, name, context.getResources().getDrawable(R.drawable.whisky_icon));

        this.type = type;
        this.context = context;


    }
}

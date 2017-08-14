package com.crash.boozl.boozl.code.Alcohols;


import android.content.Context;
import android.graphics.drawable.Drawable;

import com.crash.boozl.boozl.code.Alcohol;
import com.crash.boozl.boozl.code.R;

public class Vodka extends Alcohol {

    private Context context;
    private String type;


    public Vodka(String alcohol_percentage, String brand, String description, String name, String type, Context context) {
        super(alcohol_percentage, brand, description, type, name, context.getResources().getDrawable(R.drawable.vodka_icon));

        this.context = context;
        this.type = type;


    }
}

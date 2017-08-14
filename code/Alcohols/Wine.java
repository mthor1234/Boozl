package com.crash.boozl.boozl.code.Alcohols;

import android.content.Context;

import com.crash.boozl.boozl.code.Alcohol;
import com.crash.boozl.boozl.code.R;

public class Wine extends Alcohol {

    private String description;    // Writeup describing the alcohol.. i.e. the companies product description when trying to sell the alcohol
    private String type;           // Merlot, Chardonnay, etc.

    private int year;               // Optional.. Really only pertains to alcohols that year is important such as Wine, Whisky, or Gin?

    private Context context;        // Used to get the alcohol image


    public Wine(String alcohol_percentage, String brand, String description, String name, String type, Context context) {
        super(alcohol_percentage, brand, description, type, name, context.getResources().getDrawable(R.drawable.wine_icon));
        this.description = description;

        this.context = context;

    }
}

package com.crash.boozl.boozl.code;

import android.graphics.drawable.Drawable;

public class Alcohol {

    String name;    // Budweiser, Bud Light, Franzia, etc.
    String brand;   // anheuser busch.... etc
    String type;    // IPA, Merlot, Tennessee Whiskey, etc.
    String alcohol_percentage;
    String description;
    Drawable image;

    String ounces;



    public Alcohol(String alcohol_percentage, String brand, String description, String type, String name, Drawable image) {
        this.alcohol_percentage = alcohol_percentage;
        this.brand = brand;
        this.type = type;
        this.name = name;
        this.image = image;
        this.description = description;
        this.ounces = ounces;
    }

}

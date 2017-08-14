package com.crash.boozl.boozl.code;

// Deal class is created to handle all the 'Deals'. It holds the store and alcohol data types
// The "results" listview holds the deals and all the information about each deal can be retrieved
// By using the 'Deal' data type

import android.graphics.drawable.Drawable;

import com.google.android.gms.maps.model.LatLng;

public class Deal {

    private String ID;
    private Alcohol alcohol;        // Alcohol data type... can retrieve important alcohol details

    private boolean is_Cans;        // If the beer comes in cans or bottles

    private String alcohol_name;    // Budweiser, Franzia, etc.
    private String description;     // Budweiser is an all american drink known for it's.....
    private Store store;            // Store data type... can retrieve important store details
    private String size;            // The size of the alcohol
    private String type;            // Tennessee whiskey, IPA, etc
    private String alcohol_percentage;
    private String mililiters;          // Size of the can/bottle in ml


    private String distance_from_user;
    private String price;           // The price of the deal


    private LatLng latLng;

    private Drawable alcohol_image;


    public Deal(Alcohol alcohol,String ID, String price, String size, Store store, String mililiters) {
        this.alcohol = alcohol;
        this.alcohol_image = alcohol.image;
        this.description = alcohol.description;
        this.alcohol_name = alcohol.name;
        this.distance_from_user = distance_from_user;
        this.ID = ID;
        this.price = price;
        this.size = size;
        this.store = store;
        this.type = alcohol.type;
        this.alcohol_percentage = alcohol.alcohol_percentage;
        this.latLng = store.getLatlng();
        this.mililiters = mililiters;

        store.addCurrentDeal(this);
    }


    public String getAlcohol_name() {
        return alcohol_name;
    }

    public void setAlcohol_name(String alcohol_name) {
        this.alcohol_name = alcohol_name;
    }

    public boolean is_Cans() {
        return is_Cans;
    }

    public void setIs_Cans(boolean is_Cans) {
        this.is_Cans = is_Cans;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Alcohol getAlcohol() {
        return alcohol;
    }

    public void setAlcohol(Alcohol alcohol) {
        this.alcohol = alcohol;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Drawable getAlcohol_image() {
        return alcohol_image;
    }

    public void setAlcohol_image(Drawable alcohol_image) {
        this.alcohol_image = alcohol_image;
    }

    public String getDistance_from_user() {
        return distance_from_user;
    }

    public void setDistance_from_user(String distance_from_user) {
        this.distance_from_user = distance_from_user;
    }


    public String getAlcohol_percentage() {
        return alcohol_percentage;
    }

    public void setAlcohol_percentage(String alcohol_percentage) {
        this.alcohol_percentage = alcohol_percentage;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public String getMililiters() {
        return mililiters;
    }

    public void setmililiters(String mililiters) {
        this.mililiters = mililiters;
    }
}



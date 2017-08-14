package com.crash.boozl.boozl.code;


import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class Store {

    private String store_name;
    private String store_description;
    private String address;             // The whole store address.. i.e "1 Main St., Hartford, CT 06084"
    private String street_address;      // Just the street name... i.e "1 Main St."
    private String city_zip;            // Just the City, State, Zip.... i.e "Hartford, CT 06084"
    private String phone_number;        // The stores phone number... i.e "(860) 555-4123"

    private String monday_hours;
    private String tuesday_hours;
    private String wednesday_hours;
    private String wed;
    private String thursday_hours;
    private String fri;
    private String sat;
    private String sun;
    private String friday_hours;
    private String saturday_hours;
    private String sunday_hours;
    private String store_ID;
    private Location store_loc;


    private double rating;

    private boolean carry_beer;         // Does the store carry beer?
    private boolean carry_wine;         // Does the store carry wine?
    private boolean carry_liquor;       // Does the store carry liquor

    private ArrayList<Deal> current_Deals = new ArrayList<Deal>();
    private ArrayList<Deal> expired_Deals;  // Necessary? Maybe good for documenting



    private LatLng latlng;    // Use when I set up the lat/lng from Google Maps V2


    public Store() {

    }

    public Store(String store_name) {
        this.store_name = store_name;
    }


    public Store(String store_name, String street_address, String city_zip, String phone_number) {
        this.store_name = store_name;
        this.street_address = street_address;
        this.phone_number = phone_number;
        this.city_zip = city_zip;
    }

    public Store(String ID, String address, boolean carry_beer, boolean carry_liquor, boolean carry_wine, String phone_number, String store_name, LatLng latlng, String monday_hours, String tuesday_hours, String wednesday_hours, String thursday_hours, String friday_hours, String saturday_hours, String sunday_hours) {
        store_ID = ID;
        this.address = address;
        this.carry_beer = carry_beer;
        this.carry_liquor = carry_liquor;
        this.carry_wine = carry_wine;
        this.phone_number = phone_number;
        this.store_name = store_name;
        this.monday_hours = monday_hours;
        this.tuesday_hours = tuesday_hours;
        this.wednesday_hours = wednesday_hours;
        this.thursday_hours = thursday_hours;
        this.friday_hours = friday_hours;
        this.saturday_hours = saturday_hours;
        this.sunday_hours = sunday_hours;
        this.latlng = latlng;

        setStreet_address(address.substring(0, address.indexOf(",", 0)));
        setCity_zip(address.substring(address.indexOf(",", 0) + 2, address.length() - 1));

        store_loc = new Location(getStore_name());

        store_loc.setLatitude(latlng.latitude);
        store_loc.setLongitude(latlng.longitude);


    }


    // Probably needs work and is most likely subjected to errors
    // Set the store hours... i.e setStoreHours("10am - 9pm", "10am - 9pm" ....... );
    public void setStoreHours(String mon, String tues, String wed, String thurs, String fri, String sat, String sun){
        this.monday_hours = mon;
        this.tuesday_hours = tues;
        this.wednesday_hours = wed;
        this.thursday_hours = thurs;
        this.friday_hours = fri;
        this.saturday_hours = sat;
        this.sunday_hours = sun;
    }


    public String getFri() {
        return fri;
    }

    public String getFriday_hours() {
        return friday_hours;
    }

    public String getMonday_hours() {
        return monday_hours;
    }

    public String getSat() {
        return sat;
    }

    public String getSaturday_hours() {
        return saturday_hours;
    }

    public String getSun() {
        return sun;
    }

    public String getSunday_hours() {
        return sunday_hours;
    }

    public String getThursday_hours() {
        return thursday_hours;
    }

    public String getTuesday_hours() {
        return tuesday_hours;
    }

    public String getWed() {
        return wed;
    }

    public String getWednesday_hours() {
        return wednesday_hours;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isCarry_beer() {
        return carry_beer;
    }

    public void setCarry_beer(boolean carry_beer) {
        this.carry_beer = carry_beer;
    }

    public boolean isCarry_liquor() {
        return carry_liquor;
    }

    public void setCarry_liquor(boolean carry_liquor) {
        this.carry_liquor = carry_liquor;
    }

    public boolean isCarry_wine() {
        return carry_wine;
    }

    public void setCarry_wine(boolean carry_wine) {
        this.carry_wine = carry_wine;
    }

    public ArrayList<Deal> getCurrent_Deals() {
        return current_Deals;
    }

    public void setCurrent_Deals(ArrayList<Deal> current_Deals) {
        this.current_Deals = current_Deals;
    }

    public ArrayList<Deal> getExpired_Deals() {
        return expired_Deals;
    }

    public void setExpired_Deals(ArrayList<Deal> expired_Deals) {
        this.expired_Deals = expired_Deals;
    }

    public LatLng getLatlng() {
        return latlng;
    }

    public void setLatlng(LatLng latlng) {
        this.latlng = latlng;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getStore_description() {
        return store_description;
    }

    public void setStore_description(String store_description) {
        this.store_description = store_description;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getCity_zip() {
        return city_zip;
    }

    public void setCity_zip(String city_zip) {
        this.city_zip = city_zip;
    }

    public String getStreet_address() {
        return street_address;
    }

    public void setStreet_address(String street_address) {
        this.street_address = street_address;
    }

    public String getStore_ID() {
        return store_ID;
    }

    public void setStore_ID(String store_ID) {
        this.store_ID = store_ID;
    }

    public void addCurrentDeal(Deal deal){
        current_Deals.add(deal);
    }

    public Location getStore_loc() {
        return store_loc;
    }

    public void setStore_loc(Location store_loc) {
        this.store_loc = store_loc;
    }
}



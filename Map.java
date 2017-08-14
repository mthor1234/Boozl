package com.crash.boozl.boozl.code;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;


public class Map extends Fragment implements GoogleMap.OnInfoWindowClickListener{

    private static View view;
    /**
     * Note that this may be null if the Google Play services APK is not
     * available.
     */

    private static GoogleMap mMap;
    private static Double latitude, longitude;

    private Search search;

    private HashMap<Marker, Deal> marker_hash;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        view = (RelativeLayout) inflater.inflate(R.layout.fragment_map, container, false);
        // Passing harcoded values for latitude & longitude. Please change as per your need. This is just used to drop a Marker on the Map

        latitude = 41.879766;
        longitude = -72.362155;

        marker_hash = new HashMap<>();

        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setUpMapIfNeeded();

        search.activity_description.setText("Results");


        showDeals();
    }


    /***** Sets up the map if it is possible to do so *****/
    public void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((MapFragment) search.fragment_manager
                    .findFragmentById(R.id.location_map)).getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null)
                setUpMap();
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the
     * camera.
     * <p>
     * This should only be called once and when we are sure that {@link #mMap}
     * is not null.
     */
    private static void setUpMap() {


        mMap.getUiSettings().setCompassEnabled(false);
//        mMap.getUiSettings().setMapToolbarEnabled(false);


        // For showing a move to my loction button
        mMap.setMyLocationEnabled(false);
        // For dropping a marker at a point on the Map
        mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("You"));
        // For zooming automatically to the Dropped PIN Location
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,
                longitude), 12.0f));


    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        if (mMap != null)
            setUpMap();

        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((MapFragment) search.fragment_manager
                    .findFragmentById(R.id.location_map)).getMap(); // getMap is deprecated
            // Check if we were successful in obtaining the map.
            if (mMap != null)
                setUpMap();
        }
    }

    /**** The mapfragment's id must be removed from the FragmentManager
     **** or else if the same it is passed on the next time then
     **** app will crash ****/
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mMap != null) {
            Search.fragment_manager.beginTransaction()
                    .remove(search.fragment_manager.findFragmentById(R.id.location_map)).commit();
            mMap = null;
        }
    }


    public Search getSearch() {
        return search;
    }

    public void setSearch(Search search) {
        this.search = search;
    }

    public void showDeals(){

        mMap.setOnInfoWindowClickListener(this);


        mMap.clear();
        marker_hash.clear();


        mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("You"));

        // Loop through every deal
        for(int i = 0; i < search.adapter.result.size(); i++){

            // Add markers on the map for each deal location... Title the marker as the storename and the snippet as the alcohol name... Need to limit markers to only one if theres multiple in one location
            if(search.adapter.result.get(i).getLatLng() != null) {

                MarkerOptions temp_marker_options = new MarkerOptions().position(new LatLng(search.adapter.result.get(i).getLatLng().latitude, search.adapter.result.get(i).getLatLng().longitude)
                ).title(search.adapter.result.get(i).getStore().getStore_name()).snippet(search.adapter.result.get(i).getAlcohol_name());


                Marker tempMark = mMap.addMarker(temp_marker_options);

                marker_hash.put(tempMark, search.adapter.result.get(i));

            }
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

        Fragment_Deal_Info deal_Fragment = new Fragment_Deal_Info();

        Deal deal = marker_hash.get(marker);

        FragmentTransaction transaction = search.fragment_manager.beginTransaction();
        transaction.replace(R.id.fragment_holder, deal_Fragment);
        transaction.addToBackStack(null);
        transaction.commit();

        deal_Fragment.setActivity_search(search);
        deal_Fragment.setDeal(deal);

    }



}



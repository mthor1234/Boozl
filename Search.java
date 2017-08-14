package com.crash.boozl.boozl.code;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.crash.boozl.boozl.code.Alcohols.Beer;
import com.crash.boozl.boozl.code.Alcohols.Gin;
import com.crash.boozl.boozl.code.Alcohols.Rum;
import com.crash.boozl.boozl.code.Alcohols.Tequila;
import com.crash.boozl.boozl.code.Alcohols.Vodka;
import com.crash.boozl.boozl.code.Alcohols.Whisky;
import com.crash.boozl.boozl.code.Alcohols.Wine;
import com.google.android.gms.maps.model.LatLng;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Search extends FragmentActivity {

    private DrawerLayout mDrawerLayout;
    RelativeLayout mDockLayout;
    ImageView search_menu, searchCancel;
    ListView lv_Results;

    ImageButton menu;

    // Gridview variables
    GridView gv_alcoholType;

    RadioGroup rg_display, rg_sort;

    Switch switch_list_map;

    Search search;

    TextView activity_description;

    Button btn_network_status, btn_location_status;

    Location userLocation = null;
    LocationHandler myLocation;

    public boolean is_Connected_To_Internet = false;
    boolean mIsReceiverRegistered_Internet = false;
    InternetBroadcastReceiver mReceiver_Internet = null;
    InternetHandler internet;


    boolean mIsReceiverRegistered = false;
    LocationBroadcastReceiver mReceiver = null;


    final ArrayList<Deal> al_testDeals = new ArrayList<Deal>();

    public static FragmentManager fragment_manager;

    Map map;

    String LOG_TAG = Search.class.getSimpleName();

    Fragment_Deal_Info fragment_deal_info;
    Fragment_Store_Info fragment_store_info;


    AutoCompleteTextView autocompletetv_Search;

    // Used to populate each different gridview on the DrawerLayout
    ArrayList<Item> gridItems_alcoholType = new ArrayList<Item>();

    ArrayList<Store> al_Stores = new ArrayList<Store>();
    ArrayList<ParseObject> storeHolder = new ArrayList<ParseObject>();


    CustomListViewAdapter adapter;

    // Creates a custom GridView adapter that is applied to each adapter
    CustomGridViewAdapter customGridViewAdapter_Alcohol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (savedInstanceState == null) {
            System.out.println("No saved instance");
            fragment_deal_info = new Fragment_Deal_Info();
            fragment_deal_info.setActivity_search(this);

            fragment_store_info = new Fragment_Store_Info();
            fragment_store_info.setActivity_search(this);

        }
        else {

            if((Fragment_Deal_Info) getFragmentManager().findFragmentByTag("Deal_Info") != null) {
                fragment_deal_info = (Fragment_Deal_Info) getFragmentManager().findFragmentByTag("Deal_Info");
                fragment_store_info = (Fragment_Store_Info) getFragmentManager().findFragmentByTag("Store_Info");
            }
            else{
                fragment_deal_info = new Fragment_Deal_Info();
                fragment_deal_info.setActivity_search(this);
                fragment_store_info = new Fragment_Store_Info();
                fragment_store_info.setActivity_search(this);

            }
        }


        // Enable Local Datastore.
        // Sometimes get an error here! Not sure why... It happens after going to the Galaxy Main Screen then re opening the app
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "ibsGWbLLA1BsbYWCd163g2vVJMd9LrhE6vmIovj2", "Vy8jgDckbEu3D5258KJSmSftShHqGHukeMtWliHs");


        setContentView(R.layout.activity_search);
        search_menu = (ImageView) findViewById(R.id.menu_imageView);
        searchCancel = (ImageView) findViewById((R.id.imgbtn_search_cancel));
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDockLayout = (RelativeLayout) findViewById(R.id.dockedLayout);

        lv_Results = (ListView) findViewById(R.id.lv_results);

        gv_alcoholType = (GridView) findViewById(R.id.gridview_alcohol_selection);

//        rg_display = (RadioGroup) findViewById(R.id.rg_display);
        rg_sort = (RadioGroup) findViewById(R.id.rg_sort);

        switch_list_map = (Switch) findViewById(R.id.switch_toggle);


        autocompletetv_Search = (AutoCompleteTextView) findViewById(R.id.autocompletetv_search);

        activity_description = (TextView) findViewById(R.id.zone_name);
        btn_network_status = (Button) findViewById(R.id.btn_network_alert);
        btn_location_status = (Button) findViewById(R.id.btn_location_alert);

//        menu = (ImageButton) findViewById(R.id.imgbtn_menu);


        search = this;

        map = new Map();
        map.setSearch(this);

        fragment_manager = getFragmentManager();

        map = new Map();

        adapter = new CustomListViewAdapter(search, al_testDeals);

        lv_Results.setAdapter(adapter);

        internet = new InternetHandler(this, this);

        is_Connected_To_Internet = internet.networkCheck();

        if(is_Connected_To_Internet){
//            getStores();
        }
        else{
            Toast.makeText(Search.this, "No network connection! Enable network connection", Toast.LENGTH_LONG).show();
        }


        // Get the deals from Parse database and populate the deal listview

//        getDeals();



        LocationHandler.LocationResult locationResult = new LocationHandler.LocationResult(){
            @Override
            public void gotLocation(Location location) {
                //Got the location!
                // Location was not found... Should display something to the user or try again
                Log.d(LOG_TAG, "Search is Finding the Location!");

                if (location == null) {
                    Log.d(LOG_TAG, "Location not found");
                    userLocation = location;

                } else {
                    Log.d(LOG_TAG, "Location found!");
                    userLocation = location;
                    System.out.println("Is userLocation null: " + userLocation == null);
                    Search.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Log.d("UI thread", "I am the UI thread");
                            update_Location_Alert(userLocation);
                        }
                    });

                    userLocation = location;
                }
            }
        };
//
//
        update_Location_Alert(userLocation);
        myLocation = new LocationHandler(this, this);
        myLocation.getLocation(this, locationResult);



        // Create the grid view icons
        // Alcohol Type Icons
        Bitmap beerIcon = BitmapFactory.decodeResource(this.getResources(), R.drawable.beer_icon);
        Bitmap wineIcon = BitmapFactory.decodeResource(this.getResources(), R.drawable.wine_icon);
        Bitmap vodkaIcon = BitmapFactory.decodeResource(this.getResources(), R.drawable.vodka_icon);
        Bitmap whiskyIcon = BitmapFactory.decodeResource(this.getResources(), R.drawable.whisky_icon);
        Bitmap tequilaIcon = BitmapFactory.decodeResource(this.getResources(), R.drawable.tequila_icon);
        Bitmap ginIcon = BitmapFactory.decodeResource(this.getResources(), R.drawable.other_icon);
        final Bitmap rumIcon = BitmapFactory.decodeResource(this.getResources(), R.drawable.other_icon);
        Bitmap kegIcon = BitmapFactory.decodeResource(this.getResources(), R.drawable.other_icon);
        Bitmap storeIcon = BitmapFactory.decodeResource(this.getResources(), R.drawable.other_icon);


        // Sort by Icons
        Bitmap a_to_zIcon = BitmapFactory.decodeResource(this.getResources(), R.drawable.a_to_z_icon);
        Bitmap sizeIcon = BitmapFactory.decodeResource(this.getResources(), R.drawable.size_icon);
        Bitmap priceIcon = BitmapFactory.decodeResource(this.getResources(), R.drawable.price_icon);
        Bitmap distanceIcon = BitmapFactory.decodeResource(this.getResources(), R.drawable.distance_icon);


        // Add the icons and text to the different grid views
        // Alcohol Type
        gridItems_alcoholType.add(new Item(beerIcon, "Beer"));
        gridItems_alcoholType.add(new Item(wineIcon, "Wine"));
        gridItems_alcoholType.add(new Item(vodkaIcon, "Vodka"));
        gridItems_alcoholType.add(new Item(whiskyIcon, "Whisky"));
        gridItems_alcoholType.add(new Item(tequilaIcon, "Tequila"));
        gridItems_alcoholType.add(new Item(ginIcon, "Gin"));
        gridItems_alcoholType.add(new Item(rumIcon, "Rum"));
        gridItems_alcoholType.add(new Item(kegIcon, "Keg"));
        gridItems_alcoholType.add(new Item(storeIcon, "Store"));

        search_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(mDockLayout);
            }
        });

        // searchCancel is used to remove all the text the user has used to search.... Prevents the user from having to backspace
        //Throughout the entire text in order to clear the search
        searchCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                autocompletetv_Search.setText("");
            }
        });


        btn_network_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // notify user
                AlertDialog.Builder dialog = new AlertDialog.Builder(Search.this);
                dialog.setMessage(Search.this.getResources().getString(R.string.network_not_enabled));
                dialog.setPositiveButton(Search.this.getResources().getString(R.string.open_network_settings), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                        // Wong INTENT!

                        Intent myIntent = new Intent(WifiManager.ACTION_PICK_WIFI_NETWORK);
                        Search.this.startActivity(myIntent);
                    }
                });
                dialog.setNegativeButton(Search.this.getString(R.string.Cancel), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Toast.makeText(Search.this, "No network connection! Enable network connection", Toast.LENGTH_LONG).show();
                    }
                });
                dialog.show();

            }
        });


        btn_location_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        customGridViewAdapter_Alcohol = new CustomGridViewAdapter(this, R.layout.row_grid, gridItems_alcoholType, this);

        gv_alcoholType.setAdapter(customGridViewAdapter_Alcohol);



        for(int i = 0; i < gridItems_alcoholType.size(); i++){
            gridItems_alcoholType.get(i).toggleCheck();
        }

        switch_list_map.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    if(fragment_deal_info.isAdded()) {
                        FragmentTransaction transaction = fragment_manager.beginTransaction();
                        transaction.remove(fragment_deal_info);
                        transaction.commit();
                    }
                    else if(fragment_store_info.isAdded()) {
                        FragmentTransaction transaction = fragment_manager.beginTransaction();
                        transaction.remove(fragment_store_info);
                        transaction.commit();

                    }

                    else if(map.isAdded()) {
                        FragmentTransaction transaction = fragment_manager.beginTransaction();
                        transaction.remove(map);
                        transaction.commit();

                    }
                    activity_description.setText("Results");
                }

                else{
                    if (fragment_deal_info.isAdded()) {
                        System.out.println("Deal info is currently visible!");


                        FragmentTransaction transaction = fragment_manager.beginTransaction();

                        transaction.replace(R.id.fragment_holder, map);

                        transaction.addToBackStack(null);
                        map.setSearch(search);
                        transaction.commit();

                    } else if (fragment_store_info.isAdded()) {
                        System.out.println("Store info is currently visible!");

                        FragmentTransaction transaction = fragment_manager.beginTransaction();
                        transaction.replace(R.id.fragment_holder, map);

                        transaction.addToBackStack(null);
                        map.setSearch(search);
                        transaction.commit();

                    } else if (!map.isAdded()) {
                        System.out.println("Map has not been added yet!");
                        FragmentTransaction transaction = fragment_manager.beginTransaction();
                        transaction.replace(R.id.fragment_holder, map);
                        transaction.addToBackStack(null);
                        map.setSearch(search);
                        transaction.commit();
                    }
                    activity_description.setText("Results");
                }
            }
        });


        rg_sort.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_a_to_z) {
                    adapter.sortDeals(R.id.rb_a_to_z);
                } else if (checkedId == R.id.rb_price) {
                    adapter.sortDeals(R.id.rb_price);

                } else if (checkedId == R.id.rb_size) {
                    adapter.sortDeals(R.id.rb_size);
                } else if (checkedId == R.id.rb_distance) {
                    adapter.sortDeals(R.id.rb_distance);
                }
            }
        });



        // Adds a text watcher to the AutoComplete Search bar so that the results can be filtered out
        autocompletetv_Search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = autocompletetv_Search.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text);
                adapter.sortDeals(rg_sort.getCheckedRadioButtonId());

                // Update the map
                if (search.map.isResumed()) {
                    map.showDeals();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        autocompletetv_Search.setOnEditorActionListener(new AutoCompleteTextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    InputMethodManager inputManager = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);

                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);

                    mDrawerLayout.closeDrawer(mDockLayout);

                    // Clicking the autocomplete search accessory button will take you to the Map view with the deals if the Mapview radio button is currently checked
                    if(fragment_store_info.isAdded() || fragment_deal_info.isAdded() && switch_list_map.isChecked()){
                        FragmentTransaction transaction = fragment_manager.beginTransaction();
                        transaction.replace(R.id.fragment_holder, map);
                        map.setSearch(search);
                        transaction.commit();
                    }
                    // Otherwise it will take you to the Search activity with the List view displaying all the deals
                    else if(fragment_deal_info.isAdded() && !switch_list_map.isChecked()){
                        FragmentTransaction transaction = fragment_manager.beginTransaction();
                        transaction.remove(fragment_deal_info);
                        transaction.commit();
                    }
                    else if(fragment_store_info.isAdded() && !switch_list_map.isChecked()){
                        FragmentTransaction transaction = fragment_manager.beginTransaction();
                        transaction.remove(fragment_store_info);
                        transaction.commit();
                    }

                    handled = true;
                }
                return handled;
            }
        });

}

    @Override
    protected void onDestroy() {
        System.out.println("Destroying Search");
        super.onDestroy();

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_settings, popup.getMenu());
        popup.show();
    }



    @Override
    public void onBackPressed() {
        if(fragment_manager.getBackStackEntryCount() != 0) {
            fragment_manager.popBackStack();

        } else {
            activity_description.setText("Results");
            super.onBackPressed();
        }
    }



    public void getDeals() {

        System.out.println("getDeals called!");
        System.out.println("Size of stores is: " + al_Stores.size());


        for (int z = 0; z < al_Stores.size(); z++) {
            final int j = z;

                ParseQuery<ParseObject> query = ParseQuery.getQuery("Alcohol").whereEqualTo("Store", storeHolder.get(z));
            query.findInBackground(new FindCallback<ParseObject>() {
                public void done(final List<ParseObject> alcohol, ParseException e) {
                    if (e == null) {

                        ArrayList<ParseObject> tempIDHolder = new ArrayList<ParseObject>();

                        // your logic here
                        for (int i = 0; i < alcohol.size(); i++) {
                            final String tempName = alcohol.get(i).get("Name").toString();
                            String tempPrice = alcohol.get(i).get("Price").toString();
                            String tempQuantity = alcohol.get(i).get("Quantity").toString();
                            String tempSize = alcohol.get(i).get("Container_Size").toString();
                            String tempABV = alcohol.get(i).get("ABV").toString();
                            String tempType = alcohol.get(i).get("Type_Alcohol").toString();

//                        ParseObject tempStore = alcohol.get(i).getParseObject("Store");

                            final String tempAlcoholID = alcohol.get(i).getObjectId().toString();


                            System.out.println(tempName);


                            if (tempType.equalsIgnoreCase("Beer")) {
                                Beer tempBeer = new Beer(tempABV + "% abv", "", "", tempName, tempType, search);
                                al_testDeals.add(new Deal((tempBeer), "1", tempPrice, tempQuantity, al_Stores.get(j), tempSize));
                            } else if (tempType.equalsIgnoreCase("Gin")) {
                                Gin tempGin = new Gin(tempABV + "% abv", "", "", tempName, tempType, search);
                                al_testDeals.add(new Deal((tempGin), "1", tempPrice, convert_Militiers_to_Liters(tempSize), al_Stores.get(j), tempSize));
                            } else if (tempType.equalsIgnoreCase("Rum")) {
                                Rum tempRum = new Rum(tempABV + "% abv", "", "", tempName, tempType, search);
                                al_testDeals.add(new Deal((tempRum), "1", tempPrice, convert_Militiers_to_Liters(tempSize), al_Stores.get(j), tempSize));
                            } else if (tempType.equalsIgnoreCase("Tequila")) {
                                Tequila tempTequila = new Tequila(tempABV + "% abv", "", "", tempName, tempType, search);
                                al_testDeals.add(new Deal((tempTequila), "1", tempPrice, convert_Militiers_to_Liters(tempSize), al_Stores.get(j), tempSize));

                            } else if (tempType.equalsIgnoreCase("Vodka")) {
                                Vodka tempVodka = new Vodka(tempABV + "% abv", "", "", tempName, tempType, search);
                                al_testDeals.add(new Deal((tempVodka), "1", tempPrice, convert_Militiers_to_Liters(tempSize), al_Stores.get(j), tempSize));

                            } else if (tempType.equalsIgnoreCase("Whisky") || tempType.equalsIgnoreCase("Whiskey")) {
                                Whisky tempWhisky = new Whisky(tempABV + "% abv", "", "", tempName, tempType, search);
                                al_testDeals.add(new Deal((tempWhisky), "1", tempPrice, convert_Militiers_to_Liters(tempSize), al_Stores.get(j), tempSize));

                            } else if (tempType.equalsIgnoreCase("Wine")) {
                                Wine tempWine = new Wine(tempABV + "% abv", "", "", tempName, tempType, search);
                                al_testDeals.add(new Deal((tempWine), "1", tempPrice, convert_Militiers_to_Liters(tempSize), al_Stores.get(j), tempSize));
                            }

                            adapter.addDeals(tempType);

                        }
                    } else {
                        // handle Parse Exception here
                        System.out.println("Parse Error!");
                    }

                    adapter.notifyDataSetChanged();
                }
            });
        }


    }
//
    // Get the stores from the Parse database
        public void getStores(){
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Store");
            query.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> store, ParseException e) {
                    if (e == null) {
                        // your logic here
                        for (int i = 0; i < store.size(); i++) {

                            storeHolder.add(store.get(i));

                            String tempName = store.get(i).get("Store_Name").toString();
                            String tempAddress = store.get(i).get("Address").toString();
                            String tempPhone = store.get(i).get("Phone").toString();

                            // GeoPoint
                            ParseGeoPoint tempGeoPoint = store.get(i).getParseGeoPoint("LatLng");
                            LatLng tempLatLng = new LatLng(tempGeoPoint.getLatitude(), tempGeoPoint.getLongitude());

                            String tempMonday = store.get(i).get("Monday").toString();
                            String tempTuesday = store.get(i).get("Tuesday").toString();
                            String tempWednesday = store.get(i).get("Wednesday").toString();
                            String tempThursday = store.get(i).get("Thursday").toString();
                            String tempFriday = store.get(i).get("Friday").toString();
                            String tempSaturday = store.get(i).get("Saturday").toString();
                            String tempSunday = store.get(i).get("Sunday").toString();

                            Boolean tempHas_Beer = (Boolean) store.get(i).get("Has_Beer");
                            Boolean tempHas_Liquor = (Boolean) store.get(i).get("Has_Liquor");
                            Boolean tempHas_Wine = (Boolean) store.get(i).get("Has_Wine");
                            String tempID = store.get(i).getObjectId();

                            System.out.println("Store id: " + tempID + "Name: " + tempName + " Address: " + tempAddress + " Phone: " + tempPhone);

                            Store tempStore = new Store(tempID, tempAddress, tempHas_Beer, tempHas_Liquor, tempHas_Wine, tempPhone, tempName, tempLatLng, tempMonday, tempTuesday, tempWednesday, tempThursday, tempFriday, tempSaturday, tempSunday);
                            al_Stores.add(tempStore);
                        }

                    } else {
                        // handle Parse Exception here
                        System.out.println("Parse Error!");
                    }

                    getDeals();


                }
            });
        }

    public void updateStoreDistances(){
        DecimalFormat df = new DecimalFormat("#.0");

        for(Deal deal: al_testDeals){

            if(deal.getStore().getStore_loc() != null){
                double distance_meters = userLocation.distanceTo(deal.getStore().getStore_loc());
                double distance_miles = distance_meters * 0.00062137;

                deal.setDistance_from_user(df.format(distance_miles) + " mi");

            }

        }
        Log.d(LOG_TAG, "Updated Store Distances!");
        adapter.notifyDataSetChanged();
    }


    public String convert_Militiers_to_Liters(String size){
        if(size.equalsIgnoreCase("1000")){
            return "1 L";
        }
        else if(size.equalsIgnoreCase("1750")){
            return "1.75 L";
        }

        return size + " ml";
    }


    public void update_Location_Alert(Location loc){
        Log.d(LOG_TAG, "Updating Location Alert");

        if(loc == null){
            Log.d(LOG_TAG, "Update Location Alert: Location not found");
//            btn_location_status.setVisibility(View.VISIBLE);

            Toast.makeText(Search.this, "Location not found! Ensure Location Services & Network is enabled", Toast.LENGTH_LONG).show();

        }
        else{
            Log.d(LOG_TAG, "Update Location Alert: Location found!");
//            btn_location_status.setVisibility(View.GONE);
            updateStoreDistances();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Other onResume() code here

        if (!mIsReceiverRegistered) {
            if (mReceiver == null)
                mReceiver = new LocationBroadcastReceiver();
            registerReceiver(mReceiver, new IntentFilter("android.location.PROVIDERS_CHANGED"));
            mIsReceiverRegistered = true;
        }

        if (!mIsReceiverRegistered_Internet) {
            if (mReceiver_Internet == null)
                mReceiver_Internet = new InternetBroadcastReceiver();
            registerReceiver(mReceiver_Internet, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
            mIsReceiverRegistered_Internet = true;
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mIsReceiverRegistered) {
            unregisterReceiver(mReceiver);
            mReceiver = null;
            mIsReceiverRegistered = false;
        }

        if (mIsReceiverRegistered_Internet) {
            unregisterReceiver(mReceiver_Internet);
            mReceiver_Internet = null;
            mIsReceiverRegistered_Internet = false;
        }

        // Other onPause() code here

    }

    // used to listen for intents which are sent after a task was
    // successfully processed

    // Need to make it so it updates the distances if the location is already enabled/found for the first time
    // I keep having to toggle the location at least once in order for it to update
    private class LocationBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            LocationHandler.LocationResult locationResult = new LocationHandler.LocationResult(){
                @Override
                public void gotLocation(Location location){
                    //Got the location!
                    // Location was not found... Should display something to the user or try again
                    if(location == null){
                        Log.d(LOG_TAG, "Broadcast Receiver: Location not found");


                    }
                    else{
                        Log.d(LOG_TAG, "Broadcast Receiver: Location found!");
//                        btn_location_status.setVisibility(View.GONE);
//
                        userLocation = location;
//                        updateStoreDistances();
                        System.out.println("Is userLocation null: " + userLocation == null);
                        Search.this.runOnUiThread(new Runnable() {
                            public void run() {
                                Log.d("UI thread", "I am the UI thread");
                                update_Location_Alert(userLocation);
                            }
                        });



                    }

                }

            };

            myLocation.getLocation(Search.this, locationResult);
//            update_Location_Alert(userLocation);

        }

    }

    // Need to update data if it is stale!
    private class InternetBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            is_Connected_To_Internet = internet.networkCheck();

            if(is_Connected_To_Internet){
//                btn_network_status.setVisibility(View.GONE);
                Log.d(LOG_TAG, "Internet Broadcast: Connected!");


                NetworkInfo networkInfo =
                        intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                if(networkInfo.isConnected()) {
                    // Wifi is connected
                    Log.d("Inetify", "Wifi is connected: " + String.valueOf(networkInfo));

                    if(adapter.getCount() < 1) {
                        getStores();
                    }

                }
            }
            else{
                Toast.makeText(Search.this, "No network connection! Enable network connection", Toast.LENGTH_LONG).show();

//                btn_network_status.setVisibility(View.VISIBLE);
                Log.d(LOG_TAG, "Internet Broadcast: Not Connected!");
            }

        }

    }

}
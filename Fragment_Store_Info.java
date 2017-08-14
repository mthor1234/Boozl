package com.crash.boozl.boozl.code;


import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;


// TO DO: Add a Open and Closed icon in the image view when the store is closed or open

public class Fragment_Store_Info extends Fragment implements View.OnClickListener{

    TextView tv_storeName, tv_storeRoad, tv_storeTown, tv_storePhone, tv_storeDistance;
    TextView tv_mon, tv_tue, tv_wed, tv_thu, tv_fri, tv_sat, tv_sun;
    int store_Rating;

    ImageView imgview_icon;

    private Deal deal;
    private ListView lv_deals;

    private Search activity_search;

    RelativeLayout rel;

    ImageButton imgbtn_directions;


    private SlidingUpPanelLayout slidingLayout;


    public Fragment_Store_Info() {
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_store_info, container, false);

        System.out.println("creating store fragment");

        tv_storeName = (TextView) view.findViewById(R.id.tv_storename_storepage);
        tv_storeRoad = (TextView) view.findViewById(R.id.tv_road_storepage);
        tv_storeTown = (TextView) view.findViewById(R.id.tv_city_storepage);
        tv_storePhone = (TextView) view.findViewById(R.id.tv_phone_storepage);
        tv_storeDistance = (TextView) view.findViewById(R.id.tv_distance_storepage);

        tv_mon = (TextView) view.findViewById(R.id.tv_mon_storepage);
        tv_tue = (TextView) view.findViewById(R.id.tv_tue_storepage);
        tv_wed = (TextView) view.findViewById(R.id.tv_wed_storepage);
        tv_thu = (TextView) view.findViewById(R.id.tv_thur_storepage);
        tv_fri = (TextView) view.findViewById(R.id.tv_fri_storepage);
        tv_sat = (TextView) view.findViewById(R.id.tv_sat_storepage);
        tv_sun = (TextView) view.findViewById(R.id.tv_sun_storepage);

        imgbtn_directions = (ImageButton) view.findViewById(R.id.imgbtn_directions_storepage);


        imgview_icon = (ImageView) view.findViewById(R.id.imgview_icon_storepage);
        slidingLayout = (SlidingUpPanelLayout) view.findViewById(R.id.sliding_layout);
        rel = (RelativeLayout) view.findViewById(R.id.relativelayout_store);
        lv_deals = (ListView) view.findViewById(R.id.lv_storedeals_storepage);


            rel.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });

        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(deal != null) {
            setArguments(deal);
        }
    }

    // Release all the fragment's views for garbage collection once onDestroyView is called during the lifecycle
    @Override
    public void onDestroyView() {
        super.onDestroyView();

        tv_storeName = null;
        tv_storeRoad = null;
        tv_storeTown = null;
        tv_storePhone = null;
        tv_storeDistance = null;
        imgview_icon = null;
        slidingLayout = null;
        rel = null;
        lv_deals = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    public void setArguments(Deal deal){

        System.out.println("Store Frag....Store Name: " + deal.getStore().getStore_name());
        System.out.println("Store Frag....Address: " + deal.getStore().getAddress());
        System.out.println("Store Frag....Phone: " + deal.getStore().getPhone_number());


        this.tv_storeName.setText(deal.getStore().getStore_name());
        this.tv_storePhone.setText(deal.getStore().getPhone_number());

        this.tv_storeRoad.setText(deal.getStore().getStreet_address());
        this.tv_storeTown.setText(deal.getStore().getCity_zip());
        this.tv_storeDistance.setText(deal.getDistance_from_user());

        this.imgview_icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_store_mall_directory_white_48dp));

        this.tv_mon.setText("Mon: " + deal.getStore().getMonday_hours());
        this.tv_tue.setText("Tue: " +deal.getStore().getTuesday_hours());
        this.tv_wed.setText("Wed: " +deal.getStore().getWednesday_hours());
        this.tv_thu.setText("Thu: " +deal.getStore().getThursday_hours());
        this.tv_fri.setText("Fri: " +deal.getStore().getFriday_hours());
        this.tv_sat.setText("Sat: " +deal.getStore().getSaturday_hours());
        this.tv_sun.setText("Sun: " +deal.getStore().getSunday_hours());

        activity_search.activity_description.setText("Store Information");

        lv_deals.setAdapter(new CustomListViewAdapter(activity_search, deal.getStore().getCurrent_Deals()));

        imgbtn_directions.setOnClickListener(this);


    }


    public Deal getDeal() {
        return deal;
    }

    public void setDeal(Deal deal) {
        this.deal = deal;
    }

    public Search getActivity_search() {
        return activity_search;
    }

    public void setActivity_search(Search activity_search) {
        this.activity_search = activity_search;
    }


    @Override
    public void onClick(View v) {
        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + deal.getStore().getStreet_address()+"," + deal.getStore().getCity_zip());
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }
}

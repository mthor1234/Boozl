package com.crash.boozl.boozl.code.Alcohols;


import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.MotionEventCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.crash.boozl.boozl.code.CustomListViewAdapter;
import com.crash.boozl.boozl.code.Deal;
import com.crash.boozl.boozl.code.R;
import com.crash.boozl.boozl.code.Search;

import java.util.ArrayList;


// TO DO: Add a Open and Closed icon in the image view when the store is closed or open

public class Fragment_Store_Info extends Fragment {

    TextView tv_storeName, tv_storeRoad, tv_storeTown, tv_storePhone, tv_storeDistance;
    RatingBar rb_storeRating;

    int store_Rating;

    ImageView imgview_icon;

    private Deal deal;
    private ListView lv_deals;

    private Search activity_search;


    public Fragment_Store_Info() {
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_store_info, container, false);


        tv_storeName = (TextView) view.findViewById(R.id.tv_storename_storepage);
        tv_storeRoad = (TextView) view.findViewById(R.id.tv_road_storepage);
        tv_storeTown = (TextView) view.findViewById(R.id.tv_city_storepage);
        tv_storePhone = (TextView) view.findViewById(R.id.tv_phone_storepage);
        tv_storeDistance = (TextView) view.findViewById(R.id.tv_distance_storepage);

        imgview_icon = (ImageView) view.findViewById(R.id.imgview_icon_storepage);

        lv_deals = (ListView) view.findViewById(R.id.lv_storedeals_storepage);


        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setArguments(deal);
    }

    public void setArguments(Deal deal){


        this.tv_storeName.setText(deal.getStore().getStore_name());
        this.tv_storePhone.setText(deal.getStore().getPhone_number());

        this.tv_storeRoad.setText(deal.getStore().getStreet_address());
        this.tv_storeTown.setText(deal.getStore().getCity_zip());

        lv_deals.setAdapter(new CustomListViewAdapter(activity_search, deal.getStore().getCurrent_Deals()));

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
}

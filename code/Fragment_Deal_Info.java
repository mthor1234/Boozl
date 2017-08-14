package com.crash.boozl.boozl.code;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;


public class Fragment_Deal_Info extends Fragment implements View.OnClickListener {

    TextView tv_alcoholName, tv_alcoholType, tv_alcoholSize, tv_alcoholPrice, tv_alcoholPercentage;
    TextView tv_storeRoad, tv_storeTown, tv_storePhone, tv_storeDistance;

    ImageView imgview_icon;

    Button btn_storeName;

    ImageButton imgbtn_directions;

//    ImageButton imgbtn_directions;

    private Deal deal;
    private ExpandableListView expandableListView_hours;

    private Search activity_search;


    public Fragment_Deal_Info() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_deal_info, container, false);


        tv_alcoholName = (TextView) view.findViewById(R.id.dealpage_tv_alcoholname);
        tv_alcoholType = (TextView) view.findViewById(R.id.dealpage_tv_alcohol_type);
        tv_alcoholSize = (TextView) view.findViewById(R.id.dealpage_tv_result_size);
        tv_alcoholPrice = (TextView) view.findViewById(R.id.dealpage_tv_price);
        tv_alcoholPercentage = (TextView) view.findViewById(R.id.dealpage_tv_percent);

        tv_storeRoad = (TextView) view.findViewById(R.id.dealpage_tv_road);
        tv_storeTown = (TextView) view.findViewById(R.id.dealpage_tv_townzip);
        tv_storePhone = (TextView) view.findViewById(R.id.dealpage_tv_phone);
        tv_storeDistance = (TextView) view.findViewById(R.id.dealpage_tv_distance);

        btn_storeName = (Button) view.findViewById(R.id.dealpage_btn_storename);
//        imgbtn_directions = (ImageButton) view.findViewById(R.id.imgbtn_directions_storepage);


        imgview_icon = (ImageView) view.findViewById(R.id.dealpage_imgview_icon);

//        if (savedInstanceState != null) {
//
//            System.out.println("There is a saved instance!");
//
//            // Restore last state
//            tv_alcoholName.setText(savedInstanceState.getString("tv_alcoholName"));
//            tv_alcoholType.setText(savedInstanceState.getString("tv_alcoholType"));
//            tv_alcoholSize.setText(savedInstanceState.getString("tv_alcoholSize"));
//            tv_alcoholPrice.setText(savedInstanceState.getString("tv_alcoholPrice"));
//            tv_alcoholPercentage.setText(savedInstanceState.getString("tv_alcoholPercentage"));
//            tv_storeRoad.setText(savedInstanceState.getString("tv_storeRoad"));
//            tv_storeTown.setText(savedInstanceState.getString("tv_storeTown"));
//            tv_storePhone.setText(savedInstanceState.getString("tv_storePhone"));
//            tv_storeDistance.setText(savedInstanceState.getString("tv_storeDistance"));
//
//            btn_storeName.setText(savedInstanceState.getString("btn_storeName"));
//        }

        imgbtn_directions = (ImageButton) view.findViewById(R.id.dealpage_imgbtn_directions);

        ArrayList<String> list = new ArrayList<String>();


        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (deal != null) {
            System.out.println(deal.getAlcohol_name());
            setArguments(deal);
        }

    }

    /**
     * This method will only be called once when the retained
     * Fragment is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retain this fragment across configuration changes.
        setRetainInstance(true);
    }

    /**
     * Set the callback to null so we don't accidentally leak the
     * Activity instance.
     */
    @Override
    public void onDetach() {
        super.onDetach();
    }



// Release all the fragment's views for garbage collection once onDestroyView is called during the lifecycle
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        tv_alcoholName = null;
        tv_alcoholType = null;
        tv_alcoholSize = null;
        tv_alcoholPrice = null;
        tv_alcoholPercentage = null;
        tv_storeRoad = null;
        tv_storeTown = null;
        tv_storePhone = null;
        tv_storeDistance = null;
        btn_storeName = null;
        imgview_icon = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

//    System.out.println("Outstate null: " + outState == null);
//            outState.putString("tv_alcoholType", tv_alcoholType.getText().toString());
//            outState.putString("tv_alcoholSize", tv_alcoholSize.getText().toString());
//            outState.putString("tv_alcoholPrice", tv_alcoholPrice.getText().toString());
//            outState.putString("tv_alcoholPercentage", tv_alcoholPercentage.getText().toString());
//            outState.putString("tv_storeRoad", tv_storeRoad.getText().toString());
//            outState.putString("tv_storeTown", tv_storeTown.getText().toString());
//            outState.putString("tv_storePhone", tv_storePhone.getText().toString());
//            outState.putString("tv_storeDistance", tv_storeDistance.getText().toString());
//            outState.putString("btn_storeName", btn_storeName.getText().toString());

    }


    public void setArguments(Deal deal){
        System.out.println("btn_storeName: " + this.btn_storeName);
        System.out.println("Deal: " + deal);
        System.out.println("Store: " + deal.getStore());
        System.out.println("Store Name: " + deal.getStore().getStore_name());


        this.btn_storeName.setText(deal.getStore().getStore_name());
        this.tv_alcoholName.setText(deal.getAlcohol_name());


        // Need to set the fancy type of alcohol type such as "Tennessee Whiskey" instead of just "Whiskey"
        this.tv_alcoholType.setText(deal.getType());
        this.tv_alcoholSize.setText(deal.getSize());

        Double value = Double.valueOf(deal.getPrice());
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String gainString = formatter.format(value);

        this.imgview_icon.setImageDrawable(deal.getAlcohol_image());
        this.tv_alcoholPrice.setText(gainString);
        this.tv_alcoholPercentage.setText(deal.getAlcohol_percentage());
        this.tv_storePhone.setText(deal.getStore().getPhone_number());
        this.tv_storeRoad.setText(deal.getStore().getStreet_address());
        this.tv_storeTown.setText(deal.getStore().getCity_zip());
        this.tv_storeDistance.setText(deal.getDistance_from_user());


        activity_search.activity_description.setText("Deal Information");
        btn_storeName.setOnClickListener(this);

        imgbtn_directions.setOnClickListener(this);
    }


    public Deal getDeal() {

        return deal;
    }

    public void setDeal(Deal deal) {
        this.deal = deal;
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()) {
            case R.id.dealpage_btn_storename:

                FragmentManager fm = activity_search.getFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();


                Fragment_Store_Info fragment = new Fragment_Store_Info();

                transaction.replace(R.id.fragment_holder, fragment, "Store_Info");


                transaction.addToBackStack("Store_Info");


                fragment.setActivity_search(activity_search);
                fragment.setDeal(deal);

                transaction.commitAllowingStateLoss();

                if (activity_search != null) {
                    activity_search.fragment_store_info = fragment;
                }
                break;

            case R.id.dealpage_imgbtn_directions:
                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + deal.getStore().getStreet_address()+"," + deal.getStore().getCity_zip());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
                break;

            default:
                break;
        }


    }





    public Search getActivity_search() {
        return activity_search;
    }

    public void setActivity_search(Search activity_search) {
        this.activity_search = activity_search;
    }
}

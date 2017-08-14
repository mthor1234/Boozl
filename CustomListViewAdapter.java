package com.crash.boozl.boozl.code;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class CustomListViewAdapter extends BaseAdapter {
    private List<Deal> dealList = null;
    ArrayList<Deal> result;

    Search search;
    Context context;

    int[] imageId;
    private static LayoutInflater inflater = null;

    public CustomListViewAdapter(Search search, ArrayList<Deal> prgmNameList) {

        result = new ArrayList<Deal>();

        System.out.println("Is deal null: " + prgmNameList == null);

        result.addAll(prgmNameList);
        dealList = prgmNameList;

        context = search;
        this.search = search;
//        imageId= prgmImages;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return result.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public class Holder {
        TextView tv_Result_Title, tv_Result_Size, tv_Result_Store, tv_Result_Price, tv_Result_Distance;
        ImageView imgview_Result_Icon;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        View row = convertView;

//        final Fragment_Deal_Info fragment = new Fragment_Deal_Info();

        if (row == null) {
            holder = new Holder();

            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(R.layout.custom_result_row, parent, false);

            holder.tv_Result_Title = (TextView) row.findViewById(R.id.tv_result_title);
            holder.tv_Result_Size = (TextView) row.findViewById(R.id.tv_result_size);
            holder.tv_Result_Store = (TextView) row.findViewById(R.id.tv_result_store);
            holder.tv_Result_Price = (TextView) row.findViewById(R.id.tv_result_price);
            holder.tv_Result_Distance = (TextView) row.findViewById(R.id.tv_result_distance);
            holder.imgview_Result_Icon = (ImageView) row.findViewById(R.id.imgview_result_icon);

//        holder.img.setImageResource(imageId[position]);

            row.setTag(holder);
        } else {
            holder = (Holder) row.getTag();
        }

        Double value = Double.valueOf(result.get(position).getPrice());
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String gainString = formatter.format(value);

        holder.tv_Result_Title.setText(result.get(position).getAlcohol_name());
        holder.tv_Result_Size.setText(result.get(position).getSize());
        holder.tv_Result_Price.setText(gainString);
        holder.tv_Result_Distance.setText(result.get(position).getDistance_from_user());
        holder.tv_Result_Store.setText(result.get(position).getStore().getStore_name());
        holder.imgview_Result_Icon.setImageDrawable(result.get(position).getAlcohol_image());


        row.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v) {
           FragmentManager fm = search.getFragmentManager();
           FragmentTransaction transaction = fm.beginTransaction();

           // Animation not working... Work on animations later
//                transaction.setCustomAnimations(R.animator.enter_from_left, R.animator.exit_to_right);

           search.fragment_deal_info.setDeal(result.get(position));

           System.out.println("Not Visible");
           transaction.replace(R.id.fragment_holder, search.fragment_deal_info, "Deal_Info");

            transaction.addToBackStack("Deal_Info");
           transaction.commit();

       }
   }

        );

            row.setOnTouchListener(new View.OnTouchListener()

            {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {
                        v.setBackgroundColor(context.getResources().getColor(R.color.gray));
                    } else {
                        v.setBackgroundColor(context.getResources().getColor(R.color.white));
                    }
                    return false;
                }
            });


        return row;
    }

    // Filter the listview based on the text entered by the user
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        result.clear();
        if (charText.length() == 0) {
            result.addAll(dealList);
        } else {
            for (Deal deal : dealList) {
                if (deal.getAlcohol_name().toLowerCase(Locale.getDefault()).contains(charText)) {
                    result.add(deal);
                }
            }
        }
        notifyDataSetChanged();
    }

    // Sort the listview based on the alcohol selection types the user selects

    // Make it so you can select multiple items, and it clears when nothing is selected
    public void addDeals(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());

        for (Deal deal : dealList) {
            if (deal.getType().toLowerCase(Locale.getDefault()).contains(charText) && !result.contains(deal)) {
                result.add(deal);
            }
        }
        notifyDataSetChanged();
    }

    public void removeDeals(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());

        for (Deal deal : dealList) {
            if (deal.getType().toLowerCase(Locale.getDefault()).contains(charText) && result.contains(deal)) {
                result.remove(deal);
            }
        }
        notifyDataSetChanged();
    }

    public void sortDeals(int v) {

        switch (v) {

            // If radio button a to z is pressed.. Sort alphabetically
            case R.id.rb_a_to_z:


                Collections.sort(result, new Comparator<Deal>() {
                    @Override
                    public int compare(Deal lhs, Deal rhs) {
                        // String implements Comparable
                        return (lhs.getAlcohol_name()).compareTo(rhs.getAlcohol_name());
                    }
                });

                notifyDataSetChanged();
                break;

            // If radio Button price is pressed... Sort by prices
            case R.id.rb_price:

                Collections.sort(result, new Comparator<Deal>() {
                    @Override
                    public int compare(Deal lhs, Deal rhs) {
                        // String implements Comparable
                        return (lhs.getPrice()).compareTo(rhs.getPrice());
                    }
                });

                notifyDataSetChanged();

                notifyDataSetChanged();
                break;

            // If radio button size is pressed
            case R.id.rb_size:

                ArrayList<Deal> beerList = new ArrayList<Deal>();
                ArrayList<Deal> nonbeerList = new ArrayList<Deal>();

                // Insertion sort by all the deal's sizes
                for (int i = 0; i < result.size(); i++) {
                        if(result.get(i).getAlcohol().type.equalsIgnoreCase("Beer")){
                            beerList.add(result.get(i));
                        }
                    else{
                            nonbeerList.add(result.get(i));
                        }
                }

                Collections.sort(beerList, new Comparator<Deal>() {
                            @Override
                            public int compare(Deal lhs, Deal rhs) {
                                // String implements Comparable
                                return (rhs.getSize()).compareTo(lhs.getSize());
                            }
                        });

                    Collections.sort(nonbeerList, new Comparator<Deal>() {
                        @Override
                        public int compare(Deal lhs, Deal rhs) {
                            // String implements Comparable
                            return (rhs.getMililiters()).compareTo(lhs.getMililiters());
                        }
                    });

                result.clear();
                result.addAll(beerList);
                result.addAll(nonbeerList);

                notifyDataSetChanged();

                break;

            // If radio button distance is pressed...Sort by distance

            case R.id.rb_distance:

                Collections.sort(result, new Comparator<Deal>() {
                    @Override
                    public int compare(Deal lhs, Deal rhs) {
                        // String implements Comparable
                        return (lhs.getDistance_from_user()).compareTo(rhs.getDistance_from_user());
                    }
                });


                notifyDataSetChanged();

                break;

            default:

                break;
        }

    }

}


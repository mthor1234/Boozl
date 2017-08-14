package com.crash.boozl.boozl.code;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.DrawableRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class CustomGridViewAdapter extends ArrayAdapter<Item> {
    Context context;
    Search search;
    int layoutResourceId;
    ArrayList<Item> data = new ArrayList<Item>();

    public CustomGridViewAdapter(Context context, int layoutResourceId,
                                 ArrayList<Item> data, Search search) {
        super(context, layoutResourceId, data);

        this.search = search;
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        RecordHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(R.layout.row_grid, parent, false);

            holder = new RecordHolder();
            holder.relativeLayout = (RelativeLayout) row.findViewById(R.id.relativelayout_griditem);
            holder.txtTitle = (TextView) row.findViewById(R.id.item_text);
            holder.imageItem = (ImageView) row.findViewById(R.id.item_image);
            holder.cb_checked = (CheckBox) row.findViewById(R.id.cb_search);

            holder.cb_checked.setChecked(true);
            holder.cb_checked.setVisibility(View.VISIBLE);

          row.setBackgroundColor(context.getResources().getColor(R.color.gray));
            row.setBackgroundResource(R.drawable.gridview_view_round);

            row.setTag(holder);
        } else {
            holder = (RecordHolder) row.getTag();
        }

        final RecordHolder finalHolder = holder;
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalHolder.cb_checked.toggle();

                data.get(position).toggleCheck();

                if(data.get(position).getChecked()) {
//                    v.setBackgroundColor(context.getResources().getColor(R.color.gray));
                    v.setBackgroundResource(R.drawable.gridview_view_round);
                    search.adapter.addDeals(search.gridItems_alcoholType.get(position).getTitle());
                    finalHolder.cb_checked.setVisibility(View.VISIBLE);
                }
                    else{
                    search.adapter.removeDeals(search.gridItems_alcoholType.get(position).getTitle());
                    v.setBackgroundColor(context.getResources().getColor(R.color.white));
                    finalHolder.cb_checked.setVisibility(View.INVISIBLE);
                    }

                // Update map with the filtered deals
                if(search.map.isResumed()) {
                    search.map.showDeals();
                }
            }
        });

        Item item = data.get(position);
        holder.txtTitle.setText(item.getTitle());
        holder.imageItem.setImageBitmap(item.getImage());



        return row;
    }

    public class RecordHolder {
        TextView txtTitle;
        ImageView imageItem;
        CheckBox cb_checked;
        RelativeLayout relativeLayout;
    }
}
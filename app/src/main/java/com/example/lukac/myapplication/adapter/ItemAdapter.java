package com.example.lukac.myapplication.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import com.example.lukac.myapplication.R;
import com.example.lukac.myapplication.entity.Item;
import com.example.lukac.myapplication.service.Tools;

import java.util.List;

/**
 * Created by lukac on 02/11/2017.
 */

public class ItemAdapter extends ArrayAdapter<Item> {


    /**
     * Constructor
     *
     * @param context    The current context.
     * @param resource The resource ID for a layout file containing a TextView to use when
     *                 instantiating views.
     * @param objects  The objects to represent in the ListView.
     */
    public ItemAdapter(@NonNull Context context, int resource, @NonNull List<Item> objects) {
        super(context, resource, objects);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Item item  = getItem(position);
        //Log.e("item", ": " + item.getId());
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        // Lookup view for data population
        TextView tvTitle = (TextView) convertView.findViewById(R.id.item_title);
        TextView tvDate  = (TextView) convertView.findViewById(R.id.item_date);
        // Populate the data into the template view using the data object

        // riempio con i valori
        tvTitle.setText(item.getTitle());
        tvDate.setText(Tools.getFormattedDate(item.getDate()));
         
        // Return the completed view to render on screen
        return convertView;
    }


}

package com.example.capitalink.profile;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.capitalink.R;

import java.util.ArrayList;
import java.util.List;

public class profileAdapter extends ArrayAdapter<profile> {

    public profileAdapter(@NonNull Activity context, int resource, @NonNull ArrayList<profile> profiles) {
        super(context, resource, profiles);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.profile_list_item, parent, false);
        }

        // Get the {@link AndroidFlavor} object located at this position in the list
        profile p = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID version_number
        TextView listitem = (TextView) listItemView.findViewById(R.id.miwok);
        // Get the version number from the current AndroidFlavor object and
        // set this text on the number TextView
        listitem.setText(p.getListItem());

        // Find the ImageView in the list_item.xml layout with the ID list_item_icon
        ImageView iconView = (ImageView) listItemView.findViewById(R.id.list_item_icon);
        // Check if an image is provided for this word or not
        if (p.hasImage()) {
            // If an image is available, display the provided image based on the resource ID
            iconView.setImageResource(p.getmImage());
            // Make sure the view is visible
            iconView.setVisibility(View.VISIBLE);
        } else {
            // Otherwise hide the ImageView (set visibility to GONE)
            iconView.setVisibility(View.GONE);
        }

    return listItemView;
    }

}

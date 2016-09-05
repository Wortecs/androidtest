package com.horsegaming.sensorchecker.core.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.horsegaming.sensorchecker.R;
import com.horsegaming.sensorchecker.core.database.Location;

import java.util.List;

/**
 * Created by Horse on 05.09.2016.
 */
public class LocationListAdapter extends BaseAdapter {

    private List<? extends Location> content;
    private Context context;

    public LocationListAdapter(List<? extends Location> content, Context context){
        this.content = content;
        this.context = context;
    }

    @Override
    public int getCount() {
        return content.size();
    }

    @Override
    public Object getItem(int position) {
        return content.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.location_list_view, parent, false);
        TextView textView = (TextView)rowView.findViewById(R.id.list_textView);
        textView.setText(this.content.get(position).Name);

        return rowView;
    }

}

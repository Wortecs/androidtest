package com.horsegaming.sensorchecker.core.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import com.horsegaming.sensorchecker.core.net.Component;

import java.util.List;

/**
 * Created by Horse on 05.09.2016.
 */
public class ComponentListAdapter extends BaseAdapter
{
    List<? extends Component.Drawer> content;
    Context context;
    int locationId;

    public ComponentListAdapter(List<? extends Component.Drawer> content, Context context, int locationId){
        this.content = content;
        this.context = context;
        this.locationId = locationId;
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

        return content.get(position).draw(context, parent, locationId);

    }
}

package com.horsegaming.sensorchecker.core.net;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.ViewGroup;

import com.horsegaming.sensorchecker.R;
import com.horsegaming.sensorchecker.core.database.DataBaseHelper;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Horse on 05.09.2016.
 */
public abstract class Component
{


    public final void save(JSONObject obj, int locationID, SQLiteDatabase db){
        try {

            boolean value = false;
            if(!obj.isNull(getKeyword()))
                value = obj.getBoolean(getKeyword());

            DataBaseHelper.Helper.addParameter(locationID, getKeyword(), value, db);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public abstract String getKeyword();

    public abstract Drawer load();


    public interface Drawer
    {
        View draw(Context context, ViewGroup parent,  int locationID);
    }

}

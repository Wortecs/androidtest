package com.horsegaming.sensorchecker.core.net;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.horsegaming.sensorchecker.core.database.DataBaseHelper;
import com.horsegaming.sensorchecker.core.net.components.BuoysComponent;
import com.horsegaming.sensorchecker.core.net.components.MoonComponent;
import com.horsegaming.sensorchecker.core.net.components.TidesComponent;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Horse on 05.09.2016.
 */
public class ComponentsHandler
{
    private static List<Component> components = new ArrayList<>();

    private ComponentsHandler(){}

    static {
        components.add(new BuoysComponent());
        components.add(new TidesComponent());
        components.add(new MoonComponent());
    }

    public static boolean add(Component component){
        if(components.contains(component))
            return false;

        components.add(component);
        return true;
    }


    public static void save(JSONObject object, int locationID, SQLiteDatabase db){
        for (Component component: components) {
           component.save(object, locationID, db);
        }
    }

    public static List<Component.Drawer> load(Context context, int locationId){
        HashMap<String, Boolean> checker = DataBaseHelper.Helper.getParameters(locationId);
        List<Component.Drawer> drawers = new ArrayList<>(components.size());

        for (Component component: components) {
            if(checker.get(component.getKeyword())){
                drawers.add(component.load());
            }

        }

        return drawers;
    }

}

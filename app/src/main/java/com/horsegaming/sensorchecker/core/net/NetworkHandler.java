package com.horsegaming.sensorchecker.core.net;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.horsegaming.sensorchecker.core.database.DataBaseHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Horse on 05.09.2016.
 */
public class NetworkHandler
{
    private NetworkHandler(){};

    public static boolean CheckConnection(final Context context){

        ConnectivityManager manager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();

        return info != null && info.isConnected();
    }

    public static void parseJSONLocation(String stringJSON){
        try {
            DataBaseHelper.Helper.clear();
            JSONObject mainObj = new JSONObject(stringJSON);

            Log.d("JSON parse ", "Start");

            JSONArray tmpArr = mainObj.getJSONArray("ReturnValue").getJSONObject(0).getJSONArray("Items");
            SQLiteDatabase db = DataBaseHelper.Helper.getWritableDatabase();
            db.beginTransaction();
            executeAndAddToDB(tmpArr, db);

            Log.d("JSON parse ", "End");
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static void executeAndAddToDB( JSONArray array, SQLiteDatabase db  ) throws JSONException{

        for (int i = 0; i <  array.length(); i++) {
            JSONObject tmpObj = array.getJSONObject(i);

            int itemType = tmpObj.getInt("ItemType");
            boolean last = false;

            int locationID = tmpObj.getInt("LocationId");
            int parentID = tmpObj.getInt("ParentId");

            if( itemType != 2){

                JSONArray tmpArr = tmpObj.getJSONArray("Items");

                executeAndAddToDB(tmpArr, db);

            }else {
                last = true;
                ComponentsHandler.save(tmpObj, locationID, db);
            }

            String name = tmpObj.getString("Name");
            DataBaseHelper.Helper.addLocation(locationID, parentID, name, last, db);
        }
    }


}

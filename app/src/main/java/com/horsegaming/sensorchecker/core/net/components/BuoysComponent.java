package com.horsegaming.sensorchecker.core.net.components;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.horsegaming.sensorchecker.R;
import com.horsegaming.sensorchecker.core.database.DataBaseHelper;
import com.horsegaming.sensorchecker.core.net.AsyncGetter;
import com.horsegaming.sensorchecker.core.net.Component;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

/**
 * Created by Horse on 05.09.2016.
 */
public class BuoysComponent extends Component
{
    final String keyword = "VisibleOnBuoys";

    @Override
    public String getKeyword() {
        return keyword;
    }

    @Override
    public Drawer load() {
        return new BuoysDrawer();
    }

    public class BuoysDrawer implements Drawer
    {
        @Override
        public View draw(Context context, ViewGroup parent, int locationID) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.buoys_view_layout, parent, false);

            try {
                String jsonStr = new AsyncGetter()
                        .execute("http://localbuoywebserver.staturedev.com/api/MobileApi/GetBouyInfo?locationId=" + locationID).get();
                JSONObject obj = new JSONObject(jsonStr).getJSONObject("ReturnValue");
                String height  = "";
                String period  = "";
                if(!obj.isNull("WaveHeight")){
                    height = obj.getString("WaveHeight");
                }
                if(!obj.isNull("WavePeriod")){
                    period = obj.getString("WavePeriod");
                }

                TextView textView = (TextView)rowView.findViewById(R.id.buoys_vawePeriod);
                textView.setText(height);
                textView = (TextView)rowView.findViewById(R.id.buoys_vawePeriod);
                textView.setText(period);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return rowView;
        }


    }
}

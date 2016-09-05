package com.horsegaming.sensorchecker.core.net.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.horsegaming.sensorchecker.R;
import com.horsegaming.sensorchecker.core.net.AsyncGetter;
import com.horsegaming.sensorchecker.core.net.Component;
import com.horsegaming.sensorchecker.core.view.Diagram;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.FloatBuffer;
import java.util.concurrent.ExecutionException;

/**
 * Created by Horse on 05.09.2016.
 */
public class TidesComponent extends Component{

    final String keyword = "VisibleOnTides";

    @Override
    public String getKeyword() {
        return keyword;
    }

    @Override
    public Drawer load() {
        return new TidesDrawer();
    }

    public class TidesDrawer implements Drawer{

        @Override
        public View draw(Context context, ViewGroup parent, int locationID) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.tides_view_layout, parent, false);

            try {
                String jsonStr = new AsyncGetter()
                        .execute("http://localbuoywebserver.staturedev.com/api/MobileApi/GetTidalGeneralInfo?locationId=" + locationID).get();
                JSONObject obj = new JSONObject(jsonStr).getJSONObject("ReturnValue");
                TextView textView;
                if(!obj.isNull("TideLatitude")){
                    textView = (TextView)rowView.findViewById(R.id.tide_latitude);
                    textView.setText( obj.getString("TideLatitude"));
                }
                if(!obj.isNull("TideLongitude")){
                    textView = (TextView)rowView.findViewById(R.id.tide_longitude);
                    textView.setText(obj.getString("TideLongitude"));
                }

                jsonStr = new AsyncGetter()
                        .execute("http://localbuoywebserver.staturedev.com/api/MobileApi/GetTidalTidesData?locationId=" + locationID).get();
                JSONArray arr = new JSONObject(jsonStr).getJSONObject("ReturnValue").getJSONArray("TideDatas");
                double[] tmpValues = new double[arr.length()];
                for (int i = 0; i < arr.length(); i++) {
                    tmpValues[i] = arr.getJSONObject(i).getDouble("Value");
                }
                Diagram diagram = (Diagram)rowView.findViewById(R.id.tide_diagram);
                diagram.set(tmpValues);


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

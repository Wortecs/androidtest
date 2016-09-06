package com.horsegaming.sensorchecker.core.net.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.horsegaming.sensorchecker.R;
import com.horsegaming.sensorchecker.core.net.AsyncGetter;
import com.horsegaming.sensorchecker.core.net.Component;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Iterator;
import java.util.concurrent.ExecutionException;

/**
 * Created by Horse on 05.09.2016.
 */
public class MoonComponent extends Component
{
    final String keyword = "VisibleOnMoonPhases";
    @Override
    public String getKeyword() {
        return keyword;
    }

    @Override
    public Drawer load() {
        return new MoonDrawer();
    }

    public class MoonDrawer implements Drawer{

        @Override
        public View draw(Context context, ViewGroup parent, int locationID) {

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.moon_view_layout, parent, false);

            try {
                String jsonStr = new AsyncGetter()
                        .execute("http://localbuoywebserver.staturedev.com/api/MobileApi/GetMoonPhases?locationId=" + locationID).get();
                JSONObject obj = new JSONObject(jsonStr).getJSONObject("ReturnValue");
                JSONObject phasesJSON = obj.getJSONObject("Phases");
                Iterator<String> iter = phasesJSON.keys();
                StringBuilder sb = new StringBuilder(50);
                while(iter.hasNext()){
                    sb.append(phasesJSON.getString(iter.next()));
                    sb.append(" ");
                }
                TextView tw = (TextView)rowView.findViewById(R.id.moon_phase);
                tw.setText(sb.toString());



            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return  rowView;
        }
    }
}

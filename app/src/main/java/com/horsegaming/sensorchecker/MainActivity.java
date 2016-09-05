package com.horsegaming.sensorchecker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.horsegaming.sensorchecker.core.database.DataBaseHelper;
import com.horsegaming.sensorchecker.core.database.Location;
import com.horsegaming.sensorchecker.core.net.AsyncGetter;
import com.horsegaming.sensorchecker.core.net.ComponentsHandler;
import com.horsegaming.sensorchecker.core.net.NetworkHandler;
import com.horsegaming.sensorchecker.core.view.LocationListAdapter;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    LocationListAdapter mainAdapter;
    Intent intentToOther;
    Intent intentToMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int locid = this.getIntent().getIntExtra(this.getString(R.string.db_table_location_locid), -1);
        Log.d("Loc id", locid+"");
        this.setContentView(R.layout.activity_main);

        //Can release in 1 activity class
        intentToOther = new Intent(this, OtherActivity.class);
        intentToMain = new Intent(this, MainActivity.class);

        if(locid == -1){

            DataBaseHelper.init(this);

            if(NetworkHandler.CheckConnection(this)){

                AsyncGetter getter = new AsyncGetter();
                try {
                    String json = getter.execute(this.getString(R.string.url_location)).get();
                    NetworkHandler.parseJSONLocation(json);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

            }
        }

        mainAdapter = new LocationListAdapter(DataBaseHelper.Helper.getLocation(locid), this);
        ListView view = (ListView)this.findViewById(R.id.main_listview);
        view.setAdapter(mainAdapter);
        Log.d("Download end:", true+"");

        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                boolean last = ((Location) mainAdapter.getItem(position)).Last;
                int locationID = ((Location) mainAdapter.getItem(position)).LocationID;
                Log.d("Location:", (mainAdapter.getItem(position)).toString());

                intentToMain.putExtra(getString(R.string.db_table_location_locid),
                        locationID);
                intentToOther.putExtra(getString(R.string.db_table_location_locid),
                        locationID);

                startActivity(last? intentToOther: intentToMain);
            }
        });
    }


}

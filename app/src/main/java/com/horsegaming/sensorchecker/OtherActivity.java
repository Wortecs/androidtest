package com.horsegaming.sensorchecker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.horsegaming.sensorchecker.core.net.ComponentsHandler;
import com.horsegaming.sensorchecker.core.view.ComponentListAdapter;

public class OtherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int locid = this.getIntent().getIntExtra(this.getString(R.string.db_table_location_locid), -1);

        this.setContentView(R.layout.activity_other);
        ComponentListAdapter adapter
                = new ComponentListAdapter(ComponentsHandler.load(this,locid), this, locid);

        ListView lv = (ListView)this.findViewById(R.id.other_listComponents);
        lv.setAdapter(adapter);
        Log.d("Other open", locid+"");
    }
}

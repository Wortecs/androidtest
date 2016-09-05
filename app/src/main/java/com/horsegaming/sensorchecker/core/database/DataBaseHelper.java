package com.horsegaming.sensorchecker.core.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.horsegaming.sensorchecker.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Horse on 05.09.2016.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    private Context context;
    public static DataBaseHelper Helper;

    public static DataBaseHelper init(Context context) {
        if(Helper == null){
            Helper = new DataBaseHelper(context, context.getString(R.string.db_name), null,  1);
        }
        return Helper;
    }

    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final StringBuilder sbCreator = new StringBuilder(200);
        //Create main table
        sbCreator.append("CREATE TABLE ");
        sbCreator.append(context.getString(R.string.db_table_location));
        sbCreator.append("( ");

        sbCreator.append(context.getString(R.string.db_table_location_locid));
        sbCreator.append(" integer primary key, ");

        sbCreator.append(context.getString(R.string.db_table_location_parid));
        sbCreator.append(" integer,");

        sbCreator.append(context.getString(R.string.db_table_location_locname));
        sbCreator.append(" text,");

        sbCreator.append(context.getString(R.string.db_table_location_last));
        sbCreator.append(" integer );");

        db.execSQL(sbCreator.toString());

        //Clear buffer
        sbCreator.delete(0, sbCreator.length());

        //Create buoys info table
        sbCreator.append("CREATE TABLE ");

        sbCreator.append(context.getString(R.string.db_table_params));
        sbCreator.append("( ");

        sbCreator.append(context.getString(R.string.db_table_location_locid));
        sbCreator.append(" integer, ");

        sbCreator.append(context.getString(R.string.db_table_params_param));
        sbCreator.append(" text,");

        sbCreator.append(context.getString(R.string.db_table_params_value));

        sbCreator.append(" integer, FOREIGN KEY (");
        sbCreator.append( context.getString(R.string.db_table_location_locid) );
        sbCreator.append(") REFERENCES " );
        sbCreator.append( context.getString(R.string.db_table_location) );
        sbCreator.append( "(" );
        sbCreator.append( context.getString(R.string.db_table_location_locid) );
        sbCreator.append( ") );" );

        db.execSQL(sbCreator.toString());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addLocation(int locationID, int parentID, String locationName, boolean last, SQLiteDatabase db){


        ContentValues cv = new ContentValues();
        cv.put(context.getString(R.string.db_table_location_locid), locationID);
        cv.put(context.getString(R.string.db_table_location_parid), parentID);
        cv.put(context.getString(R.string.db_table_location_locname), locationName);
        cv.put(context.getString(R.string.db_table_location_last), last ? 1 : 0);
        db.insert(context.getString(R.string.db_table_location), null, cv);

        //db.close();
    }

    public ArrayList<Location> getLocation(int parentId){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Location> locations = new ArrayList<>(100);

        String selection = context.getString(R.string.db_table_location_parid) + " = ?";
        String[] selectionArgs = new String[] { parentId+"" };

        Cursor cursor =  db.query(context.getString(R.string.db_table_location), null,
                selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst())
        {
            int locid = cursor.getColumnIndex(context.getString(R.string.db_table_location_locid));
            int locname = cursor.getColumnIndex(context.getString(R.string.db_table_location_locname));
            int loclast = cursor.getColumnIndex(context.getString(R.string.db_table_location_last));

            do {
                locations.add(new Location
                        (
                                cursor.getInt(locid),
                                cursor.getString(locname),
                                cursor.getInt(loclast)

                        ));
            } while (cursor.moveToNext());
        }

        db.close();
        return locations;
    }

    public void addParameter(int locationID, String parameter, boolean value, SQLiteDatabase db ){

        ContentValues cv = new ContentValues();
        cv.put(context.getString(R.string.db_table_location_locid), locationID);
        cv.put(context.getString(R.string.db_table_params_param), parameter);
        cv.put(context.getString(R.string.db_table_params_value), value);
        db.insert(context.getString(R.string.db_table_params), null, cv);


    }

    public HashMap<String, Boolean> getParameters(int locationID){
        SQLiteDatabase db = this.getReadableDatabase();
        HashMap<String, Boolean> params = new HashMap<>();

        String selection = context.getString(R.string.db_table_location_locid) + " = ?";
        String[] selectionArgs = new String[] { locationID+"" };

        Cursor cursor =  db.query(context.getString(R.string.db_table_params), null,
                selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst())
        {
            int paramid = cursor.getColumnIndex(context.getString(R.string.db_table_params_param));
            int valueid = cursor.getColumnIndex(context.getString(R.string.db_table_params_value));


            do {
                params.put(cursor.getString(paramid),cursor.getInt(valueid) != 0);

            } while (cursor.moveToNext());
        }

        db.close();
        return params;
    }

    public void clear(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(context.getString(R.string.db_table_location),null,null);
        db.delete(context.getString(R.string.db_table_params),null,null);
        db.close();
    }
}

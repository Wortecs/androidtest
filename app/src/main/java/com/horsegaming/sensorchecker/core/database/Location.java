package com.horsegaming.sensorchecker.core.database;

/**
 * Created by Horse on 05.09.2016.
 */
public class Location
{
    public int LocationID;
    public String Name;
    public boolean Last;

    public Location(int locationID, String name, int last){
        this.LocationID = locationID;
        this.Name = name;
        this.Last = last!=0;
    }

    @Override
    public String toString() {
        return "id: " + LocationID + " name: " + Name + " last: " + Last ;
    }
}

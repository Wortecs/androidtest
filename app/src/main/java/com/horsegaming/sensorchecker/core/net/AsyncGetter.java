package com.horsegaming.sensorchecker.core.net;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Horse on 05.09.2016.
 */
public class AsyncGetter extends AsyncTask<String, Void, String> {


    @Override
    protected String doInBackground(String... params)
    {
        if(params.length > 1 )
            throw new IllegalArgumentException();

        String rmpURL = params[0];
        String resultJson = "";
        try {
            URL url = new URL(rmpURL);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            resultJson = buffer.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return resultJson;
    }

}

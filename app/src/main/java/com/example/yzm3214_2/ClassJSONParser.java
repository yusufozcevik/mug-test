package com.example.yzm3214_2;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ClassJSONParser {
    org.json.JSONObject jsonO;
    org.json.JSONObject jObj;
    private InputStream is = null;
    private String json = "";

    public ClassJSONParser(){

    }

    JSONObject getJSONFromURL(String url){
        try {
            // defaultHttpClient
            URL urlObject = new java.net.URL(url);
            HttpURLConnection conn = (HttpURLConnection) urlObject.openConnection();
            conn.setRequestMethod("GET");
            is = new BufferedInputStream(conn.getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
            Log.d("***%%",e.toString());
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "UTF-8"), 8); // iso-8859-1 //
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                String tmp = line;
                sb.append(tmp);
            }
            is.close();
            json = sb.toString();
            //Log.d("***jsonData", json);
        } catch (Exception e) {
            Log.i("Buffer Error", "Error converting result " + e.toString());
            Log.d("***+++",e.toString());
        }

        // try parse the string to a JSON object
        try {
            if (json != null) {
                jObj = new JSONObject(json);
            } else {
                jObj = null;
            }

        } catch (JSONException e) {
            //
            Log.d("***---",e.toString());
        }
        return jObj;
    }


}

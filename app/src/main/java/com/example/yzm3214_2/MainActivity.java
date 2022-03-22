package com.example.yzm3214_2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
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
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(isOnline()) {
            //MyAsyncMethod mam = new MyAsyncMethod();
            //mam.execute();

            ConcurrentTask ct = new ConcurrentTask();
            ct.doTheWork();
        }
    }

    private class MyAsyncMethod extends AsyncTask<String,Integer,String>{

        org.json.JSONObject jsonO;
        org.json.JSONObject jObj;
        private InputStream is = null;
        private String json = "";
        ProgressDialog progressDialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            ClassJSONParser parser = new ClassJSONParser();
            JSONObject obj = parser.getJSONFromURL("http://dummy.restapiexample.com/api/v1/employees");
            if(obj!=null)
                Log.i("***obj",obj.toString());
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.cancel();
        }
    };

    public class ConcurrentTask {
        ExecutorService executor;

        public ConcurrentTask() {
            /* I used an executor that creates a new thread every time creating a server requests object
             * you might need to create a thread pool instead, depending on your application
             * */
            executor = Executors.newSingleThreadExecutor();
        }

        private JSONObject doTheWork() {
            // init
            Callable<JSONObject> callable;
            Future<JSONObject> future;
            JSONObject jsonResult = null;

            try {
                // create callable object with desired job
                callable = new Callable<JSONObject>() {
                    @Override
                    public JSONObject call() throws Exception {
                        JSONObject jsonResponse;

                        // connect to the server
                        ClassJSONParser jParser = new ClassJSONParser();
                        jsonResponse = jParser.getJSONFromURL("http://dummy.restapiexample.com/api/v1/employees");

                        // insert desired data into json object

                        // and return the json object
                        Log.i("***ConcurrencyPackage", jsonResponse.toString());
                        return jsonResponse;
                    }
                };

                future = executor.submit(callable);
                jsonResult = future.get();
            } catch (InterruptedException ex) {
                // Log exception at first so you could know if something went wrong and needs to be fixed
            } catch (ExecutionException ex) {
                // Log exception at first so you could know if something went wrong and needs to be fixed
            }

            return jsonResult;
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnectedOrConnecting();
        }
        return false;
    }
}
package com.example.yzm3214_2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ListViewActivity extends AppCompatActivity {
    private ListView lv;
    List<HashMap<String,String>> hList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        lv = findViewById(R.id.list_view);

        /*String iller[] = {"Ankara","Antalya","İstanbul","İzmir"};
        ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, android.R.id.text1, iller);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(ListViewActivity.this, iller[i] + " iteme tıklandı", Toast.LENGTH_SHORT).show();
            }
        });*/

        hList = new ArrayList<>();

        String fromMapKey[] = {"key1","key2","key3"};
        int toIdArray[] = {R.id.tv1, R.id.tv2, R.id.tv3 };

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                HashMap<String, String> hm = hList.get(i);
                String sehirAdi = hm.get("key1");
                Toast.makeText(ListViewActivity.this, sehirAdi, Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, hList, R.layout.layout,fromMapKey,toIdArray);
        lv.setAdapter(simpleAdapter);

        new ConcurrentTask().doTheWork();
    }

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
                        jsonResponse = jParser.getJSONFromURL("http://api.plos.org/search?q=title:DNA");


                        JSONObject response = jsonResponse.getJSONObject("response");
                        JSONArray jsonArray = response.getJSONArray("docs");
                        //Log.d("***",String.valueOf(jsonArray.length()));
                        for(int i = 0; i < jsonArray.length(); i++){
                            JSONObject obj = jsonArray.getJSONObject(i);
                            HashMap<String,String> hm = new HashMap<>();
                            hm.put("key1",obj.getString("id"));
                            hm.put("key2",obj.getString("journal"));
                            hm.put("key3",obj.getString("eissn"));
                            hList.add(hm);
                        }

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
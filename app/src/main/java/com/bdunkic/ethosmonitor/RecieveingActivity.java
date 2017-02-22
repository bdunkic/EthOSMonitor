package com.bdunkic.ethosmonitor;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Bojan on 1.2.2017..
 */
public class RecieveingActivity extends AppCompatActivity {

    private String TAG = RecieveingActivity.class.getSimpleName();
    public static final String CONDITION_TAG="CONDITION_TAG";
    public static final String IP_TAG="IP_TAG";
    public static final String GPUTEMP_TAG="GPUTEMP_TAG";
    public static final String CPUTEMP_TAG="CPUTEMP_TAG";
    public static final String MINER_TAG="MINER_TAG";
    public static final String VERSION_TAG="VERSION_TAG";
    public static final String MINERHASH_TAG="MINERHASH_TAG";
    public static final String HASH_TAG="HASH_TAG";
    public static final String RAM_TAG="RAM_TAG";

    public static final String MEMINFO_TAG="MEMINFO_TAG";







    String text;

    private ListView lvRigs;

    private ArrayList<HashMap<String, String>> rigList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recieveing);


        handleIntent();

        lvRigs=(ListView)findViewById(R.id.lvRigs);

        rigList=new ArrayList<>();


    }

    private void handleIntent() {

        Intent intent = getIntent();


        if (intent.hasExtra("EXTRA_KEY")) {

             text = intent.getStringExtra(MainActivity.EXTRA_KEY);


            new GetRigs().execute();




        }
    }

    private class GetRigs extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(RecieveingActivity.this,"Json is downloading",Toast.LENGTH_LONG).show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {

            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = "http://"+text+".ethosdistro.com/?json=yes";
            String jsonStr = sh.makeServiceCall(url);
            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {

                try {
                    JSONObject jObject = new JSONObject(jsonStr).getJSONObject("rigs");
                    Iterator<String> keys = jObject.keys();
                    while (keys.hasNext()) {
                        String key = keys.next();
                        Log.v("rigs key", key);
                        if(jObject.get(key) instanceof JSONObject) {
                            JSONObject innerJObject = jObject.getJSONObject(key);

                            String miner = innerJObject.getString("miner");
                            String condition = innerJObject.getString("condition");
                            String cpuTemp = innerJObject.getString("cpu_temp");
                            String version = innerJObject.getString("version");
                            String gpus = innerJObject.getString("gpus");
                            String miner_hashes = innerJObject.getString("miner_hashes");
                            String memInfo = innerJObject.getString("meminfo");
                            String ram = innerJObject.getString("ram");
                            String ip = innerJObject.getString("ip");
                            String hash= innerJObject.getString("hash");
                            String gpu_temp=innerJObject.getString("temp");

                            Log.v("details", "id = " + key + ", " + "miner = " + miner + ", " + "Condition = " + condition +
                                    ", " + "CPU TEMP = " + cpuTemp );

                            HashMap<String, String> rigs = new HashMap<>();

                            rigs.put("key",key);
                            rigs.put("miner",miner);
                            rigs.put("condition",condition);
                            rigs.put("cpu_temp",cpuTemp);
                            rigs.put("version",version);
                            rigs.put("gpus",gpus);
                            rigs.put("miner_hashes",miner_hashes);
                            rigs.put("meminfo",memInfo);
                            rigs.put("ram",ram);
                            rigs.put("ip",ip);
                            rigs.put("hash",hash);
                            rigs.put("temp",gpu_temp);


                            rigList.add(rigs);


                        } else if (jObject.get(key) instanceof String){
                            String value = jObject.getString("type");
                            Log.v("key = type", "value = " + value);
                        }
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }





            }


            return null;

    }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            ListAdapter adapter = new SimpleAdapter(RecieveingActivity.this, rigList,
                    R.layout.list_item, new String[]{ "key","condition", "miner","cpu_temp"},
                    new int[]{R.id.tvRig, R.id.tvCondition,R.id.tvMiner,R.id.tvCpuTemp});
            lvRigs.setAdapter(adapter);

            lvRigs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String miner = rigList.get(position).get("miner");
                    String condition = rigList.get(position).get("condition");
                    String cpuTemp = rigList.get(position).get("cpuTemp");
                    String version = rigList.get(position).get("version");
                    String gpus = rigList.get(position).get("gpus");
                    String miner_hashes = rigList.get(position).get("miner_hashes");
                    String memInfo = rigList.get(position).get("meminfo");
                    String ram = rigList.get(position).get("ram");
                    String ip = rigList.get(position).get("ip");
                    String hash= rigList.get(position).get("hash");
                    String gpu_temp=rigList.get(position).get("temp");
                   // String item = rigList.get(position).get("key");



                    Intent intent = new Intent(RecieveingActivity.this,RigListActivity.class);

                    intent.putExtra(MINER_TAG,miner);
                    intent.putExtra(CONDITION_TAG,condition);
                    intent.putExtra(CPUTEMP_TAG,cpuTemp);
                    intent.putExtra(version,VERSION_TAG);

                    intent.putExtra(MINERHASH_TAG,miner_hashes);
                    intent.putExtra(MEMINFO_TAG,memInfo);
                    intent.putExtra(ram,RAM_TAG);
                    intent.putExtra(IP_TAG,ip);
                    intent.putExtra(hash,HASH_TAG);
                    intent.putExtra(GPUTEMP_TAG,gpu_temp);

                    startActivity(intent);


                }
            });


        }
    }
        }









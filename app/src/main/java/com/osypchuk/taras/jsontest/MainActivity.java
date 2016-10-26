package com.osypchuk.taras.jsontest;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import info.androidhive.jsonparsing.R;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    private ListView lv;
    public String account_id = "374639183";
    public String steam_url_request = "https://api.steampowered.com/IDOTA2Match_570/GetMatchHistory/V001/?key=73EE0AFD6C12F2AB4605F611C2AB86EF&account_id=";

    // URL to get contacts JSON
    private String url = steam_url_request + account_id;

    ArrayList<HashMap<String, String>> matchList;
    ArrayList<HashMap<String,String>> playerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        matchList = new ArrayList<>();
        playerList = new ArrayList<>();
        final String LOG_TAG = "myLogs";

        lv = (ListView) findViewById(R.id.matcheslist);
        lv.setClickable(true);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(LOG_TAG, "itemClick: position = " + position + ", id = " + id);
            }
        });
        new GetMatches().execute();
    }


    /**
     * Async task class to get json by making HTTP call
     */
    private class GetMatches extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONObject result = jsonObj.getJSONObject("result");
                    JSONArray matches = result.getJSONArray("matches");
                    for (int i = 0; i < matches.length(); i++){

                        JSONObject lastmatch = matches.getJSONObject(i);

                        String match_id = "match id: "+lastmatch.getString("match_id");
                        String match_seq_num = lastmatch.getString("match_seq_num");
                        String start_time = lastmatch.getString("start_time");
                        int lobby_type = lastmatch.getInt("lobby_type");
                        String slobby_type = "";
                        switch (lobby_type) {
                            case -1:
                                slobby_type = "Invalid";break;
                            case 0:
                                slobby_type = "Public matchmaking";break;
                            case 1:
                                slobby_type = "Practice";break;
                            case 2:
                                slobby_type = "Tournament";break;
                            case 3:
                                slobby_type = "Tutorial";break;
                            case 4:
                                slobby_type = "Co-op with AI";break;
                            case 5:
                                slobby_type = "Team match";break;
                            case 6:
                                slobby_type = "Solo queue";break;
                            case 7:
                                slobby_type = "Ranked matchmaking";break;
                            case 8:
                                slobby_type = "Solo Mid 1 vs 1";break;
                        }

                        String radiant_team_id = lastmatch.getString("radiant_team_id");
                        String dire_team_id = lastmatch.getString("dire_team_id");

                        long timestamp = Long.parseLong(start_time)*1000;
                        Date d = new Date (timestamp);
                        String start = d.toString();

                        HashMap<String, String> match = new HashMap<>();

                        match.put("match_id",match_id);
                        match.put("start_time",start);
                        match.put("lobby_type",slobby_type);
                        match.put("match_seq_num",match_seq_num);


                    matchList.add(match);

                    JSONArray players = lastmatch.getJSONArray("players");
                    for (int j = 0; j < players.length(); j++){
                        JSONObject player = players.getJSONObject(j);
                        String account_id = player.getString("account_id");
                        String player_slot = player.getString("player_slot");
                        String hero_id = player.getString("hero_id");

                        HashMap<String, String> tenplayers = new HashMap<>();

                        player.put("account_id",account_id);
                        player.put("player_slot",player_slot);
                        player.put("hero_id",hero_id);

                        playerList.add(tenplayers);

                    }
                    }

                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adaptermatches = new SimpleAdapter(
                    MainActivity.this, matchList,
                    R.layout.list_item, new String[]{"match_id", "start_time","lobby_type"}, new int[]{R.id.match_id,
                    R.id.start_time, R.id.lobby_type});

            lv.setAdapter(adaptermatches);
        }


    }
}
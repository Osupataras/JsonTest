package com.osypchuk.taras.jsontest;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import info.androidhive.jsonparsing.R;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    private ListView lv;

    // URL to get contacts JSON
    private static String url = "https://api.steampowered.com/IDOTA2Match_570/GetMatchHistory/V001/?key=73EE0AFD6C12F2AB4605F611C2AB86EF&account_id=374639183";

    ArrayList<HashMap<String, String>> matchList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        matchList = new ArrayList<>();

        lv = (ListView) findViewById(R.id.list);

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
                    JSONArray matches = jsonObj.getJSONArray("matches");
                    JSONObject lastmatch = matches.getJSONObject(0);

                    String match_id = lastmatch.getString("match_id");
                    String match_seq_num = lastmatch.getString("match_seq_num");
                    String start_time = lastmatch.getString("start_time");
                    String lobby_type = lastmatch.getString("lobby_type");
                    String radiant_team_id = lastmatch.getString("radiant_team_id");
                    String dire_team_id = lastmatch.getString("dire_team_id");

                    HashMap<String, String> match = new HashMap<>();

                    match.put("match_id",match_id);
                    match.put("start_time",start_time);
                    match.put("lobby_type",lobby_type);

                    matchList.add(match);

                    JSONObject players = lastmatch.getJSONObject("players");
                    for (int i = 0; i < players.length(); i++){
                        String account_id = players.getString("account_id");
                        String player_slot = players.getString("player_slot");
                        String hero_id = players.getString("hero_id");

                        HashMap<String, String> player = new HashMap<>();

                        player.put("account_id",account_id);
                        player.put("player_slot",player_slot);
                        player.put("hero_id",hero_id);

                        matchList.add(player);
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
            ListAdapter adapter = new SimpleAdapter(
                    MainActivity.this, matchList,
                    R.layout.list_item, new String[]{"match_id", "start_time",
                    "lobyy_type"}, new int[]{R.id.match_id,
                    R.id.start_time, R.id.lobby_type});

            lv.setAdapter(adapter);
        }

    }
}